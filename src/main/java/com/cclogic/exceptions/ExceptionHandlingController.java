package com.cclogic.exceptions;

/**
 * Created by Nishant on 9/19/2017.
 */
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(ex.errorCodeValue);
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response,ex.errorCode);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponse> invalidDataFound(InvalidDataException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(ex.errorCodeValue);
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, ex.errorCode);
    }

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ExceptionResponse> unAuthorizedAccess(UnAuthorizedAccessException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(ex.errorCodeValue);
        response.setErrorMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, ex.errorCode);
    }
}
