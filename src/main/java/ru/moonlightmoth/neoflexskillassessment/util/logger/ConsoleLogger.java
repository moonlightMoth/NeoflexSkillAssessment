package ru.moonlightmoth.neoflexskillassessment.util.logger;


import java.io.PrintStream;


public class ConsoleLogger implements Logger {

    final private PrintStream ps;

    public ConsoleLogger()
    {
        ps = System.out;
    }

    @Override
    public void log(String s)
    {
        ps.println(s);
    }

}
