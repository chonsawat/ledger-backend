package com.example.app.demo.Error;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRestResponse {
    private String message;
    private HttpStatus httpsStatus;
    private ZonedDateTime timestamp;
}
