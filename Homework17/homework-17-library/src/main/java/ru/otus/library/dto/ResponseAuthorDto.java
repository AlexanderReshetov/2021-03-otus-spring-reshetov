package ru.otus.library.dto;

import ru.otus.library.domain.Author;

public class ResponseAuthorDto {
    private final Long id;
    private final String name;

    public ResponseAuthorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ResponseAuthorDto toDto(Author author) {
        return new ResponseAuthorDto(author.getId(), author.getName());
    }

    public static Author toDomain(ResponseAuthorDto responseAuthorDto) {
        return new Author(responseAuthorDto.getId(), responseAuthorDto.getName());
    }
}