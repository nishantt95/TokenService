package com.cclogic.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by Nishant on 9/19/2017.
 */
public class InvalidDataException extends RuntimeException {

    public HttpStatus errorCode = HttpStatus.UNPROCESSABLE_ENTITY;

    public int errorCodeValue = errorCode.value();

    public InvalidDataException(String message){
        super(message);
    }
}
