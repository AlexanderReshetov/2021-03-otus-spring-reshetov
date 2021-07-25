package ru.otus.homework.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.MessageSourceIOService;
import ru.otus.homework.service.TestResultOutputService;
import ru.otus.homework.service.TestService;

import java.util.List;

@ShellComponent
public class ShellModeCommands {
    private final int questionCreditCount;
    private final TestService testService;
    private final MessageSourceIOService messageSourceIOService;
    private final TestResultOutputService testResultOutputService;
    private List<Question> questionList;
    private Person person;
    private boolean isShellMode;

    public ShellModeCommands(QuestionConfig questionConfig, TestService testService, MessageSourceIOService messageSourceIOService, TestResultOutputService testResultOutputService) {
        this.questionCreditCount = questionConfig.getCredit().getCount();
        this.testService = testService;
        this.messageSourceIOService = messageSourceIOService;
        this.testResultOutputService = testResultOutputService;
        this.isShellMode = false;
    }

    public void activateShellMode() {
        messageSourceIOService.println("message.output.shell.activate");
        questionList = testService.getQuestionList();
        isShellMode = true;
    }

    @ShellMethod(key = {"p", "person"}, value = "Input your name as '<Lastname> <Firstname>'")
    @ShellMethodAvailability(value = "isShellModeAvailable")
    public void inputPerson(@ShellOption("lastname") String surname, @ShellOption("firstname") String name) {
        person = new Person(surname, name);
    }

    @ShellMethod(key = {"q next", "question next"}, value = "Answer next question")
    @ShellMethodAvailability(value = "isPersonDataAvailable")
    public void answerNextQuestion() {
        testService.answerNextQuestion(questionList);
    }

    @ShellMethod(key = {"q", "question"}, value = "Answer question by number (ask the first question by number 1)")
    @ShellMethodAvailability(value = "isPersonDataAvailable")
    public void answerQuestionByNumber(@ShellOption("number") int number) {
        number--;
        testService.answerQuestionByNumber(questionList, number);
    }

    @ShellMethod(key = {"f", "finish"}, value = "Finish the test and know your results")
    @ShellMethodAvailability(value = "isPersonDataAvailable")
    public void finishTest() {
        testResultOutputService.output(testService.testFromList(person, questionList, questionCreditCount));
    }

    public Availability isShellModeAvailable() {
        return isShellMode
                ? Availability.available()
                : Availability.unavailable("Command is available only in shell mode (execute 'run test shell' first, please)");
    }

    public Availability isPersonDataAvailable() {
        return isShellModeAvailable().isAvailable() && (person != null)
                ? Availability.available()
                : Availability.unavailable("Command is available only for authorized person (run 'person' first, please)");
    }
}
