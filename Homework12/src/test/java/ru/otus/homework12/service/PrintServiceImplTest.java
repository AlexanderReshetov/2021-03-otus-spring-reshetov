package ru.otus.homework12.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис вывода на консоль должен")
public class PrintServiceImplTest {
    @Mock
    private PrintStream printStream;
    @InjectMocks
    private PrintServiceImpl printService;

    @Test
    @DisplayName("выводить строку")
    void shouldPrintln() {
        printService.println("TestString");

        verify(printStream).println("TestString");
    }
}
