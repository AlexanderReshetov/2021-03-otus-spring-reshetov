package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDao questionDao = context.getBean(QuestionDao.class);
        List<Question> questionList = questionDao.findAll();
        for (Question question: questionList
             ) {
            System.out.println(question.getText());
        }
    }
}
