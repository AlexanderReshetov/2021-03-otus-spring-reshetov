package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.ResponseAuthorDto;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.exception.AuthorNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponseAuthorDto> getAll() {
        return authorRepository.findAll().stream().map(ResponseAuthorDto::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseAuthorDto getById(Long id) {
        return ResponseAuthorDto.toDto(authorRepository.findById(id).orElseThrow(() -> new AuthorNotExistsException("There is no library with id = " + id)));
    }

    @Transactional
    public void saveAll(List<ResponseAuthorDto> responseAuthorDtoList) {
        authorRepository.saveAll(responseAuthorDtoList.stream().map(ResponseAuthorDto:: toDomain).collect(Collectors.toList()));
    }
}
