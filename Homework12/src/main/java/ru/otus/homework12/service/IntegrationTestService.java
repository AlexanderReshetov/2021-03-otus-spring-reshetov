package ru.otus.homework12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework12.dto.IntegrationTestResultDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import java.util.List;
import java.util.Random;

@Service
public class IntegrationTestService {
    private final PrintService printService;

    @Autowired
    public IntegrationTestService(PrintService printService) {
        this.printService = printService;
    }

    public IntegrationTestResultDto test(List<UnitTestResultDto> unitTestResultDtoList) {
        boolean unitTestingOk = true;
        printService.println("Now code is on integration testing...");
        for(UnitTestResultDto unitTestResultDto : unitTestResultDtoList) {
            printService.println("Name - " + unitTestResultDto.getUnit().getName());
            printService.println("Text - " + unitTestResultDto.getUnit().getText());
            String unitResultString = unitTestResultDto.getResult() ? "OK" : "FAILED";
            printService.println("Result - " + unitResultString);
            unitTestingOk = unitTestingOk && unitTestResultDto.getResult();
        }
        final boolean result = unitTestingOk && new Random().nextBoolean();
        String resultString = result ? "OK" : "FAILED";
        printService.println("Integration testing result - " + resultString);
        return new IntegrationTestResultDto(unitTestResultDtoList, result);
    }
}
