package ru.moonlightmoth.neoflexskillassessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moonlightmoth.neoflexskillassessment.exceptions.InvalidLeavePaymentInfoException;
import ru.moonlightmoth.neoflexskillassessment.model.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandlingController {

    @ExceptionHandler(InvalidLeavePaymentInfoException.class)
    public ResponseEntity<ExceptionResponse> invalidLeavePaymentInfo(InvalidLeavePaymentInfoException e)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .message(e.getMessage()).build());
    }
}
