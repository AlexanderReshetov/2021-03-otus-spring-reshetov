package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Question;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:application.properties")
@Component
public class QuestionDaoImpl implements QuestionDao {
    private final String csvPath;

    public QuestionDaoImpl(@Value("${question.path}") String csvPath) {
        this.csvPath = csvPath;
    }

    public List<Question> findAll() throws QuestionReadException {
        try (Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(csvPath).toURI()));
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
