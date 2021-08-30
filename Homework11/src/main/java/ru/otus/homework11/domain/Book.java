package ru.otus.homework11.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedEntityGraph(name = "Book.fields", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Comment> comments;

    public Book() {
    }

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }
    public Book(Long id, String name, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}