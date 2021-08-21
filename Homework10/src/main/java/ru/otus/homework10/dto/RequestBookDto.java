package ru.otus.homework10.dto;

public class RequestBookDto {
    private final String name;
    private final Long authorId;
    private final Long genreId;

    public RequestBookDto(String name, Long authorId, Long genreId) {
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getGenreId() {
        return genreId;
    }
}
