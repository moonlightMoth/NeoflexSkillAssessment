package com.moonlightmoth.neoflexskillassessment.integration;

import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.unit.TestLogger;
import com.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import com.moonlightmoth.neoflexskillassessment.util.logger.ConsoleLogger;
import com.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
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
