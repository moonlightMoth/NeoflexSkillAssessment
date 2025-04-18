package ru.moonlightmoth.neoflexskillassessment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentResponse;


import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
public class LeavePaymentServiceTest {

    @Autowired
    private LeavePaymentCalculatorService leavePaymentCalculatorService;

    @Autowired
    private JacksonTester<LeavePaymentResponse> jsonTester;

    @Test
    void calculationTest() throws IOException
    {
        LeavePaymentInfo leavePaymentInfo = LeavePaymentInfo.builder().avgSalary(80000).vacationLength(15).build();
        LeavePaymentInfo leavePaymentInfo1 = LeavePaymentInfo.builder().avgSalary(80000)
                .startDate(LocalDate.of(2022,12,12))
                .endDate(LocalDate.of(2023,1,12)).build();
        LeavePaymentInfo leavePaymentInfo2 = LeavePaymentInfo.builder().avgSalary(80000)
                .startDate(LocalDate.of(2025,1,1))
                .endDate(LocalDate.of(2025,1,3)).build();

        LeavePaymentResponse leavePaymentResponse = leavePaymentCalculatorService.calculate(leavePaymentInfo);
        LeavePaymentResponse leavePaymentResponse1 = leavePaymentCalculatorService.calculate(leavePaymentInfo1);
        LeavePaymentResponse leavePaymentResponse2 = leavePaymentCalculatorService.calculate(leavePaymentInfo2);

        JsonContent<LeavePaymentResponse> json = jsonTester.write(leavePaymentResponse);
        JsonContent<LeavePaymentResponse> json1 = jsonTester.write(leavePaymentResponse1);
        JsonContent<LeavePaymentResponse> json2 = jsonTester.write(leavePaymentResponse2);

        assertThat(json).extractingJsonPathStringValue("$.leavePayment").isEqualTo("40955.63");
        assertThat(json1).extractingJsonPathStringValue("$.leavePayment").isEqualTo("65529.01");
        assertThat(json2).extractingJsonPathStringValue("$.leavePayment").isEqualTo("0.0");



    }
}
