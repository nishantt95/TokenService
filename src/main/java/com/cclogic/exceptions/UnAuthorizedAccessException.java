package com.cclogic.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Created by Nishant on 9/19/2017.
 */
public class UnAuthorizedAccessException extends RuntimeException{
    public HttpStatus errorCode = HttpStatus.UNAUTHORIZED;
    public int errorCodeValue = errorCode.value();

    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
