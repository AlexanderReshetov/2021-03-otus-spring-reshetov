package ru.otus.homework.dao;

import ru.otus.homework.dao.exception.QuestionReadException;
import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll() throws QuestionReadException;
}
