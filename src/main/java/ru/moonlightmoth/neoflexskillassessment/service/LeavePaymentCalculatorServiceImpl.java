package ru.moonlightmoth.neoflexskillassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentResponse;
import ru.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import ru.moonlightmoth.neoflexskillassessment.util.LeavePaymentDecimalFormat;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class LeavePaymentCalculatorServiceImpl implements LeavePaymentCalculatorService {

    private static final double AVERAGE_MONTH_LENGTH = 29.3;

    @Autowired
    private LeavePaymentDecimalFormat leavePaymentDecimalFormat;

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Override
    public LeavePaymentResponse calculate(@Valid LeavePaymentInfo leavePaymentInfo)
    {
        return leavePaymentInfo.getVacationLength() == 0 ?
                calculateWithDates(leavePaymentInfo) :
                calculateWithoutDates(leavePaymentInfo);
    }

    private LeavePaymentResponse calculateWithoutDates(LeavePaymentInfo leavePaymentInfo)
    {
        double avgSalary = leavePaymentInfo.getAvgSalary();
        int vacationLength = leavePaymentInfo.getVacationLength();

        double leavePayment = (avgSalary / AVERAGE_MONTH_LENGTH) * vacationLength;

        leavePayment = Double.parseDouble(leavePaymentDecimalFormat.format(leavePayment));

        return LeavePaymentResponse.builder().leavePayment(leavePayment).build();
    }

    private LeavePaymentResponse calculateWithDates(LeavePaymentInfo leavePaymentInfo)
    {
        double avgSalary = leavePaymentInfo.getAvgSalary();
        LocalDate startDate = leavePaymentInfo.getStartDate();
        LocalDate endDate = leavePaymentInfo.getEndDate();

        int numberOfPaidDays = 0;

        while (startDate.isBefore(endDate)) // all range except last day
        {
            if (isPaidDayoff(startDate))
                numberOfPaidDays++;
            startDate = startDate.plusDays(1);
        }

        if (isPaidDayoff(endDate)) // last day of range is included
            numberOfPaidDays++;

        double leavePayment = (avgSalary / AVERAGE_MONTH_LENGTH) * numberOfPaidDays;

        leavePayment = Double.parseDouble(leavePaymentDecimalFormat.format(leavePayment));

        return LeavePaymentResponse.builder().leavePayment(leavePayment).build();
    }


    // check if LocalDate object represents paid day off. holidays and weekends are considered unpaid
    private boolean isPaidDayoff(LocalDate localDate)
    {
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY)
            return false;

        return !holidaysRepository.isHoliday(localDate);
    }
}
