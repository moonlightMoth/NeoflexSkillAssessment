package ru.moonlightmoth.neoflexskillassessment.validation;

import ru.moonlightmoth.neoflexskillassessment.exceptions.InvalidLeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

public class LeavePaymentInfoValidator implements ConstraintValidator<ValidLeavePaymentInfo, LeavePaymentInfo> {

    @Override
    public boolean isValid(LeavePaymentInfo leavePaymentInfo, ConstraintValidatorContext ctx) {

        double avgSalary = leavePaymentInfo.getAvgSalary();
        int vacationLength = leavePaymentInfo.getVacationLength();
        LocalDate startDate = leavePaymentInfo.getStartDate();
        LocalDate endDate = leavePaymentInfo.getEndDate();

        // avgSalary must be given
        if (avgSalary <= 0)
            throw new InvalidLeavePaymentInfo("avgSalary must be > 0");

        if (vacationLength <= 0 && (startDate == null || endDate == null))
            throw new InvalidLeavePaymentInfo(
                    "Either vacationLength must be > 0 or both startDate and endDate present");

        if (vacationLength > 0 && (startDate != null || endDate != null))
            throw new InvalidLeavePaymentInfo(
                    "Either vacationLength must be > 0 or both startDate and endDate present");

        if (startDate != null && startDate.isAfter(endDate))
            throw new InvalidLeavePaymentInfo(
                    "startDate must be after endDate");


        return true;
    }
}
