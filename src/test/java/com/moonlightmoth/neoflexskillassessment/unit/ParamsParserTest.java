package com.moonlightmoth.neoflexskillassessment.unit;

import com.moonlightmoth.neoflexskillassessment.integretion.util.ParamsParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class ParamsParserTest {

    @Test
    void parseDoubleTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertEquals(paramsParser.parseDouble("10.123"), 10.123);
        assertEquals(paramsParser.parseDouble("-10.123"), -10.123);
        assertEquals(paramsParser.parseDouble("0.0"), 0.0);
        assertEquals(paramsParser.parseDouble("-0.0"), -0.0);

        assertThrows(NumberFormatException.class, () -> paramsParser.parseDouble("asd"));
        assertThrows(NumberFormatException.class, () -> paramsParser.parseDouble(""));
        assertThrows(NumberFormatException.class, () -> paramsParser.parseDouble("123-123"));
    }

    @Test
    void isValidDoubleParamTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertTrue(paramsParser.isValidDoubleParam("10.123"));
        assertTrue(paramsParser.isValidDoubleParam("0.23"));
        assertTrue(paramsParser.isValidDoubleParam("123123"));

        assertFalse(paramsParser.isValidDoubleParam("-10.123"));
        assertFalse(paramsParser.isValidDoubleParam("0.0"));
        assertFalse(paramsParser.isValidDoubleParam("-0.0"));
        assertFalse(paramsParser.isValidDoubleParam("asd"));
        assertFalse(paramsParser.isValidDoubleParam(""));
        assertFalse(paramsParser.isValidDoubleParam("123-123"));

    }

    @Test
    void parseIntTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertEquals(paramsParser.parseInt("123"), 123);
        assertEquals(paramsParser.parseInt("-123"), -123);

        assertThrows(NumberFormatException.class, () -> paramsParser.parseInt("asd"));
        assertThrows(NumberFormatException.class, () -> paramsParser.parseInt("-10.123"));
        assertThrows(NumberFormatException.class, () -> paramsParser.parseInt(""));
        assertThrows(NumberFormatException.class, () -> paramsParser.parseInt("0.0"));

        assertThrows(NumberFormatException.class, () -> paramsParser.parseInt("3000000000"));
    }

    @Test
    void isValidIntParamTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertTrue(paramsParser.isValidIntParam("123"));
        assertTrue(paramsParser.isValidIntParam("124"));
        assertTrue(paramsParser.isValidIntParam("523"));
        assertTrue(paramsParser.isValidIntParam("1"));

        assertFalse(paramsParser.isValidIntParam("-123"));
        assertFalse(paramsParser.isValidIntParam("0"));
        assertFalse(paramsParser.isValidIntParam("0.0"));
        assertFalse(paramsParser.isValidIntParam("-0.0"));
        assertFalse(paramsParser.isValidIntParam("asd"));
        assertFalse(paramsParser.isValidIntParam(""));
        assertFalse(paramsParser.isValidIntParam("123-123"));

    }

    @Test
    void parseLocalDateTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertEquals(paramsParser.parseLocalDate("12.12.12"), LocalDate.of(2012, Month.DECEMBER, 12));
        assertEquals(paramsParser.parseLocalDate("04.01.03"), LocalDate.of(2003, Month.JANUARY, 4));
        assertEquals(paramsParser.parseLocalDate("04.01.00"), LocalDate.of(2000, Month.JANUARY, 4));
        assertEquals(paramsParser.parseLocalDate("29.02.24"), LocalDate.of(2024, Month.FEBRUARY, 29));

        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate("asd"));
        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate("-10.123"));
        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate(""));
        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate("32.12.24"));
        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate("30.02.24"));
        assertThrows(DateTimeParseException.class, () -> paramsParser.parseLocalDate("29.02.23"));

    }

    @Test
    void isValidLocalDateParamTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertTrue(paramsParser.isValidLocalDateParam("12.12.12"));
        assertTrue(paramsParser.isValidLocalDateParam("04.01.03"));
        assertTrue(paramsParser.isValidLocalDateParam("04.01.00"));
        assertTrue(paramsParser.isValidLocalDateParam("29.02.24"));

        assertFalse(paramsParser.isValidLocalDateParam("asd"));
        assertFalse(paramsParser.isValidLocalDateParam("-10.123"));
        assertFalse(paramsParser.isValidLocalDateParam(""));
        assertFalse(paramsParser.isValidLocalDateParam("32.12.24"));
        assertFalse(paramsParser.isValidLocalDateParam("30.02.24"));
        assertFalse(paramsParser.isValidLocalDateParam("29.02.23"));
    }

    @Test
    void validateParamsTest()
    {
        ParamsParser paramsParser = new ParamsParser();

        assertEquals(paramsParser.validateParams("12", null, null, null), ParamsParser.INVALID_PARAMS);
        assertEquals(paramsParser.validateParams("12", null, null, "12.12.12"), ParamsParser.INVALID_PARAMS);
        assertEquals(paramsParser.validateParams("12", null, "12.12.12", null), ParamsParser.INVALID_PARAMS);

        assertEquals(paramsParser.validateParams("12", null, "12.12.12", "12.12.12"), ParamsParser.FULL_FORM);
        assertEquals(paramsParser.validateParams("12", "12", null, null), ParamsParser.SHORT_FORM);

        assertEquals(paramsParser.validateParams("12", "12", null, "12.12.12"), ParamsParser.INVALID_PARAMS);
        assertEquals(paramsParser.validateParams("12", "12", "12.12.12", null), ParamsParser.INVALID_PARAMS);
        assertEquals(paramsParser.validateParams("12", "12", "12.12.12", "12.12.12"), ParamsParser.INVALID_PARAMS);

        assertEquals(paramsParser.validateParams(null, null, "12.12.12", "12.12.12"), ParamsParser.INVALID_PARAMS);
        assertEquals(paramsParser.validateParams(null, "12", null, null), ParamsParser.INVALID_PARAMS);

        assertEquals(paramsParser.validateParams("12", null, "13.12.12", "12.12.12"), ParamsParser.INVALID_PARAMS);

    }
}
