package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Person;
import ru.otus.homework.service.exception.IOServiceException;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.PersonInputDataException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис заполнения личных данных пользователя PersonInputDataServiceImpl должен")
public class PersonInputDataServiceImplTest {
    @Mock
    private MessageSourceIOService messageSourceIOService;
    @InjectMocks
    private PersonInputDataServiceImpl personInputDataService;

    private static final String TEST_LINE = "TestLine";

    @Test
    @DisplayName("корректно считать фамилию и имя пользователя")
    void shouldCorrectInputData() throws MessageSourceIOServiceException, PersonInputDataException {
        given(messageSourceIOService.readLine()).willReturn(TEST_LINE);

        Person person = personInputDataService.inputData();

        assertEquals(person.getSurname(), TEST_LINE);
        assertEquals(person.getName(), TEST_LINE);
    }

    @Test
    @DisplayName("выбросить PersonInputDataException, если считать личные данные пользователя не удалось")
    void shouldThrowExceptionWhenIncorrectInputData() throws MessageSourceIOServiceException, PersonInputDataException {
        given(messageSourceIOService.readLine()).willThrow(MessageSourceIOServiceException.class);

        assertThrows(PersonInputDataException.class, () -> personInputDataService.inputData());
    }

}
