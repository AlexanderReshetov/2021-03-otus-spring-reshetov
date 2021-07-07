package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.PersonInputDataException;
import ru.otus.homework.service.exception.TestServiceException;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {
    private final int questionCreditCount;
    private final PersonInputDataService personInputDataService;
    private final TestService testService;
    private final TestResultOutputService testResultOutputService;
    private final MessageSourceIOService messageSourceIOService;

    @Autowired
    public TestRunnerServiceImpl(QuestionConfig questionConfig,
                                 PersonInputDataService personInputDataService,
                                 TestResultOutputService testResultOutputService,
                                 TestService testService,
                                 MessageSourceIOService messageSourceIOService) {
        this.questionCreditCount = questionConfig.getCredit().getCount();
        this.personInputDataService = personInputDataService;
        this.testResultOutputService = testResultOutputService;
        this.testService = testService;
        this.messageSourceIOService = messageSourceIOService;
    }

    public void run() {
        try {
            final Person person = personInputDataService.inputData();
            final TestResult testResult = testService.test(person, questionCreditCount);
            testResultOutputService.output(testResult);
        }
        catch(PersonInputDataException | TestServiceException e) {
            messageSourceIOService.println("message.output.error");
        }

    }
}
