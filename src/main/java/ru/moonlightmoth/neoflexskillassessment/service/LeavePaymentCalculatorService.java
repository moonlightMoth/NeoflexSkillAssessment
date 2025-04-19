package ru.moonlightmoth.neoflexskillassessment.service;

import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentInfo;
import ru.moonlightmoth.neoflexskillassessment.model.LeavePaymentResponse;

public interface LeavePaymentCalculatorService {

    LeavePaymentResponse calculate(LeavePaymentInfo leavePaymentInfo);
}
