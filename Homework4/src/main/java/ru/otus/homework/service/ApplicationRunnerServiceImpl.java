package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.otus.homework.service.exception.TestRunnerException;

@Service
public class ApplicationRunnerServiceImpl implements ApplicationRunner {
    private final TestRunnerService testRunnerService;

    @Autowired
    public ApplicationRunnerServiceImpl(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            testRunnerService.run();
        }
        catch (TestRunnerException e) {
            e.printStackTrace();
        }
    }
}
