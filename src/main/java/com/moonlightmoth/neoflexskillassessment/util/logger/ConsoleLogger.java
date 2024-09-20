package com.moonlightmoth.neoflexskillassessment.util.logger;


import org.springframework.http.ResponseEntity;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ConsoleLogger implements Logger {

    final private PrintStream ps;

    public ConsoleLogger()
    {
        ps = System.out;
    }

    @Override
    public void log(String s)
    {
        ps.println(s);
    }

}
