package ru.moonlightmoth.neoflexskillassessment.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import ru.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import ru.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.moonlightmoth.neoflexskillassessment.validation.ValidLeavePaymentInfo;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

@RestController
public class CalculateController {

    @Autowired
    private ParamsParser parser;

    @Autowired
    private LeavePayCalculator leavePayCalculator;

    @Autowired
    private Logger consoleLogger;
    private static final String BAD_REQUEST_HINT =
                            "Request parameters format:<br>" +
                            "avgSalary AND (vacationLength XOR (fromDate AND dueToDate))<br>" +
                            "avgSalary: double<br>" +
                            "vacationLength: int<br>" +
                            "fromDate: dd.MM.yy<br>" +
                            "dueToDate: dd.MM.yy <br>" + System.lineSeparator();

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(@Valid @ModelAttribute LeavePaymentInfo leavePaymentInfo, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());

        System.out.println(leavePaymentInfo);
        return ResponseEntity.ok(leavePaymentInfo.toString());
    }

}
