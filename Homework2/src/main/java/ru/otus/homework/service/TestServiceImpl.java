package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{
    private final QuestionDao questionDao;
    private final IOService ioservice;

    @Autowired
    public TestServiceImpl(QuestionDao questionDao, IOService ioservice) {
        this.questionDao = questionDao;
        this.ioservice = ioservice;
    }

    public int Test() throws TestServiceException {
        int countCorrectAnswers = 0;
        List<Question> questionList = questionDao.findAll();
        for (Question question: questionList
        ) {
            ioservice.println("Question: " + question.getText());
            ioservice.print("Answer: ");
            try {
                String answer = ioservice.readLine();
                if (answer.equals(question.getAnswer())) { countCorrectAnswers++; }
            }
            catch(IOServiceException e) {
                throw new TestServiceException("Answering error!", e);
            }

        }
        return countCorrectAnswers;
    }
}
