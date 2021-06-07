package ru.otus.homework.domain;

public class TestResult {
    private final Person person;
    private final int questionCreditCount;
    private final int countCorrectAnswers;

    public TestResult(Person person, int questionCreditCount, int countCorrectAnswers) {
        this.person = person;
        this.questionCreditCount = questionCreditCount;
        this.countCorrectAnswers = countCorrectAnswers;
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
}
