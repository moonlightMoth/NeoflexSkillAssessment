package com.moonlightmoth.neoflexskillassessment.integration;

import com.moonlightmoth.neoflexskillassessment.NeoflexSkillAssessmentApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, // set up web env in tests
        classes = TestConfig.class) // inject test config for HolidaysRepository
public class HttpParamsTest {

    private static final String VALID_URI_0 = "http://localhost:8080/calculate?avgSalary=365000&vacationLength=1";


    private static final String VALID_URI_1 = "http://localhost:8080/calculate?avgSalary=142329&vacationLength=12"; //4679.31
    private static final String VALID_URI_2 = "http://localhost:8080/calculate?avgSalary=9875155.12&vacationLength=1"; //27055.22
    private static final String VALID_URI_3 = "http://localhost:8080/calculate?avgSalary=1542323.54&vacationLength=34"; //143668.50
    private static final String VALID_URI_4 = "http://localhost:8080/calculate?avgSalary=1&vacationLength=34"; //0.10
    private static final String VALID_URI_5 = "http://localhost:8080/calculate?avgSalary=0.01&vacationLength=100"; //0.01


    private static final String VALID_URI_6 = "http://localhost:8080/calculate?avgSalary=365&fromDate=01.01.24&dueToDate=05.01.24"; // 0.00
    private static final String VALID_URI_7 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=01.09.24&dueToDate=05.09.24"; //4000.00
    private static final String VALID_URI_8 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=01.01.24&dueToDate=01.02.24"; //18000.00
    private static final String VALID_URI_9 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=16.09.24&dueToDate=20.09.24"; //5000.00
    private static final String VALID_URI_10 = "http://localhost:8080/calculate?avgSalary=4321231.34&fromDate=01.01.24&dueToDate=05.03.24"; // 473559.60


    private static final String INVALID_URI_1 = "http://localhost:8080/calculate?avgSalary=365000&vacation=123";
    private static final String INVALID_URI_2 = "http://localhost:8080/calculate?avgSalary=&vacationLength=1";
    private static final String INVALID_URI_3 = "http://localhost:8080/calculate?avgSalary=0&vacationLength=1";
    private static final String INVALID_URI_4 = "http://localhost:8080/calculate?avgSalary=365000&vacationLength=0";
    private static final String INVALID_URI_5 = "http://localhost:8080/calculate?avgSalary=365000&vacationLength=1&fromDate=01.01.24&dueToDate=05.03.24";
    private static final String INVALID_URI_6 = "http://localhost:8080/calculate?savl=365000&vacationLength=1";
    private static final String INVALID_URI_7 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=01.01.224&dueToDate=05.03.24";
    private static final String INVALID_URI_8 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=29.02.23&dueToDate=05.03.23";
    private static final String INVALID_URI_9 = "http://localhost:8080/calculate?avgSalary=365000&fromDate=02.01.24&dueToDate=01.01.24";
    private static final String INVALID_URI_10 = "http://localhost:8080/calculate?avgSalary=365000&fromDate.01.224&dueToDate=05.03.24";


    private static final String NOT_FOUND_URI_1 = "http://localhost:8080/calculateasd";
    private static final String NOT_FOUND_URI_2 = "http://localhost:8080/";

    private static final String BAD_REQUEST_HINT =
            "Request parameters format:<br>" +
                    "avgSalary AND (vacationLength XOR (fromDate AND dueToDate))<br>" +
                    "avgSalary: double<br>" +
                    "vacationLength: int<br>" +
                    "fromDate: dd.MM.yy<br>" +
                    "dueToDate: dd.MM.yy <br>" + System.lineSeparator();

    @Test
    void controllerTest()
    {
        assertEquals(sendTestRequest(VALID_URI_0), "1000.00");
    }

    @Test
    void validUriTestsShortForm() //mostly rounding tests
    {
        assertEquals(sendTestRequest(VALID_URI_1), "4679.31");
        assertEquals(sendTestRequest(VALID_URI_2), "27055.22");
        assertEquals(sendTestRequest(VALID_URI_3), "143668.50");
        assertEquals(sendTestRequest(VALID_URI_4), "0.10");
        assertEquals(sendTestRequest(VALID_URI_5), "0.01");
    }

    @Test
    void validUriTestsFullForm()
    {
        assertEquals(sendTestRequest(VALID_URI_6), "0.00");
        assertEquals(sendTestRequest(VALID_URI_7), "4000.00");
        assertEquals(sendTestRequest(VALID_URI_8), "18000.00");
        assertEquals(sendTestRequest(VALID_URI_9), "5000.00");
        assertEquals(sendTestRequest(VALID_URI_10), "473559.60");
    }

    @Test
    void invalidUriTests()
    {
        assertEquals(sendTestRequest(INVALID_URI_1), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_2), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_3), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_4), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_5), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_6), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_7), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_8), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_9), BAD_REQUEST_HINT);
        assertEquals(sendTestRequest(INVALID_URI_10), BAD_REQUEST_HINT);
    }

    @Test
    void notFoundUriTests()
    {
        assertThrows(HttpClientErrorException.class, () -> sendTestRequest(NOT_FOUND_URI_1));
        assertThrows(HttpClientErrorException.class, () -> sendTestRequest(NOT_FOUND_URI_2));
    }

    //util method for sending request
    private String sendTestRequest(String uri)
    {
        String respStr;
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            respStr = response.getBody();
        }
        catch (HttpClientErrorException e)
        {
            respStr = e.getResponseBodyAsString();

            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw e;
        }
        return respStr;
    }
}
