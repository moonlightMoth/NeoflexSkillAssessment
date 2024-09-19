package com.moonlightmoth.neoflexskillassessment.integretion.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ParamsParser {

    public static final int INVALID_PARAMS = -1;
    public static final int SHORT_FORM = 0;
    public static final int FULL_FORM = 1;

    public int validateParams(String avgSalary, String vacationLength, String fromDate, String dueToDate)
    {
        // if avgSalary is invalid then params are invalid
        if (!isValidDoubleParam(avgSalary))
            return INVALID_PARAMS;

        //if vacationLength valid AND others absent then params are valid
        if (isValidDoubleParam(vacationLength) && fromDate == null && dueToDate == null)
            return SHORT_FORM;

        //if vacationLength absent AND others valid AND fromDate <= dueToDate then params are valid
        if (vacationLength == null && isValidLocalDateParam(fromDate) && isValidLocalDateParam(dueToDate))
        {
            LocalDate from = parseLocalDate(fromDate);
            LocalDate dueTo = parseLocalDate(dueToDate);

            if (from.isBefore(dueTo.plusDays(1)))
                return FULL_FORM;
            else
                return INVALID_PARAMS;
        }



        return INVALID_PARAMS;
    }

    // if we can parse param to non-negative double then it is valid
    public boolean isValidDoubleParam(String val)
    {
        if (val == null)
            return false;

        try
        {
            double d = parseDouble(val);
            if (d <= 0)
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    // if we can parse param to non-negative int then it is valid
    public boolean isValidIntParam(String val)
    {
        if (val == null)
            return false;

        try
        {
            int d = parseInt(val);
            if (d <= 0)
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    //if we can parse param to valid date then it is valid
    public boolean isValidLocalDateParam(String val)
    {
        if (val == null)
            return false;

        try
        {
            parseLocalDate(val);
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
        return true;
    }

    public double parseDouble(String doubleParam)
    {
        return Double.parseDouble(doubleParam);
    }

    public int parseInt(String intParam)
    {
        return Integer.parseInt(intParam);
    }

    //parse String in format dd.MM.yy
    public LocalDate parseLocalDate(String dateParam)
    {
        LocalDate localDate = LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("dd.MM.yy"));

        // LocalDate.parse() doesn't throw exception if parses 31.02.xx, but returns last valid day of feb. We need exception here
        if (localDate.getMonth() == Month.FEBRUARY && !localDate.format(DateTimeFormatter.ofPattern("dd.MM.yy")).equals(dateParam))
            throw new DateTimeParseException("No such day in February current year", "", 0);

        return LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("dd.MM.yy"));
    }
}

