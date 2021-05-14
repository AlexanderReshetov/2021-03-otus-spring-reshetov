package ru.otus.homework.domain;

public class Person {
    private final String surname;
    private String name;

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

    public void setName(String name) {
        this.name = name;
    }
}
