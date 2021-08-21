package ru.otus.homework10.dto;

import ru.otus.homework10.domain.Comment;

public class ResponseCommentDto {
    private final Long id;
    private final Long bookId;
    private final String bookName;
    private final String text;

    public ResponseCommentDto(Long id, Long bookId, String bookName, String text) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getText() {
        return text;
    }

    public static ResponseCommentDto toDto(Comment comment) {
        return new ResponseCommentDto(comment.getId(), comment.getBook().getId(), comment.getBook().getName(), comment.getText());
    }
}
