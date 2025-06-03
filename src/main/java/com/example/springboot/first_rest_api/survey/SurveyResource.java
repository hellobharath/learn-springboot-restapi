package com.example.springboot.first_rest_api.survey;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
