package com.example.springboot.first_rest_api.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SurveyResource {

    private SurveyService surveyService;

    // Constructor injection of surveyService into SurveyResource
    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    // GET /surveys
    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyService.retrieveAllSurveys();
    }

    // GET /surveys/{surveyId}
    @RequestMapping("/surveys/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable String surveyId) {
        Survey surveyById = surveyService.retrieveSurveyById(surveyId);
        if(surveyById == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return surveyById;
    }

    // GET /surveys/{surveyId}/questions
    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyId) {
        List<Question> questions = surveyService.retrieveQuestionsForSurvey(surveyId);
        if(questions == null)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        return questions;
    }

    // GET /surveys/{surveyId}/questions/{questionId}
    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestionsForSurvey(@PathVariable String surveyId,
                                                     @PathVariable String questionId) {
        Question questionById = surveyService.retrieveQuestionById(surveyId, questionId);
        if(questionById == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questionById;
    }

}
