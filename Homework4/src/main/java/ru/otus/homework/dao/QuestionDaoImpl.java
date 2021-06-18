package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.homework.config.LocaleConfig;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Question;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {
    private final String csvPath;

    @Autowired
    public QuestionDaoImpl(QuestionConfig questionConfig, LocaleConfig localeConfig) {
        if (localeConfig.getName().equals("")) {
            this.csvPath = questionConfig.getFile().getName() + "." + questionConfig.getFile().getExtension();
        }
        else {
            this.csvPath = questionConfig.getFile().getName() + "_" + localeConfig.getName() + "." + questionConfig.getFile().getExtension();
        }
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
