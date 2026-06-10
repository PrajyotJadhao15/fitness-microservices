package com.fitness.activityservice.Exceptions;

public class ActivityNotFoundException extends RuntimeException{

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
