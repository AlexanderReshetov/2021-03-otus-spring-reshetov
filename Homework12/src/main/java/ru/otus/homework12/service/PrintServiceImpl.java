package ru.otus.homework12.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PrintServiceImpl implements PrintService {
    private final PrintStream out;

    public PrintServiceImpl(@Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
    }

    public void println(String message) {
        out.println(message);
    }
}
