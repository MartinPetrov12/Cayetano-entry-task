package com.cayetano.entrytask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ControllerAdvice is used to handle exceptions globally across the whole application.
 * All exceptions fall in the same method, which returns a ClientErrorResponse
 * to the client.
 */
@ControllerAdvice
public class GameControllerExceptionHandler {

    /**
     * The method takes care of all exceptions and composes a ClientErrorResponse
     * object. It consists of the error code, the exception message and a
     * timestamp of when the exception has been thrown. Then, the object
     * is being returned to the client.
     * @param exception - the exception being thrown
     * @return a ResponseEntity, composed of the ClientErrorResponse and the HttpStatus
     */
    @ExceptionHandler
    public ResponseEntity<ClientErrorResponse> handleException(Exception exception) {
        ClientErrorResponse error = new ClientErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
