package com.moonlightmoth.neoflexskillassessment.unit;

import com.moonlightmoth.neoflexskillassessment.util.logger.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class TestLogger implements Logger {

    final private PrintStream ps;

    public TestLogger(OutputStream os)
    {
        ps = new PrintStream(os);
    }

    @Override
    public void log(String log)
    {
        ps.println(log);
    }
}
