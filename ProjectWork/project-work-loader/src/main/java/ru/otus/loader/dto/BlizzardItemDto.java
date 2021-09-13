package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardItemDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private BlizzardNameDto blizzardNameDto;

    public BlizzardItemDto() {
    }

    public BlizzardItemDto(Long id, BlizzardNameDto blizzardNameDto) {
        this.id = id;
        this.blizzardNameDto = blizzardNameDto;
    }

    public Long getId() {
        return id;
    }

    public BlizzardNameDto getBlizzardNameDto() {
        return blizzardNameDto;
    }
}
