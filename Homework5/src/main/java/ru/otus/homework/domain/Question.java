package ru.otus.homework.domain;

public class Question {
    private final String text;
    private final String answer;
    private String userAnswer;

    public Question(String text, String answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public boolean equals(Object object) {
        Question question = (Question) object;
        return ((text.equals(question.text)) && (answer.equals(question.answer)));
    }

}
