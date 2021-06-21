package ru.otus.homework.domain;

public class Question {
    private final String text;
    private final String answer;

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

    @Override
    public boolean equals(Object object) {
        Question question = (Question) object;
        return ((text.equals(question.text)) && (answer.equals(question.answer)));
    }

}
