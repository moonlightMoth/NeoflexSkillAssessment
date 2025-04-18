package ru.moonlightmoth.neoflexskillassessment.util;

import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
public class LeavePaymentDecimalFormat extends DecimalFormat {

    public LeavePaymentDecimalFormat()
    {
        super("#.##");
        this.setRoundingMode(RoundingMode.HALF_UP);
        this.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    }
}
