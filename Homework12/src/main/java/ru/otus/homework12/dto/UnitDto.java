package ru.otus.homework12.dto;

public class UnitDto {
    private final String name;
    private final String text;

    public UnitDto(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
