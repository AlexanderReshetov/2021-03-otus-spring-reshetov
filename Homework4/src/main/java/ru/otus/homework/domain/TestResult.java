package ru.otus.homework.domain;

public class TestResult {
    private final Person person;
    private final int questionCreditCount;
    private final int countCorrectAnswers;
    private final int countQuestions;

    public TestResult(Person person, int questionCreditCount, int countCorrectAnswers, int countQuestions) {
        this.person = person;
        this.questionCreditCount = questionCreditCount;
        this.countCorrectAnswers = countCorrectAnswers;
        this.countQuestions = countQuestions;
    }

    public Person getPerson() {
        return person;
    }

    public int getQuestionCreditCount() {
        return questionCreditCount;
    }

    public int getCountCorrectAnswers() {
        return countCorrectAnswers;
    }

    public int getCountQuestions() {
        return countQuestions;
    }
}
