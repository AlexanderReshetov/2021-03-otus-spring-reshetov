package ru.otus.homework12.dto;

public class UnitTestResultDto {
    private final UnitDto unitDto;
    private final Boolean result;

    public UnitTestResultDto(UnitDto unitDto, Boolean result) {
        this.unitDto = unitDto;
        this.result = result;
    }

    public UnitDto getUnit() {
        return unitDto;
    }

    public Boolean getResult() {
        return result;
    }
}
