package com.moonlightmoth.neoflexskillassessment;

import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import com.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import com.moonlightmoth.neoflexskillassessment.util.ConsoleLogger;
import com.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan(basePackages = "com.moonlightmoth.neoflexskillassessment")
public class AppConfig {

    @Bean("paramsParser")
    public ParamsParser paramsParser()
    {
        return new ParamsParser();
    }

    @Bean
    @DependsOn("paramsParser")
    public HolidaysRepository holidaysRepository(ParamsParser paramsParser)
    {
        return new HolidaysRepository(paramsParser);
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
