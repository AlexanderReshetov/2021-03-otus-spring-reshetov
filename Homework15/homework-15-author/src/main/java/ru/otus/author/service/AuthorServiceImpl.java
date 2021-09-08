package ru.otus.author.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.author.dto.ResponseAuthorDto;
import ru.otus.author.repository.AuthorRepository;
import ru.otus.author.service.exception.AuthorNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<ResponseAuthorDto> getAll() {
        return authorRepository.findAll().stream().map(ResponseAuthorDto::toDto).collect(Collectors.toList());
    }

    public ResponseAuthorDto getById(Long id) {
        return ResponseAuthorDto.toDto(authorRepository.findById(id).orElseThrow(() -> new AuthorNotExistsException("There is no library with id = " + id)));
    }
}
