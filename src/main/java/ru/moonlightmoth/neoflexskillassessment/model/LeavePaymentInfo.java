package ru.moonlightmoth.neoflexskillassessment.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.moonlightmoth.neoflexskillassessment.validation.ValidLeavePaymentInfo;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidLeavePaymentInfo
@Builder
public class LeavePaymentInfo {
    private double avgSalary;
    private int vacationLength;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
}
