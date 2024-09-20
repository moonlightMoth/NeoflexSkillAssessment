package com.moonlightmoth.neoflexskillassessment.unit;


import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;


import static org.junit.jupiter.api.Assertions.*;

public class HolidaysRepositoryTest {

    private static final String separator = System.lineSeparator();

    private static final String EMPTY_FILE_WARNING =
            "WARNING: holidays file found, but is empty, " + separator +
                    "proceeding workflow without excluding holidays data." + separator +
                    "Format must be dd.MM.yy on separate lines" + separator;
    private static final String INVALID_FILE_WARNING =
            "WARNING: holidays file found, but has invalid format, " + separator +
                    "proceeding workflow without excluding holidays data." + separator +
                    "Format must be dd.MM.yy on separate lines" + separator;
    private static final String NO_FILE_FOUND_WARNING =
            "WARNING: No holidays file found, " + separator +
                    "proceeding workflow without excluding holidays data." + separator;

    @Test
    void fetchHolidaysLogTest() // check if holidays are correctly printed to log
    {
        //setup streams
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        TestLogger testLogger = new TestLogger(bos);

        //retrieve holidays
        new HolidaysRepository(
                new ParamsParser(),
                new ClassPathResource("holidays_test_1"),
                testLogger);

        // System.lineSeparator for test to be cross-platform
        StringBuilder expected = new StringBuilder("Holidays loaded:").append(separator);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("holidays_test_1").getInputStream())))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                expected.append(line).append(separator);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        assertEquals(bos.toString(), expected.toString());
    }

    @Test
    void fetchHolidaysFillTest() // check if holidays are correctly gathered
    {

        HolidaysRepository holidaysRepository = new HolidaysRepository(
                new ParamsParser(),
                new ClassPathResource("holidays_test_2"),
                new TestLogger(OutputStream.nullOutputStream()));

        HashSet<LocalDate> expected = new HashSet<>();

        expected.add(LocalDate.of(2024, Month.JANUARY, 1));
        expected.add(LocalDate.of(2024, Month.JANUARY, 2));

        expected.forEach(localDate -> assertTrue(holidaysRepository.isHoliday(localDate)));
    }

    @Test
    void fetchHolidaysEmptyTest() // check if empty file correctly processed and logged
    {
        //setup streams
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        TestLogger testLogger = new TestLogger(bos);

        HolidaysRepository holidaysRepository = new HolidaysRepository(
                new ParamsParser(),
                new ClassPathResource("holidays_test_3"),
                testLogger);

        // check for empty list
        assertDoesNotThrow(() -> {holidaysRepository.isHoliday(LocalDate.of(2024, Month.JANUARY, 1));});

        assertEquals(EMPTY_FILE_WARNING, bos.toString());
    }

    @Test
    void fetchHolidaysInvalidTest() // check if invalid file correctly processed and logged
    {
        //setup streams
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        TestLogger testLogger = new TestLogger(bos);

        HolidaysRepository holidaysRepository = new HolidaysRepository(
                new ParamsParser(),
                new ClassPathResource("holidays_test_4"),
                testLogger);

        // check for empty list
        assertDoesNotThrow(() -> {holidaysRepository.isHoliday(LocalDate.of(2024, Month.JANUARY, 1));});

        assertEquals(INVALID_FILE_WARNING, bos.toString());
    }

    @Test
    void fetchHolidaysAbsentTest() // check if absent file correctly processed and logged
    {
        //setup streams
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        TestLogger testLogger = new TestLogger(bos);

        HolidaysRepository holidaysRepository = new HolidaysRepository(
                new ParamsParser(),
                new ClassPathResource("holidays_test_5"),
                testLogger);

        // check for empty list
        assertDoesNotThrow(() -> {holidaysRepository.isHoliday(LocalDate.of(2024, Month.JANUARY, 1));});

        assertEquals(NO_FILE_FOUND_WARNING, bos.toString());
    }
}
