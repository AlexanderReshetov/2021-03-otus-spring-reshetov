package ru.otus.homework6.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework6.service.PrintService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("В интеграционном тесте shell команды должны")
public class ShellIntegrationTest {
    @MockBean
    private PrintService printService;
    @Autowired
    private Shell shell;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long NEW_BOOK_ID = 3L;
    private static final String NEW_BOOK_NAME = "NewBook";
    private static final Long NEW_AUTHOR_ID = 2L;
    private static final String NEW_AUTHOR_NAME = "TestAuthor2";
    private static final Long NEW_GENRE_ID = 3L;
    private static final String NEW_GENRE_NAME = "TestGenre3";
    private static final String UPD_BOOK_NAME = "UpdBook";
    private static final Long UPD_AUTHOR_ID = 1L;
    private static final Long UPD_GENRE_ID = 2L;

    @Test
    @DisplayName("вывести список предустановленных книг")
    void shouldPrintAllPredefinedBooks() {
        shell.evaluate(() -> "show all");

        verify(printService).println("Book 1");
        verify(printService).println("Id = 1");
        verify(printService).println("Name = TestBook1");
        verify(printService, times(2)).println("Author id = 1");
        verify(printService, times(2)).println("Author name = TestAuthor1");
        verify(printService).println("Genre id = 1");
        verify(printService).println("Genre name = TestGenre1");
        verify(printService).println("Book 2");
        verify(printService).println("Id = 2");
        verify(printService).println("Name = TestBook2");
        verify(printService).println("Genre id = 2");
        verify(printService).println("Genre name = TestGenre2");
    }

    @Test
    @DisplayName("добавить новую книгу и получить ее по ИД")
    void shouldInsertBookAndGetItById() {
        shell.evaluate(() -> "insert " + NEW_BOOK_NAME + " " + NEW_AUTHOR_ID + " " + NEW_GENRE_ID);
        shell.evaluate(() -> "show " + NEW_BOOK_ID);

        verify(printService).println("Id = " + NEW_BOOK_ID);
        verify(printService).println("Name = " + NEW_BOOK_NAME);
        verify(printService).println("Author id = " + NEW_AUTHOR_ID);
        verify(printService).println("Author name = " + NEW_AUTHOR_NAME);
        verify(printService).println("Genre id = " + NEW_GENRE_ID);
        verify(printService).println("Genre name = " + NEW_GENRE_NAME);
    }

    @Test
    @DisplayName("обновить книгу и получить ее в полном списке")
    void shouldUpdateBookAndGetItInFullList() {
        shell.evaluate(() -> "update " + EXISTING_BOOK_ID + " " + UPD_BOOK_NAME + " " + UPD_AUTHOR_ID + " " + UPD_GENRE_ID);
        shell.evaluate(() -> "show all");

        verify(printService).println("Book 1");
        verify(printService).println("Id = " + EXISTING_BOOK_ID);
        verify(printService).println("Name = " + UPD_BOOK_NAME);
        verify(printService, times(2)).println("Author id = 1");
        verify(printService, times(2)).println("Author name = TestAuthor1");
        verify(printService, times(2)).println("Genre id = 2");
        verify(printService, times(2)).println("Genre name = TestGenre2");
        verify(printService).println("Book 2");
        verify(printService).println("Id = 2");
        verify(printService).println("Name = TestBook2");
    }

    @Test
    @DisplayName("удалить книгу и не получить ее в полном списке")
    void shouldDeleteBookAndDoNotGetItInFullList() {
        shell.evaluate(() -> "delete " + EXISTING_BOOK_ID);
        shell.evaluate(() -> "show all");

        verify(printService).println("Book 1");
        verify(printService).println("Id = 2");
        verify(printService).println("Name = TestBook2");
        verify(printService).println("Author id = 1");
        verify(printService).println("Author name = TestAuthor1");
        verify(printService).println("Genre id = 2");
        verify(printService).println("Genre name = TestGenre2");
        verify(printService, times(0)).println("Book 2");
    }
}
