package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.TestRunnerIOException;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionReadException;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class TestRunnerServiceImpl implements TestRunnerService{
    private final QuestionDao questionDao;

    @Value("${question.credit.count}")
    private int questionCreditCount;

    @Autowired
    public TestRunnerServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void run() throws QuestionReadException, TestRunnerIOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Your surname: ");
            Person person = new Person(reader.readLine());
            System.out.print("Your name: ");
            person.setName(reader.readLine());
            int countCorrectAnswers = 0;
            List<Question> questionList = questionDao.findAll();
            for (Question question: questionList
            ) {
                System.out.println("Question: " + question.getText());
                System.out.print("Answer: ");
                String answer = reader.readLine();
                if (answer.equals(question.getAnswer())) { countCorrectAnswers++; }
            }
            System.out.println("You are known as " + person.getName() + " " + person.getSurname() + ".");
            System.out.println("You gave " + countCorrectAnswers + " correct answers for 5 questions.");
            System.out.println("You need " + questionCreditCount + " correct answers to get a credit.");
            if (countCorrectAnswers >= questionCreditCount) { System.out.println("Congratulations! You get a credit!"); }
            else { System.out.println("Don't be upset, but you didn't get a credit :("); }
        }
        catch(IOException e) {
            throw new TestRunnerIOException(e.getMessage());
        }

    }
}
