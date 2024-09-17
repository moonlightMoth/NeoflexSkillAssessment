package com.moonlightmoth.neoflexskillassessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {

    private static final int INVALID_ARGS = -1;
    private static final int SHORT_FORM = 0;
    private static final int FULL_FORM = 1;

    @GetMapping("/calculate")
    public ResponseEntity<String> calculate(
            @RequestParam (name = "avgSalary", required = false)                String avgSalary,
            @RequestParam (name = "vacationLength", required = false)           String vacationLength,
            @RequestParam (name = "fromDate", required = false)                 String fromDate,
            @RequestParam (name = "dueToDate", required = false)                String dueToDate)
    {
        int type = getRequestType(avgSalary, vacationLength, fromDate, dueToDate);
        ResponseEntity<String> resp;

        switch (type)
        {
            default -> resp = ResponseEntity.status(HttpStatus.OK).body("Request must have query OR body params avgSalary AND (vacationLength OR (fromDate AND dueToDate))");
        }

        return resp;
    }

    private int getRequestType(String avgSalary, String vacationLength, String fromDate, String dueToDate) //TODO
    {
        if (avgSalary == null)
            return INVALID_ARGS;

        if (vacationLength == null && (fromDate == null || dueToDate == null)) //TODO
            return INVALID_ARGS;

        return INVALID_ARGS;
    }
}
