package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.dao.exception.QuestionReadException;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoCsv implements QuestionDao {
    private final String csvPath;

    @Autowired
    public QuestionDaoCsv(QuestionConfig questionConfig) {
        this.csvPath = questionConfig.getLocalizedFileName();
    }

    public List<Question> findAll() throws QuestionReadException {
        try (Reader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + csvPath)));
             CSVReader csvReader = new CSVReader(reader);){
            List<String[]> list = csvReader.readAll();
            List<Question> questionList = new ArrayList<Question>();
            for (String[] strings : list) {
                questionList.add(new Question(strings[0], strings[1]));
            }
            return questionList;
        }
        catch(Exception e) {
            throw new QuestionReadException("Question file reading error!", e);
        }
    }
}
