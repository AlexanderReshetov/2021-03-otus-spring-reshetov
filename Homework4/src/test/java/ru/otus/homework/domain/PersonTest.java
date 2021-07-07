package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Person должен")
public class PersonTest {
    private static final String TEST_SURNAME = "TestSurname";
    private static final String TEST_NAME = "TestName";

    @Test
    @DisplayName("корректно создаться конструктором по фамилии")
    void shouldHaveCorrectConstructorBySurname() {
        final Person person = person();

        assertEquals(person.getSurname(), TEST_SURNAME);
    }

    @Test
    @DisplayName("корректно создаться конструктором по фамилии и имени")
    void shouldHaveCorrectConstructorBySurnameAndName() {
        final Person person = personWithName();

        assertEquals(person.getSurname(), TEST_SURNAME);
        assertEquals(person.getName(), TEST_NAME);
    }

    @Test
    @DisplayName("корректно установить имя")
    void shouldHaveCorrectNameSetter() {
        final Person person = person();
        person.setName(TEST_NAME);

        assertEquals(person.getName(), TEST_NAME);
    }

    private Person person() {
        return new Person(TEST_SURNAME);
    }

    private Person personWithName() {
        return new Person(TEST_SURNAME, TEST_NAME);
    }
}