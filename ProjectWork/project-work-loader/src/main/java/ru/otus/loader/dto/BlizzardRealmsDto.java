package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlizzardRealmsDto {
    @JsonProperty("realms")
    private List<BlizzardRealmDto> blizzardRealmDtoList;

    public BlizzardRealmsDto() {
    }

    public BlizzardRealmsDto(List<BlizzardRealmDto> blizzardRealmDtoList) {
        this.blizzardRealmDtoList = blizzardRealmDtoList;
    }

    public List<BlizzardRealmDto> getBlizzardRealmDtoList() {
        return blizzardRealmDtoList;
    }
}
