package com.moonlightmoth.neoflexskillassessment.util;

import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class LeavePayCalculator {

    final private HolidaysRepository holidaysRepository;

    @Autowired
    public LeavePayCalculator(HolidaysRepository holidaysRepository)
    {
        this.holidaysRepository = holidaysRepository;
    }

    // calculate simple case: average day salary times leave days
    public double calculateShortForm(double avgSalary, int vacationLength)
    {
        double leavePayment = avgSalary * vacationLength / 365;
        double gap = leavePayment * 100 - (long) (leavePayment * 100);

        // cents are rounded in favor of the employee
        if (0.000000001 < gap && gap < 0.5)
            leavePayment += 0.01;

        return leavePayment;
    }

    //calculate complicated case: fetch holidays from HolidaysRepository and exclude these days from payment
    public double calculateFullForm(double avgSalary, LocalDate fromDate, LocalDate dueToDate)
    {
        int numberOfPaidDays = 0;

        while (fromDate.isBefore(dueToDate)) // all range except last day
        {
            if (isPaidDayoff(fromDate))
                numberOfPaidDays++;
            fromDate = fromDate.plusDays(1);
        }

        if (isPaidDayoff(dueToDate)) // last day of range is included
            numberOfPaidDays++;

        double leavePayment = avgSalary * numberOfPaidDays / 365;
        double gap = leavePayment * 100 - (long) (leavePayment * 100);

        // cents are rounded in favor of the employee
        if (0.000000001 < gap && gap < 0.5)
            leavePayment += 0.01;

        return leavePayment;
    }

    // check if LocalDate object represents paid day off. holidays and weekends are considered unpaid
    private boolean isPaidDayoff(LocalDate localDate)
    {
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY)
            return false;

        if (holidaysRepository.isHoliday(localDate))
            return false;

        return true;
    }
}
