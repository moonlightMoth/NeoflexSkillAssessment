package ru.moonlightmoth.neoflexskillassessment.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.moonlightmoth.neoflexskillassessment.validation.ValidLeavePaymentInfo;

import java.time.LocalDate;
import java.util.Objects;

@ValidLeavePaymentInfo
public class LeavePaymentInfo {
    private int avgSalary;
    private int vacationLength;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public LeavePaymentInfo()
    {
    }

    public LeavePaymentInfo(int avgSalary, int vacationLength, LocalDate startDate, LocalDate endDate)
    {
        this.avgSalary = avgSalary;
        this.vacationLength = vacationLength;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getAvgSalary()
    {
        return avgSalary;
    }

    public void setAvgSalary(int avgSalary)
    {
        this.avgSalary = avgSalary;
    }

    public int getVacationLength()
    {
        return vacationLength;
    }

    public void setVacationLength(int vacationLength)
    {
        this.vacationLength = vacationLength;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeavePaymentInfo that = (LeavePaymentInfo) o;
        return avgSalary == that.avgSalary && vacationLength == that.vacationLength && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(avgSalary, vacationLength, startDate, endDate);
    }

    @Override
    public String toString()
    {
        return "LeavePaymentInfo{" +
                "avgSalary=" + avgSalary +
                ", vacationLength=" + vacationLength +
                ", fromDate=" + startDate +
                ", dueToDate=" + endDate +
                '}';
    }
}
