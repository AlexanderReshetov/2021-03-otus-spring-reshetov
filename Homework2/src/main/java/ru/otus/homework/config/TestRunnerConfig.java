package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoImpl;

@PropertySource("classpath:application.properties")
@Configuration
public class TestRunnerConfig {
    @Bean
    public QuestionDao questionDao(@Value("${question.path}") String csvPath) {return new QuestionDaoImpl(csvPath);}
}
