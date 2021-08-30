package ru.otus.homework12.dto;

import java.util.List;

public class IntegrationTestResultDto {
    private final List<UnitTestResultDto> unitTestResultDtoList;
    private final Boolean result;

    public IntegrationTestResultDto(List<UnitTestResultDto> unitTestResultDtoList, Boolean result) {
        this.unitTestResultDtoList = unitTestResultDtoList;
        this.result = result;
    }

    public List<UnitTestResultDto> getUnitTestResultList() {
        return unitTestResultDtoList;
    }

    public Boolean getResult() {
        return result;
    }
}
