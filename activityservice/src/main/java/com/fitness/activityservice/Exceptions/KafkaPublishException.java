package com.fitness.activityservice.Exceptions;

public class KafkaPublishException extends  RuntimeException{


    public KafkaPublishException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
