package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.homework.dao.TestRunnerIOException;
import ru.otus.homework.dao.QuestionReadException;
import ru.otus.homework.service.TestRunnerService;

@ComponentScan
public class Main {
    public static void main(String[] args) throws QuestionReadException, TestRunnerIOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestRunnerService testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}
