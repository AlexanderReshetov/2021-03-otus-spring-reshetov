package ru.otus.homework12.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.gateway.UnitTestGateway;

import java.util.Arrays;

@Component
public class TestApplicationRunner implements ApplicationRunner {
    private final UnitTestGateway unitTestGateway;

    @Autowired
    public TestApplicationRunner(UnitTestGateway unitTestGateway) {
        this.unitTestGateway = unitTestGateway;
    }

    @Override
    public void run(ApplicationArguments args) {
        unitTestGateway.process(Arrays.asList(new UnitDto("Unit1", "Some code"), new UnitDto("Unit2", "Other code")));
    }
}
