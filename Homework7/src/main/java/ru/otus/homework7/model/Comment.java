package ru.otus.homework7.model;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
    @Column(name = "text")
    String text;

    public Comment() {
    }

    public Comment(Book book, String text) {
        this.book = book;
        this.text = text;
    }

    public Comment(Long id, Book book, String text) {
        this.id = id;
        this.book = book;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getText() {
        return text;
    }
}
