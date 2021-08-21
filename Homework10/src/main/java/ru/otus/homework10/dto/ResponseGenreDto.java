package ru.otus.homework10.dto;

import ru.otus.homework10.domain.Genre;

public class ResponseGenreDto {
    private final Long id;
    private final String name;

    public ResponseGenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ResponseGenreDto toDto(Genre genre) {
        return new ResponseGenreDto(genre.getId(), genre.getName());
    }
}
