package com.example.springboot.first_rest_api.survey;

import java.util.List;

public class Question {
    private String id;
    private String description;
    private List<String> options;
    private String correctAnswer;

    public Question() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public Question(String id, String description, List<String> options, String correctAnswer) {
        this.id = id;
        this.description = description;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", options=" + options +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
