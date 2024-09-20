package com.moonlightmoth.neoflexskillassessment.unit;


import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LeavePayCalculatorTest {

    @Test
    void calculateShortFormTest()
    {
        LeavePayCalculator leavePayCalculator = new LeavePayCalculator(null);

        assertEquals(leavePayCalculator.calculateShortForm(365000.0, 1), 1000);
        assertEquals(leavePayCalculator.calculateShortForm(36500, 1), 100);
        assertEquals(leavePayCalculator.calculateShortForm(0, 1), 0);

        assertNotEquals(leavePayCalculator.calculateShortForm(365000.0, 1), 123);

    }

    @Test
    void calculateFullFormTest()
    {
        LeavePayCalculator leavePayCalculator = new LeavePayCalculator(
                                new HolidaysRepository(new ParamsParser(),
                                new ClassPathResource("holidays_test_1"),
                                new TestLogger(OutputStream.nullOutputStream())));

        assertEquals(leavePayCalculator.calculateFullForm(
                365,
                LocalDate.of(2024, Month.JANUARY, 1),
                LocalDate.of(2024, Month.JANUARY, 5)), 0); // holidays

        assertEquals(leavePayCalculator.calculateFullForm(
                365,
                LocalDate.of(2024, Month.FEBRUARY, 5),
                LocalDate.of(2024, Month.FEBRUARY, 9)), 5); // paid days

        assertEquals(leavePayCalculator.calculateFullForm(
                365,
                LocalDate.of(2024, Month.SEPTEMBER, 1),
                LocalDate.of(2024, Month.SEPTEMBER, 15)), 10); // partly weekends
    }
}
