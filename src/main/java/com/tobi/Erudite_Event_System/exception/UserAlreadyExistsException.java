package com.tobi.Erudite_Event_System.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
