package com.gs.numberconverter.controller;

import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for web requests.
 */
@ControllerAdvice
public class ExceptionConfiguration extends ResponseEntityExceptionHandler {

    /**
     * Any exception missed be the default exception handler will be handled here.
     * A 500 internal server error will be returned to the user.
     * @param e
     * @param request
     * @return 
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, "Unexpected server side error", new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }
}
