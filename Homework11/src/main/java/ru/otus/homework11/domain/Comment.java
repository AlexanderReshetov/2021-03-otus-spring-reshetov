package ru.otus.homework11.domain;

import javax.persistence.*;

@Entity
@NamedEntityGraph(name = "Comment.book", attributeNodes = @NamedAttributeNode("book"))
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
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
