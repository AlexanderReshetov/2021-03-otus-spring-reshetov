package ru.otus.homework.service;

public interface IOService {
    public void print(String message);
    public void println(String message);
    public String readLine() throws IOServiceException;
}
