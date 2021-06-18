package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

@Service
public class TestRunnerServiceImpl implements TestRunnerService, ApplicationRunner {
    private final int questionCreditCount;
    private final PersonInputDataService personInputDataService;
    private final TestService testService;
    private final TestResultOutputService testResultOutputService;

    @Autowired
    public TestRunnerServiceImpl(QuestionConfig questionConfig, PersonInputDataService personInputDataService, TestResultOutputService testResultOutputService, TestService testService) {
        this.questionCreditCount = questionConfig.getCredit().getCount();
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

    @Override
    public void run(ApplicationArguments args) throws TestRunnerException {
        this.run();
    }
}
