package com.moonlightmoth.neoflexskillassessment.util.logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;
import java.io.PrintStream;

public interface Logger {

    void log(String s);
}
