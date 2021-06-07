package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.TestResult;

@Service
public class TestResultOutputServiceImpl implements TestResultOutputService {
    private final IOService ioservice;

    @Autowired
    public TestResultOutputServiceImpl(IOService ioservice) {
        this.ioservice = ioservice;
    }

    public void output(TestResult testResult) {
        ioservice.println("You are known as " + testResult.getPerson().getName() + " " + testResult.getPerson().getSurname() + ".");
        ioservice.println("You gave " + testResult.getCountCorrectAnswers() + " correct answers for 5 questions.");
        ioservice.println("You need " + testResult.getQuestionCreditCount() + " correct answers to get a credit.");
        if (testResult.getCountCorrectAnswers() >= testResult.getQuestionCreditCount()) {
            ioservice.println("Congratulations! You get a credit!");
        }
        else {
            ioservice.println("Don't be upset, but you didn't get a credit :(");
        }
    }
}
