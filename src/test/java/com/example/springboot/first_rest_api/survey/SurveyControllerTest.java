package com.example.springboot.first_rest_api.survey;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// Just launch the web controller context
@WebMvcTest(controllers = SurveyController.class)
@AutoConfigureMockMvc(addFilters = false)
class SurveyControllerTest {
    /**
     * UT for retrieveSurveyQuestionById controller method
     * mock surveyService.retrieveSurveyQuestionById() as it is an external dependency (other layer) -> @MockitoBean
     * @MockMvc -> used to fire request
     * GET /surveys/survey1/questions/Question1
     */
    @MockitoBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";
    private static String GENERIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions";

    @Test
    void retrieveSurveyQuestionById_errorScenario_404() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void retrieveSurveyQuestionById_basicScenario() throws Exception {

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        when(surveyService.retrieveSurveyQuestionById("Survey1", "Question1")).thenReturn(question);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse = """
                {
                    "id":"Question1",
                    "description":"Most Popular Cloud Platform Today",
                    "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                    "correctAnswer":"AWS"
                }
                """;
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
    }

    /**
     * add new survey question -> POST /surveys/Survey1/questions
     * assert on response status and response header (location)
     */
    @Test
    public void addNewSurveyQuestion_basicScenario() throws Exception {
        String requestBody = """
            {
                "description": "Fastest programming language",
                "options": ["Java","Python","C++","JavaScript"],
                "correctAnswer": "C++"
            }
            """;
        when(surveyService.addNewSurveyQuestion(anyString(), any())).thenReturn("SOME_ID");
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String locationHeader = response.getHeader("Location");

        assertEquals(201, response.getStatus());
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));
    }
}