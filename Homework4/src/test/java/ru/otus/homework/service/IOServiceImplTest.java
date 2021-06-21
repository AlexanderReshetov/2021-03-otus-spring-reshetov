package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.service.exception.IOServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис ввода/вывода IOServiceImpl должен")
public class IOServiceImplTest {
    @Mock
    private InputStream inputStream;
    @Mock
    private PrintStream printStream;
    @Mock
    private BufferedReader reader;
    @InjectMocks
    private IOServiceImpl ioService;

    private static final String TEST_STRING = "TestString";

    @Test
    @DisplayName("вывести строку с переводом строки на консоль")
    void shouldExecutePrintln() {

        ioService.println(TEST_STRING);

        verify(printStream).println(TEST_STRING);
    }

    @Test
    @DisplayName("вывести строку без перевода строки на консоль")
    void shouldExecutePrint() {
        ioService.print(TEST_STRING);

        verify(printStream).print(TEST_STRING);
    }

    @Test
    @DisplayName("прочесть строку с консоли")
    void shouldCorrectReadLine() throws IOException, IOServiceException {
        IOServiceImpl ioServiceSpy = spy(ioService);
        given(ioServiceSpy.getReader()).willReturn(reader);
        ioServiceSpy.readLine();

        verify(reader).readLine();
    }

    @Test
    @DisplayName("выбросить IOServiceException, если прочесть строку с консоли не удалось")
    void shouldThrowExceptionWhenIncorrectReadLine() throws IOException, IOServiceException{
        IOServiceImpl ioServiceSpy = spy(ioService);
        given(ioServiceSpy.getReader()).willReturn(reader);
        given(reader.readLine()).willThrow(IOException.class);

        assertThrows(IOServiceException.class, () -> ioServiceSpy.readLine());
    }

}
