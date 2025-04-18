package ru.moonlightmoth.neoflexskillassessment.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class HolidaysRepositoryTest {

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Test
    void initTest()
    {
        assertTrue(holidaysRepository.isHoliday(LocalDate.of(2025,1,1)));
    }
}
