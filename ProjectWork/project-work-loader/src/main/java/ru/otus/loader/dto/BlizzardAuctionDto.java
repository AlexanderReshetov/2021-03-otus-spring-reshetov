package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardAuctionDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("item")
    private BlizzardAuctionItemDto blizzardAuctionItemDto;
    @JsonProperty("unit_price")
    private Long itemPrice;
    @JsonProperty("quantity")
    private Long count;

    public BlizzardAuctionDto() {
    }

    public BlizzardAuctionDto(Long id, BlizzardAuctionItemDto blizzardAuctionItemDto, Long itemPrice, Long count) {
        this.id = id;
        this.blizzardAuctionItemDto = blizzardAuctionItemDto;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public BlizzardAuctionItemDto getBlizzardItemDto() {
        return blizzardAuctionItemDto;
    }

    public Long getCount() {
        return count;
    }

    public Long getItemPrice() {
        return itemPrice;
    }
}
