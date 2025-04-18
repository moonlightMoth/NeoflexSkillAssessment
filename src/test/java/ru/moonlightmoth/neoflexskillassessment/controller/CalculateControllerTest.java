package ru.moonlightmoth.neoflexskillassessment.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculateControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void calculateValid() {
        ResponseEntity<LeavePaymentResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/calculate?avgSalary=12&vacationLength=12", LeavePaymentResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getLeavePayment()).isNotNull();

        ResponseEntity<LeavePaymentResponse> response1 = restTemplate.getForEntity(
                "http://localhost:" + port + "/calculate?avgSalary=12&startDate=2022-12-12&endDate=2022-12-12", LeavePaymentResponse.class);

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody().getLeavePayment()).isNotNull();

    }

    @Test
    void calculateInvalid() {
        ResponseEntity<LeavePaymentResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/calculate?avgSalary=12&vacationLength=-1", LeavePaymentResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ResponseEntity<LeavePaymentResponse> response1 = restTemplate.getForEntity(
                "http://localhost:" + port + "/calculate?avgSalary=12&endDate=2022-12-12", LeavePaymentResponse.class);

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
