package ru.moonlightmoth.neoflexskillassessment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.context.properties.bind.Name;

import javax.validation.constraints.Min;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

@Data
@Builder
@AllArgsConstructor
public class LeavePaymentResponse {

    @Builder.Default
    private String message = "The amount of leave payment for employee";
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private double leavePayment;
}
