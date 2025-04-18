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
public class HolidaysRepository {
    private Set<LocalDate> holidaysSet;

    @PostConstruct
    public void init()
    {
        holidaysSet = new HashSet<>();
        holidaysSet.add(LocalDate.of(2025,1,1));
        holidaysSet.add(LocalDate.of(2025,1,2));
        holidaysSet.add(LocalDate.of(2025,1,3));
        holidaysSet.add(LocalDate.of(2025,1,6));
        holidaysSet.add(LocalDate.of(2025,1,7));
        holidaysSet.add(LocalDate.of(2025,1,8));
        holidaysSet.add(LocalDate.of(2025,3,8));
        holidaysSet.add(LocalDate.of(2025,5,1));
        holidaysSet.add(LocalDate.of(2025,5,2));
        holidaysSet.add(LocalDate.of(2025,5,8));
        holidaysSet.add(LocalDate.of(2025,5,9));
        holidaysSet.add(LocalDate.of(2025,6,12));
        holidaysSet.add(LocalDate.of(2025,6,13));
        holidaysSet.add(LocalDate.of(2025,11,3));
        holidaysSet.add(LocalDate.of(2025,11,4));
        holidaysSet.add(LocalDate.of(2025,12,31));
    }

    public boolean isHoliday(LocalDate localDate)
    {
        return holidaysSet.contains(localDate);
    }

}
