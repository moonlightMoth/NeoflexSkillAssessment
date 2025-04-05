package ru.moonlightmoth.neoflexskillassessment.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class LeavePaymentInfoValidator implements ConstraintValidator<ValidLeavePaymentInfo, LeavePaymentInfo> {

    @Override
    public boolean isValid(LeavePaymentInfo value, ConstraintValidatorContext context) {
        try {
            int avgSalary = value.getAvgSalary();
            int vacationLength = value.getVacationLength();
            LocalDate startDate = value.getStartDate();
            LocalDate endDate = value.getEndDate();

            // avgSalary must be given
            if (avgSalary <= 0)
                return false;

            if (startDate == null && endDate == null)
            {
                // if given no dates, vacationLength must be >=0
                if (vacationLength == 0)
                    return false;
            }
            else if (startDate != null && endDate != null)
            {
                // if given both startDate and endDate, vacationLength must be absent
                if (vacationLength > 0)
                    return false;
                if (startDate.isAfter(endDate))
                    return false;
            }
            else // if given only one of: startDate, endDate
            {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
