package com.fitness.activityservice.Exceptions;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message, Throwable ex){
        super(message, ex);
    }
    public InvalidUserException(String message){
    super(message);
    }
}
