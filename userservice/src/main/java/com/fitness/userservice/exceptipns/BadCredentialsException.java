package com.fitness.userservice.exceptipns;

public class BadCredentialsException extends RuntimeException {

       public BadCredentialsException(String message){

           super(message);

       }
}
