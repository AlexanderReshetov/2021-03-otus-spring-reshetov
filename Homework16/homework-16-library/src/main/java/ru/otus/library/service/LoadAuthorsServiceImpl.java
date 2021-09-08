package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.library.dto.ResponseAuthorDto;
import ru.otus.library.service.exception.AuthenticationException;

import java.util.Collections;
import java.util.List;

import static ru.otus.library.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class LoadAuthorsServiceImpl implements LoadAuthorsService {
    private final AuthorService authorService;
    private final RestOperations restOperations;
    private final String host;
    private final String port;

    @Autowired
    public LoadAuthorsServiceImpl(AuthorService authorService,
                                  RestOperations restOperations,
                                  @Value("${author-service.host}") String host,
                                  @Value("${author-service.port}") String port) {
        this.authorService = authorService;
        this.restOperations = restOperations;
        this.host = host;
        this.port = port;
    }

    @Cacheable("authors")
    public List<ResponseAuthorDto> getAllAuthors() {
        final ResponseEntity<String> tokenResponse = authenticate();
        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            final ResponseEntity<List<ResponseAuthorDto>> authorsResponse = getAuthorListByToken(tokenResponse);
            authorService.saveAll(authorsResponse.getBody());
            if (authorsResponse.getBody() != null)
                return authorsResponse.getBody();
            else {
                return Collections.emptyList();
            }
        } else {
            throw new AuthenticationException("Can't get token from author service!");
        }
    }

    private ResponseEntity<String> authenticate() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RequestEntity<?> loginRequestEntity = RequestEntity
                .post("http://" + host + ":" + port + "/login")
                .headers(headers)
                .body("{\"login\": \"user\",\"password\": \"password\"}");
        return restOperations.exchange(loginRequestEntity, String.class);
    }

    private ResponseEntity<List<ResponseAuthorDto>> getAuthorListByToken(ResponseEntity<String> tokenResponse) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(tokenResponse.getBody().replace("user ", TOKEN_PREFIX)));
        final RequestEntity<?> requestEntity = RequestEntity.get("http://" + host + ":" + port + "/authors/").headers(headers).build();
        return restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseAuthorDto>>() {
        });
    }
}