package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;

@Service
public class TestResultOutputServiceImpl implements TestResultOutputService {
    private final IOService ioservice;
    private final MessageSourceService messageSourceService;

    @Autowired
    public TestResultOutputServiceImpl(IOService ioservice, MessageSourceService messageSourceService) {
        this.ioservice = ioservice;
        this.messageSourceService = messageSourceService;
    }

    public void output(TestResult testResult) {
        ioservice.println(messageSourceService.getMessage("message.output.person",
                new Object[]{testResult.getPerson().getName(), testResult.getPerson().getSurname()}));
        ioservice.println(messageSourceService.getMessage("message.output.answer.correct.count",
                new Object[]{testResult.getCountCorrectAnswers(), testResult.getCountQuestions()}));
        ioservice.println(messageSourceService.getMessage("message.output.answer.credit.count",
                new Object[]{testResult.getQuestionCreditCount()}));
        if (testResult.getCountCorrectAnswers() >= testResult.getQuestionCreditCount()) {
            ioservice.println(messageSourceService.getMessage("message.output.congratulation", null));
        }
        else {
            ioservice.println(messageSourceService.getMessage("message.output.comfort", null));
        }
    }
}
