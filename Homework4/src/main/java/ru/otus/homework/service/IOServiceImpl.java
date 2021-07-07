package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.service.exception.IOServiceException;

import java.io.*;

@Service
public class IOServiceImpl implements IOService {
    private final PrintStream out;
    private final BufferedReader reader;

    @Autowired
    public IOServiceImpl(@Value("#{ T(java.lang.System).in}") InputStream in,
                         @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    public BufferedReader getReader() {
        return reader;
    }

    @Override
    public void print(String message) {
        out.print(message);
    }

    @Override
    public void println(String message) {
        out.println(message);
    }

    @Override
    public String readLine() {
        try {
            return getReader().readLine();
        }
        catch (IOException e) {
            throw new IOServiceException("Data input error!", e);
        }
    }
}
