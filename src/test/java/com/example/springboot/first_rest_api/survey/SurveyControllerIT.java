package com.example.springboot.first_rest_api.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This annotation launches spring application context, parameter assigns random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {

    // Autowiring takes care of http://localhost:RANDOM_PORT
    // TestRestTemplate -> library class to handle rest request hit
    @Autowired
    private TestRestTemplate testRestTemplate;

    // Now effective endpoint is /surveys/survey1/questions/question1
    private static final String SPECIFIC_QUESTION_URL = "/surveys/survey1/questions/question1";
    private static final String ALL_QUESTION_URLS = "/surveys/survey1/questions";

    /**
     * Fire a request to http://localhost:8080/surveys/survey1/questions/question1
     * and assert on the expectedResponse
     */

    // Header: [content-type:"application/json", date:"Sat, 14 Jun 2025 17:39:48 GMT", transfer-encoding:"chunked"]
    // Body: {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
    // GET http://localhost:RANDOM_PORT/surveys/survey1/questions/question1
    @Test
    void retrieveSpecificQuestionForSurvey_basicScenario() throws JSONException {

        // Prepare request header
        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeaders();

        // Combine both request header and body
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);

        // Arrange -> not required here since we are firing a get request, and endpoint defined above
        String expectedResponseBody = """
                {
                    "id":"Question1",
                    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"
                }
                """;
        // Act -> firing request
//        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(SPECIFIC_QUESTION_URL, String.class);
        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(SPECIFIC_QUESTION_URL, HttpMethod.GET, httpEntity, String.class);

        // Assert status of response = 200
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // Assert content type which is sent as part of header
        assertEquals("application/json", responseEntity.getHeaders().get("content-type").get(0));

        // Assert response body
        JSONAssert.assertEquals(expectedResponseBody, responseEntity.getBody(), false);
    }

    // GET http://localhost:RANDOM_PORT/surveys/survey1/questions
    @Test
    void retrieveAllQuestionsForSurvey_basicScenario() throws JSONException {
        String expectedResponseBody = """
                [
                    {
                        "id":"Question1",
                        "correctAnswer":"AWS"
                    },
                    {
                        "id":"Question2"
                    },
                    {
                        "id":"Question3",
                        "correctAnswer": "Kubernetes"
                    }
                ]
                """;

        // Prepare request header
        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeaders();

        // Combine both request header and body
        HttpEntity<String> httpEntity = new HttpEntity(null, httpHeaders);

//        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(ALL_QUESTION_URLS, String.class);
        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(ALL_QUESTION_URLS, HttpMethod.GET, httpEntity, String.class);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponseBody, responseEntity.getBody(), false);

    }

    // TODO: IT for GET /surveys and GET /surveys/survey1

    /**
     * POST /surveys/{surveyId}/questions
     * http:localhost:RANDOM_PORT/surveys/survey/questions/question4
     * Request header ->  Content-Type: application/json
     * Request body -> given as string above
     * Assert on -> status code, location header (http:localhost:RANDOM_PORT/surveys/survey/questions/AUTO_GENERATED_ID)
     */
    @Test
    void addNewSurveyQuestion_basicScenario() {

        // Prepare request header
        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeaders();

        // Request body
        String requestBody = """
            {
                "description": "Fastest programming language",
                "options": ["Java","Python","C++","JavaScript"],
                "correctAnswer": "C++"
            }
            """;

        // Combine both request header and body
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, httpHeaders);

        // fire the request
        ResponseEntity<String> responseEntity =
                testRestTemplate.exchange(ALL_QUESTION_URLS, HttpMethod.POST, httpEntity, String.class);
        String locationHeader = responseEntity.getHeaders().get("Location").get(0);

//        Response Header: [content-length:"0", date:"Sun, 15 Jun 2025 11:20:06 GMT", location:"http://localhost:55749/surveys/survey1/questions/1214278348"]

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertTrue(locationHeader.contains("/surveys/survey1/questions"));

        // To resolve the side effect of get list test failing, delete the posted request by passing a DELETE request to
        // the same endpoint
        ResponseEntity<String> responseEntityDelete =
                testRestTemplate.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);
        assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
//        testRestTemplate.delete(locationHeader);
    }

    private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Basic " +
                                        performBasicAuthEncoding("user", "password"));
        return httpHeaders;
    }

    String performBasicAuthEncoding(String username, String password) {
        String combined = username + ":" + password;

        // Base64 encode string -> will serialize/convert to bytes
        byte[] encodedByteStream = Base64.getEncoder().encode(combined.getBytes());

        // Deserialize/convert bytes to String
        return new String(encodedByteStream);
    }

}