package me.yuri.logback_demo.http;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
            .badRequest()
            .body(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception e) {
        return ResponseEntity
            .internalServerError()
            .body(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage()));
    }
}