package ru.moonlightmoth.neoflexskillassessment.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;


@Repository
@Slf4j
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

    private ResourceLoader resourceLoader;
    private Resource holidaysFile;

    private Set<LocalDate> holidaysSet;

    public HolidaysRepository(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init()
    {
        holidaysFile  = resourceLoader.getResource("file:src/main/resources/holidays");
        fetchHolidays(holidaysFile);
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
                holidaysSet.add(parseLocalDate(line));
            }

            if (holidaysSet.isEmpty())
            {
                log.debug(EMPTY_FILE_WARNING);
            }
            else
            {
                log.debug("Holidays loaded:");
                holidaysSet
                        .stream()
                        .sorted()
                        .forEach(date -> log.debug(date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))));
            }

        } catch (IOException e)
        {
            // if no file found, or it has invalid format, print warning and continue with empty holidays list
            log.debug(NO_FILE_FOUND_WARNING);
            holidaysSet.clear();
        }
        catch (DateTimeParseException e)
        {
            log.debug(INVALID_FILE_WARNING);
            holidaysSet.clear();
        }
    }

    public boolean isHoliday(LocalDate localDate)
    {
        return holidaysSet.contains(localDate);
    }

    //parse String in format dd.MM.yy
    private LocalDate parseLocalDate(String dateParam)
    {
        LocalDate localDate = LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("dd.MM.yy"));

        // LocalDate.parse() doesn't throw exception if parses 31.02.xx, but returns last valid day of feb. We need exception here
        if (localDate.getMonth() == Month.FEBRUARY && !localDate.format(DateTimeFormatter.ofPattern("dd.MM.yy")).equals(dateParam))
            throw new DateTimeParseException("No such day in February current year", "", 0);

        return LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("dd.MM.yy"));
    }
}
