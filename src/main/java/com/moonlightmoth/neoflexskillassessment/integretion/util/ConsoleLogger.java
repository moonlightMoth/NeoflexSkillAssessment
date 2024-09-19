package com.moonlightmoth.neoflexskillassessment.integretion.util;


import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger {

    public String buildResponseStringForLog(ResponseEntity<String> resp)
    {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) +
                "  RESPONSE --- " +
                resp.getStatusCode() +
                " " +
                resp.getBody();
    }
}
