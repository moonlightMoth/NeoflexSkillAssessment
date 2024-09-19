package com.moonlightmoth.neoflexskillassessment.integretion;

import com.moonlightmoth.neoflexskillassessment.integretion.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.integretion.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.integretion.util.ConsoleLogger;
import com.moonlightmoth.neoflexskillassessment.integretion.util.ParamsParser;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {

    @Bean
    public ParamsParser paramsParser()
    {
        return new ParamsParser();
    }

    @Bean
    @DependsOn("paramsParser")
    public HolidaysRepository holidaysRepository(ParamsParser paramsParser)
    {
        return new HolidaysRepository(paramsParser, new ClassPathResource("holidays"));
    }

    @Bean
    public LeavePayCalculator leavePayCalculator(HolidaysRepository holidaysRepository)
    {
        return new LeavePayCalculator(holidaysRepository);
    }

    @Bean
    public ConsoleLogger consoleLogger()
    {
        return new ConsoleLogger();
    }
}
