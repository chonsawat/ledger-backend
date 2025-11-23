package com.example.app.demo.Error;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorRestResponseHandler {
    @ExceptionHandler(value = ErrorRestResponseException.class)
    public ResponseEntity<Object> handleErrorReponseException(ErrorRestResponseException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorRestResponse errorResponse = new ErrorRestResponse(e.getMessage(), httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
