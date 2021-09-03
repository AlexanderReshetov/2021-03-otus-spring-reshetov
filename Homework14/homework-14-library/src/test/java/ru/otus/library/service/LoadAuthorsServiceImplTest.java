package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.library.dto.ResponseAuthorDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки авторов должен")
public class LoadAuthorsServiceImplTest {
    @Mock
    private AuthorService authorService;
    @Mock
    private RestOperations restOperations;
    @InjectMocks
    private LoadAuthorsServiceImpl loadAuthorsService;

    @Test
    @DisplayName("получить список авторов")
    void shouldGetAuthorList() {
        final List<ResponseAuthorDto> responseAuthorDtoList = Arrays.asList(
                new ResponseAuthorDto(1L, "TestAuthor1"),
                new ResponseAuthorDto(2L, "TestAuthor2"));
        when(restOperations.exchange(any(), eq(String.class))).thenReturn(ResponseEntity.ok("user "));
        when(restOperations.exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseAuthorDto>>>any()))
                .thenReturn(ResponseEntity.ok(Arrays.asList(new ResponseAuthorDto(1L, "TestAuthor1"), new ResponseAuthorDto(2L, "TestAuthor2"))));

        List<ResponseAuthorDto> newResponseAuthorDtoList = loadAuthorsService.getAllAuthors();

        verify(authorService).saveAll(newResponseAuthorDtoList);
        assertEquals(responseAuthorDtoList.size(), newResponseAuthorDtoList.size());
        assertEquals(responseAuthorDtoList.get(0).getId(), newResponseAuthorDtoList.get(0).getId());
        assertEquals(responseAuthorDtoList.get(0).getName(), newResponseAuthorDtoList.get(0).getName());
        assertEquals(responseAuthorDtoList.get(1).getId(), newResponseAuthorDtoList.get(1).getId());
        assertEquals(responseAuthorDtoList.get(1).getName(), newResponseAuthorDtoList.get(1).getName());
    }
}
