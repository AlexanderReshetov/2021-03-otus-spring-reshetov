package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardNameDto {
    @JsonProperty("en_US")
    private String en_US;
    @JsonProperty("ru_RU")
    private String ru_RU;

    public BlizzardNameDto() {
    }

    public BlizzardNameDto(String en_US, String ru_RU) {
        this.en_US = en_US;
        this.ru_RU = ru_RU;
    }

    public String getEn_US() {
        return en_US;
    }

    public String getRu_RU() {
        return ru_RU;
    }
}
