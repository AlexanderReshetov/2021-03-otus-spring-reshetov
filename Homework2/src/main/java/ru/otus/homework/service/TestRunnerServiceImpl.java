package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class TestRunnerServiceImpl implements TestRunnerService{
    private final int questionCreditCount;
    private final PersonInputDataService personInputDataService;
    private final TestService testService;
    private final OutputDataService outputDataService;

    @Autowired
    public TestRunnerServiceImpl(@Value("${question.credit.count}") int questionCreditCount, PersonInputDataService personInputDataService, OutputDataService outputDataService, TestService testService) {
        this.questionCreditCount = questionCreditCount;
        this.personInputDataService = personInputDataService;
        this.outputDataService = outputDataService;
        this.testService = testService;
    }

    public void run() throws TestRunnerException {
        try {
            Person person = personInputDataService.inputData();
            int countCorrectAnswers = testService.Test();
            outputDataService.outputData(person, countCorrectAnswers, questionCreditCount);
        }
        catch(Exception e) {
            throw new TestRunnerException("Testing error!", e);
        }

    }
}
