package ru.otus.homework10.dto;

public class RequestCommentDto {
    private final Long bookId;
    private final String text;

    public RequestCommentDto(Long bookId, String text) {
        this.bookId = bookId;
        this.text = text;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getText() {
        return text;
    }
}
