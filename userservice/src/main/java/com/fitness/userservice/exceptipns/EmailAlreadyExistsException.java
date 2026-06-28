package com.fitness.userservice.exceptipns;

public class EmailAlreadyExistsException extends  RuntimeException{

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
