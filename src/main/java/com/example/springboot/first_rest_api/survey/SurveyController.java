package com.example.springboot.first_rest_api.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyController {

    private SurveyService surveyService;

    // Constructor injection of surveyService into SurveyResource
    public SurveyController(SurveyService surveyService) {
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
    public Question retrieveSurveyQuestionById(@PathVariable String surveyId,
                                               @PathVariable String questionId) {
        Question questionById = surveyService.retrieveSurveyQuestionById(surveyId, questionId);
        if(questionById == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return questionById;
    }

    // POST /surveys/{surveyId}/questions
    @RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        // Best practice is to send the location of the API as part of the response -> Use ServletUriComponentBuilder
        // /surveys/{surveyId}/questions/{questionId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    // DELETE /surveys/{surveyId}/questions/{questionId}
    @RequestMapping(path = "/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId) {
        String deletedQuestion = surveyService.deleteSurveyQuestion(surveyId, questionId);
        if(deletedQuestion == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return ResponseEntity.noContent().build();
    }

    // PUT /surveys/{surveyId}/questions/{questionId}
    @RequestMapping(path = "/surveys/{surveyId}/questions/{questionId}", method=RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
                                                       @PathVariable String questionId,
                                                       @RequestBody Question question) {
        surveyService.updateSurveyQuestion(surveyId, questionId, question);

        return ResponseEntity.noContent().build();
    }

}
