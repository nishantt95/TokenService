package com.cclogic.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by Nishant on 9/19/2017.
 */
public class ResourceNotFoundException extends RuntimeException {

    public HttpStatus errorCode = HttpStatus.NOT_FOUND;
    public int errorCodeValue = errorCode.value();

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
