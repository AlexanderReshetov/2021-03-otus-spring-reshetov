package ru.otus.homework.domain;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String surname;
    private String name;

    public Person() {
    }

    public Person(String surname) {
        this.surname = surname;
    }

    public Person(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
