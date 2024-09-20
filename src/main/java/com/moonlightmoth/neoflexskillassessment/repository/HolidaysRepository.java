package com.moonlightmoth.neoflexskillassessment.repository;

import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import com.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;


public class HolidaysRepository {

    private static final String separator = System.lineSeparator();
    private static final String EMPTY_FILE_WARNING =
            "WARNING: holidays file found, but is empty, " + separator +
                    "proceeding workflow without excluding holidays data." + separator +
                    "Format must be dd.MM.yy on separate lines";
    private static final String INVALID_FILE_WARNING =
            "WARNING: holidays file found, but has invalid format, " + separator +
                    "proceeding workflow without excluding holidays data." + separator +
                    "Format must be dd.MM.yy on separate lines";
    private static final String NO_FILE_FOUND_WARNING =
            "WARNING: No holidays file found, " + separator +
                    "proceeding workflow without excluding holidays data.";


    final private ParamsParser paramsParser;
    private Set<LocalDate> holidaysSet;
    final private Logger logger;

    @Autowired
    public HolidaysRepository(ParamsParser paramsParser, Resource loadFrom, Logger logger)
    {
        this.paramsParser = paramsParser;
        this.logger = logger;
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
                logger.log(EMPTY_FILE_WARNING);
            }
            else
            {
                logger.log("Holidays loaded:");
                holidaysSet
                        .stream()
                        .sorted()
                        .forEach(date -> logger.log(date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))));
            }

        } catch (IOException e)
        {
            // if no file found, or it has invalid format, print warning and continue with empty holidays list
            logger.log(NO_FILE_FOUND_WARNING);
            holidaysSet.clear();
        }
        catch (DateTimeParseException e)
        {
            logger.log(INVALID_FILE_WARNING);
            holidaysSet.clear();
        }
    }

    public boolean isHoliday(LocalDate localDate)
    {
        return holidaysSet.contains(localDate);
    }
}
