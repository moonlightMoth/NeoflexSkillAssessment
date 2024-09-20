package com.moonlightmoth.neoflexskillassessment.controller;

import com.moonlightmoth.neoflexskillassessment.AppConfig;
import com.moonlightmoth.neoflexskillassessment.util.logger.ConsoleLogger;
import com.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import com.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
                            "dueToDate: dd.MM.yy <br>";

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(
            @RequestParam (name = "avgSalary", required = false)                String avgSalary,
            @RequestParam (name = "vacationLength", required = false)           String vacationLength,
            @RequestParam (name = "fromDate", required = false)                 String fromDate,
            @RequestParam (name = "dueToDate", required = false)                String dueToDate)
    {
        int type = parser.validateParams(avgSalary, vacationLength, fromDate, dueToDate);
        ResponseEntity<String> resp;
        double leavePay;


        switch (type)
        {
            case ParamsParser.SHORT_FORM :
                leavePay = leavePayCalculator.calculateShortForm(
                        Double.parseDouble(avgSalary),
                        Integer.parseInt(vacationLength));

                resp = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format(Locale.ENGLISH, "%.2f", leavePay));
            break;

            case ParamsParser.FULL_FORM:
                leavePay = leavePayCalculator.calculateFullForm(
                        parser.parseDouble(avgSalary),
                        parser.parseLocalDate(fromDate),
                        parser.parseLocalDate(dueToDate));

                resp = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format(Locale.ENGLISH, "%.2f", leavePay));
            break;
            default: resp = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(BAD_REQUEST_HINT);
        }

        consoleLogger.log(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) +
                "  RESPONSE --- " +
                resp.getStatusCode() +
                " " +
                resp.getBody());


        return resp;
    }

}
