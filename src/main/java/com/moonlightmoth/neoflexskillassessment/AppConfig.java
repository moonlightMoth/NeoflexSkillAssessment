package com.moonlightmoth.neoflexskillassessment;

import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.util.logger.ConsoleLogger;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import com.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class AppConfig {

    @Bean
    public ParamsParser paramsParser()
    {
        return new ParamsParser();
    }

    @Bean
    @DependsOn({"paramsParser", "consoleLogger"})
    public HolidaysRepository holidaysRepository()
    {
        return new HolidaysRepository(paramsParser(), new ClassPathResource("holidays"), consoleLogger());
    }

    @Bean
    @DependsOn("holidaysRepository")
    public LeavePayCalculator leavePayCalculator()
    {
        return new LeavePayCalculator(holidaysRepository());
    }

    @Bean
    public Logger consoleLogger()
    {
        return new ConsoleLogger();
    }

}
