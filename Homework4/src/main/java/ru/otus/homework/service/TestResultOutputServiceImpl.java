package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;

@Service
public class TestResultOutputServiceImpl implements TestResultOutputService {
    private final MessageSourceIOService messageSourceIOService;

    @Autowired
    public TestResultOutputServiceImpl(MessageSourceIOService messageSourceIOService) {
        this.messageSourceIOService = messageSourceIOService;
    }

    public void output(TestResult testResult) {
        messageSourceIOService.println("message.output.person",
                testResult.getPerson().getName(), testResult.getPerson().getSurname());
        messageSourceIOService.println("message.output.answer.correct.count",
                testResult.getCountCorrectAnswers(), testResult.getCountQuestions());
        messageSourceIOService.println("message.output.answer.credit.count",
                testResult.getQuestionCreditCount());
        if (testResult.getCountCorrectAnswers() >= testResult.getQuestionCreditCount()) {
            messageSourceIOService.println("message.output.congratulation");
        }
        else {
            messageSourceIOService.println("message.output.comfort");
        }
    }
}
