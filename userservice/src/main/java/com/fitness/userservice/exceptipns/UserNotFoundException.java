package com.fitness.userservice.exceptipns;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){

        super(message);

    }
}
