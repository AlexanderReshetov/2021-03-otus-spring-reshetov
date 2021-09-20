package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardAuctionItemDto {
    @JsonProperty("id")
    private Long id;

    public BlizzardAuctionItemDto() {
    }

    public BlizzardAuctionItemDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
