package ru.moonlightmoth.neoflexskillassessment.integration;

import ru.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import ru.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import ru.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
public class BeanTest {

    @Autowired
    HolidaysRepository holidaysRepository;

    @Autowired
    ParamsParser paramsParser;

    @Autowired
    LeavePayCalculator leavePayCalculator;

    @Test
    void beanTest() // check if beans are correctly created
    {
        holidaysRepository.isHoliday(LocalDate.of(2024, Month.JANUARY, 1));
        paramsParser.parseLocalDate("12.12.12");
        leavePayCalculator.calculateShortForm(12,12);
    }
}
