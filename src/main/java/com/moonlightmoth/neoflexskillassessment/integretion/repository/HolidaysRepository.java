package com.moonlightmoth.neoflexskillassessment.integretion.repository;

import com.moonlightmoth.neoflexskillassessment.integretion.util.ParamsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;


public class HolidaysRepository {

    private static final String INVALID_FILE_WARNING =
                    "WARNING: holidays file found, but has invalid format, " +
                    "proceeding workflow without excluding holidays data.\n" +
                    "Format must be dd.MM.yy on separate lines\n";
    private static final String NO_FILE_FOUND_WARNING =
                    "WARNING: No holidays file found, " +
                    "proceeding workflow without excluding holidays data.\n";
    private static final String EMPTY_FILE_WARNING =
                    "WARNING: holidays file found, but is empty, " +
                    "proceeding workflow without excluding holidays data.\n" +
                    "Format must be dd.MM.yy on separate lines\n";

    private ParamsParser paramsParser;
    private Set<LocalDate> holidaysSet;

    @Autowired
    public HolidaysRepository(ParamsParser paramsParser, Resource loadFrom)
    {
        this.paramsParser = paramsParser;
        fetchHolidays(loadFrom);
    }

    // fetch holidays from src/main/java/resources/holidays
    public void fetchHolidays(Resource holidayResource)
    {
        holidaysSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(holidayResource.getInputStream())))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                holidaysSet.add(paramsParser.parseLocalDate(line));
            }

            if (holidaysSet.isEmpty())
            {
                System.out.println(EMPTY_FILE_WARNING);
            }
            else
            {
                System.out.println("Holidays loaded:");
                holidaysSet
                        .stream()
                        .sorted()
                        .forEach(date -> System.out.println(date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))));
            }

        } catch (IOException e)
        {
            // if no file found, or it has invalid format, print warning and continue with empty holidays list
            System.out.println(NO_FILE_FOUND_WARNING);
            holidaysSet.clear();
        }
        catch (DateTimeParseException e)
        {
            System.out.println(INVALID_FILE_WARNING);
            holidaysSet.clear();
        }
    }

    public boolean isHoliday(LocalDate localDate)
    {
        return holidaysSet.contains(localDate);
    }
}
