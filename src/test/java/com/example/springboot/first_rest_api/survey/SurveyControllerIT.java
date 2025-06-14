package com.example.springboot.first_rest_api.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

// This annotation launches spring application context, parameter assigns random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {

    /**
     * Fire a request to http://localhost:8080/surveys/survey1/questions/question1
     * and assert on the expectedResponse
     */
    String expectedResponse = """
            {
                "id": "Question1",
                "description": "Most Popular Cloud Platform Today",
                "options": [
                "AWS",
                "Azure",
                "Google Cloud",
                "Oracle Cloud"
                ],
                "correctAnswer": "AWS"
            }
            """;
    // Autowiring takes care of http://localhost:RANDOM_PORT
    @Autowired
    private TestRestTemplate testRestTemplate;

    // Now effective endpoint is /surveys/survey1/questions/question1
    private static String SPECIFIC_QUESTION_URL = "/surveys/survey1/questions/question1";
    // Header: [content-type:"application/json", date:"Sat, 14 Jun 2025 17:39:48 GMT", transfer-encoding:"chunked"]
    // Body: {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
    @Test
    void retrieveQuestionsForSurvey_basicScenario() throws JSONException {
        // Arrange -> not required here since we are firing a get request, and endpoint defined above
        String expectedResponseBody = """
                {
                    "id":"Question1",
                    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"
                }
                """;
        // Act -> firing request
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        System.out.println("Header: "+ responseEntity.getHeaders());
        String responseBody = responseEntity.getBody();
        System.out.println("Body: "+ responseBody);
        // Assert
//        assertEquals(expectedResponse.trim(), responseBody);
        JSONAssert.assertEquals(expectedResponseBody, responseBody, false);
    }
}