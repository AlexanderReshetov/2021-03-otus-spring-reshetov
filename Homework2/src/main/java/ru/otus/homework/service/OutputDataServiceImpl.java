package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class OutputDataServiceImpl implements OutputDataService {
    private final IOService ioservice;

    @Autowired
    public OutputDataServiceImpl(IOService ioservice) {
        this.ioservice = ioservice;
    }

    public void outputData(Person person, int countCorrectAnswers, int questionCreditCount) {
        ioservice.println("You are known as " + person.getName() + " " + person.getSurname() + ".");
        ioservice.println("You gave " + countCorrectAnswers + " correct answers for 5 questions.");
        ioservice.println("You need " + questionCreditCount + " correct answers to get a credit.");
        if (countCorrectAnswers >= questionCreditCount) { ioservice.println("Congratulations! You get a credit!"); }
        else { ioservice.println("Don't be upset, but you didn't get a credit :("); }
    }
}
