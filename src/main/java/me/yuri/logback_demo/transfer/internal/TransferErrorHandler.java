package me.yuri.logback_demo.transfer.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransferErrorHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponse handleInsufficientFundsException(InsufficientBalanceException e) {
        return new ErrorResponse(e.getMessage());
    }

    private record ErrorResponse(String message) {}
}
