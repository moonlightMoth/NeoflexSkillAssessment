package ru.moonlightmoth.neoflexskillassessment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LeavePaymentInfoValidator.class)
public @interface ValidLeavePaymentInfo {
    String message() default "End date must be after start date, avgSalary must be > 0, " +
            "must be true: " +
            "vacationLength null && startDate not null && endDate not null || " +
            "vacationLength > 0 && startDate null && endDate null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}