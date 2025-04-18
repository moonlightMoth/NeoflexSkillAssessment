package ru.moonlightmoth.neoflexskillassessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentResponse;
import ru.moonlightmoth.neoflexskillassessment.service.LeavePaymentCalculatorService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@RestController
@Validated
public class CalculateController {
    @Autowired
    private LeavePaymentCalculatorService leavePaymentCalculatorService;

    @ResponseBody
    @GetMapping(value = "/calculate")
    public ResponseEntity<LeavePaymentResponse> calculate(@Valid @ModelAttribute LeavePaymentInfo leavePaymentInfo)
    {
        return ResponseEntity.ok(leavePaymentCalculatorService.calculate(leavePaymentInfo));
    }

}
