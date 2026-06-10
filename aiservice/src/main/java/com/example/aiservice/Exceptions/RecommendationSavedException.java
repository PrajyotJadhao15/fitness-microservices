package com.example.aiservice.Exceptions;

public class RecommendationSavedException extends RuntimeException{

    public RecommendationSavedException(String message) {
        super(message);
    }

    public RecommendationSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
