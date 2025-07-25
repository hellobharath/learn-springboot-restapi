package com.example.springboot.first_rest_api.survey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Predicate;

@Service
public class SurveyService {
    // making it static to have only 1 instance and add new details to the same instance
    private static List<Survey> surveys = new ArrayList<>();

    // Initialise the survey with some data
    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);
    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {

        // Lambda function to check for matching surveyId -> functional programming
        Predicate<Survey> surveyPredicate = survey -> survey.getId().equalsIgnoreCase(surveyId);
        // Using stream.filter to get the optional survey object with the predicate, and get the first matching instance
        // Functional programming methods generally return optional due to the probability of the return value being null
        Optional<Survey> optionalSurvey =
                surveys.stream()
                        .filter(surveyPredicate)
                        .findFirst();
        // If the value is not null, return it else return null
        return optionalSurvey.orElse(null);
    }

    public List<Question> retrieveQuestionsForSurvey(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (survey == null)
            return null;
        return survey.getQuestions();
    }

    public Question retrieveSurveyQuestionById(String surveyId, String questionId) {
        List<Question> questions = retrieveQuestionsForSurvey(surveyId);
        if(questions == null)
            return null;
        Optional<Question> question =
                questions.stream()
                         .filter(question1 -> question1.getId().equalsIgnoreCase(questionId))
                         .findFirst();
        return question.orElse(null);
    }

    public String addNewSurveyQuestion(String surveyId, Question question) {
        List<Question> questions = retrieveQuestionsForSurvey(surveyId);
        question.setId(generateRandomId());
        questions.add(question);
        return question.getId();
    }

    private static String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        String randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }

    public String deleteSurveyQuestion(String surveyId, String questionId) {
        List<Question> questions = retrieveQuestionsForSurvey(surveyId);
        if(questions == null)
            return null;
        Predicate<Question> questionPredicate = question1 -> question1.getId().equalsIgnoreCase(questionId);
        boolean questionRemoved = questions.removeIf(questionPredicate);

        return (!questionRemoved ? null : questionId);
    }

    public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
        // Remove the question with the question with the provided question id and adding the new request body, thus
        // performing an update operation
        List<Question> questions = retrieveQuestionsForSurvey(surveyId);
        questions.removeIf(question1 -> question1.getId().equalsIgnoreCase(questionId));
        questions.add(question);
    }
}
