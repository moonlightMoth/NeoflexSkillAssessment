package ru.moonlightmoth.neoflexskillassessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moonlightmoth.neoflexskillassessment.exceptions.InvalidLeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandlingController {

    @ExceptionHandler(InvalidLeavePaymentInfo.class)
    public ResponseEntity<ExceptionResponse> invalidLeavePaymentInfo(InvalidLeavePaymentInfo e)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .message(e.getMessage()).build());
    }
}
