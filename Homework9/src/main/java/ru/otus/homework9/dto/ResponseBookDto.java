package ru.otus.homework9.dto;

import ru.otus.homework9.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseBookDto {
    private final Long id;
    private final String name;
    private final ResponseAuthorDto responseAuthorDto;
    private final ResponseGenreDto responseGenreDto;
    private final List<ResponseCommentDto> responseCommentDtoList;

    public ResponseBookDto(Long id, String name, ResponseAuthorDto responseAuthorDto, ResponseGenreDto responseGenreDto, List<ResponseCommentDto> responseCommentDtoList) {
        this.id = id;
        this.name = name;
        this.responseAuthorDto = responseAuthorDto;
        this.responseGenreDto = responseGenreDto;
        this.responseCommentDtoList = responseCommentDtoList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ResponseAuthorDto getAuthorDto() {
        return responseAuthorDto;
    }

    public ResponseGenreDto getGenreDto() {
        return responseGenreDto;
    }

    public List<ResponseCommentDto> getCommentDtoList() {
        return responseCommentDtoList;
    }

    public static ResponseBookDto toDto(Book book) {
        return new ResponseBookDto(
                book.getId(),
                book.getName(),
                book.getAuthor() != null ? ResponseAuthorDto.toDto(book.getAuthor()) : null,
                book.getGenre() != null ? ResponseGenreDto.toDto(book.getGenre()) : null,
                book.getComments() != null ? book.getComments().stream().map(ResponseCommentDto::toDto).collect(Collectors.toList()) : null);
    }
}
