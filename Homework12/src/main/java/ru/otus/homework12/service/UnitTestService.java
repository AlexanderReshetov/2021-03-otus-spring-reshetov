package ru.otus.homework12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import java.util.Random;

@Service
public class UnitTestService {
    private final PrintService printService;

    @Autowired
    public UnitTestService(PrintService printService) {
        this.printService = printService;
    }

    public UnitTestResultDto test(UnitDto unitDto) {
        printService.println("Now unit is on unit testing...");
        printService.println("Name - " + unitDto.getName());
        printService.println("Text - " + unitDto.getText());
        final boolean result = new Random().nextBoolean();
        final String resultString = result ? "OK" : "FAILED";
        printService.println("Result - " + resultString);
        return new UnitTestResultDto(unitDto, result);
    }
}
