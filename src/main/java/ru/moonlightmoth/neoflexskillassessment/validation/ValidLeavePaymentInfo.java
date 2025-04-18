package ru.moonlightmoth.neoflexskillassessment.validation;

import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LeavePaymentInfoValidator.class)
public @interface ValidLeavePaymentInfo {
    String message() default "Invalid search range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
