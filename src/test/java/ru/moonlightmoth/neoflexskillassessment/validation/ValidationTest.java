package ru.moonlightmoth.neoflexskillassessment.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.moonlightmoth.neoflexskillassessment.exceptions.InvalidLeavePaymentInfoException;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    private static LeavePaymentInfoValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new LeavePaymentInfoValidator();
    }

    @Test
    void leavePaymentInfoInvalidValidationTest() {

        List<LeavePaymentInfo> invalidList = new ArrayList<>();
        invalidList.add(LeavePaymentInfo.builder().avgSalary(-1).vacationLength(12).build());
        invalidList.add(LeavePaymentInfo.builder().avgSalary(12).vacationLength(-1).build());
        invalidList.add(LeavePaymentInfo.builder().avgSalary(12)
                .startDate(LocalDate.of(2022, 2, 2)).build());
        invalidList.add(LeavePaymentInfo.builder().avgSalary(12)
                .endDate(LocalDate.of(2022, 2, 2)).build());
        invalidList.add(LeavePaymentInfo.builder().avgSalary(12).vacationLength(12)
                .startDate(LocalDate.of(2022, 2, 2))
                .endDate(LocalDate.of(2022, 2, 2)).build());
        invalidList.add(LeavePaymentInfo.builder().avgSalary(12)
                .startDate(LocalDate.of(2022, 2, 3))
                .endDate(LocalDate.of(2022, 2, 2)).build());

        invalidList.forEach(leavePaymentInfo -> assertThrows(InvalidLeavePaymentInfoException.class,
                () -> validator.isValid(leavePaymentInfo, null)));
    }

    @Test
    void leavePaymentInfovalidValidationTest() {

        List<LeavePaymentInfo> validList = new ArrayList<>();
        validList.add(LeavePaymentInfo.builder().avgSalary(12).vacationLength(12).build());
        validList.add(LeavePaymentInfo.builder().avgSalary(12)
                .startDate(LocalDate.of(2022, 2, 2))
                .endDate(LocalDate.of(2022, 2, 2)).build());
        validList.add(LeavePaymentInfo.builder().avgSalary(12)
                .startDate(LocalDate.of(2022, 1, 2))
                .endDate(LocalDate.of(2022, 2, 2)).build());


        validList.forEach(leavePaymentInfo -> assertTrue(validator.isValid(leavePaymentInfo, null)));
    }
}
