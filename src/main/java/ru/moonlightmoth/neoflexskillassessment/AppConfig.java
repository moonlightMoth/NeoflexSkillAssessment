package ru.moonlightmoth.neoflexskillassessment;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import ru.moonlightmoth.neoflexskillassessment.util.LeavePayCalculator;
import ru.moonlightmoth.neoflexskillassessment.util.logger.ConsoleLogger;
import ru.moonlightmoth.neoflexskillassessment.util.ParamsParser;
import ru.moonlightmoth.neoflexskillassessment.util.logger.Logger;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import ru.moonlightmoth.neoflexskillassessment.validation.LeavePaymentInfoValidator;

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
