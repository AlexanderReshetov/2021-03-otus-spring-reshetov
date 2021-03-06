package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {
    private final int questionCreditCount;
    private final PersonInputDataService personInputDataService;
    private final TestService testService;
    private final TestResultOutputService testResultOutputService;

    @Autowired
    public TestRunnerServiceImpl(@Value("${question.credit.count}") int questionCreditCount, PersonInputDataService personInputDataService, TestResultOutputService testResultOutputService, TestService testService) {
        this.questionCreditCount = questionCreditCount;
        this.personInputDataService = personInputDataService;
        this.testResultOutputService = testResultOutputService;
        this.testService = testService;
    }

    public void run() throws TestRunnerException {
        try {
            final Person person = personInputDataService.inputData();
            final TestResult testResult = testService.test(person, questionCreditCount);
            testResultOutputService.output(testResult);
        }
        catch(Exception e) {
            throw new TestRunnerException("Testing error!", e);
        }

    }
}
