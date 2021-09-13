package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlizzardAuctionsDto {
    @JsonProperty("auctions")
    private List<BlizzardAuctionDto> blizzardAuctionDtoList;

    public BlizzardAuctionsDto() {
    }

    public BlizzardAuctionsDto(List<BlizzardAuctionDto> blizzardAuctionDtoList) {
        this.blizzardAuctionDtoList = blizzardAuctionDtoList;
    }

    public List<BlizzardAuctionDto> getBlizzardAuctionDtoList() {
        return blizzardAuctionDtoList;
    }
}
