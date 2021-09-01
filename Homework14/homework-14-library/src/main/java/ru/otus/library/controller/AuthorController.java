package ru.otus.library.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import ru.otus.library.dto.ResponseAuthorDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.exception.AuthorNotExistsException;

import java.util.Collections;
import java.util.List;

import static ru.otus.library.security.SecurityConstants.TOKEN_PREFIX;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final RestOperations restOperations;

    @Autowired
    public AuthorController(AuthorService authorService, RestOperations restOperations) {
        this.authorService = authorService;
        this.restOperations = restOperations;
    }

    @Timed("REST_GET_ALL_AUTHORS")
    @Cacheable("authors")
    @GetMapping("/authors/")
    public ResponseEntity<List<ResponseAuthorDto>> getAllAuthors() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RequestEntity<?> loginRequestEntity = RequestEntity
                .post("http://localhost:8081/login")
                .headers(headers)
                .body("{\"login\": \"user\",\"password\": \"password\"}");
        final ResponseEntity<String> tokenResponse = restOperations.exchange(loginRequestEntity, String.class);
        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            headers.put("Authorization", Collections.singletonList(tokenResponse.getBody().replace("user ", TOKEN_PREFIX)));

            final RequestEntity<?> requestEntity = RequestEntity.get("http://localhost:8081/authors/").headers(headers).build();
            final ResponseEntity<List<ResponseAuthorDto>> authorsResponse = restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseAuthorDto>>(){});

            authorService.saveAll(authorsResponse.getBody());

            return ResponseEntity.ok(authorsResponse.getBody());
        }
        else {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @Timed("REST_GET_AUTHOR_BY_ID")
    @GetMapping("/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @ExceptionHandler(AuthorNotExistsException.class)
    public ResponseEntity<String> authorNotExists(AuthorNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}