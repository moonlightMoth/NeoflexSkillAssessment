package ru.moonlightmoth.neoflexskillassessment.integration;

import ru.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import ru.moonlightmoth.neoflexskillassessment.unit.TestLogger;
import ru.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import ru.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import ru.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;

import java.io.OutputStream;

@TestConfiguration
public class TestConfig {

    @Bean
    public ParamsParser paramsParser()
    {
        return new ParamsParser();
    }

    @Bean
    @DependsOn({"paramsParser", "consoleLogger"})
    public HolidaysRepository holidaysRepository()
    {
        return new HolidaysRepository(paramsParser(), new ClassPathResource("holidays_test_1"),testLogger());
    }

    @Bean
    @DependsOn("holidaysRepository")
    public LeavePayCalculator leavePayCalculator()
    {
        return new LeavePayCalculator(holidaysRepository());
    }

    @Bean
    public Logger testLogger()
    {
        return new TestLogger(OutputStream.nullOutputStream());
    }
}
