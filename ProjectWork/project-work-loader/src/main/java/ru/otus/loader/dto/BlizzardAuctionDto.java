package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardAuctionDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("item")
    private BlizzardAuctionItemDto blizzardAuctionItemDto;
    @JsonProperty("buyout")
    private Long buyout;
    @JsonProperty("unit_price")
    private Long unitPrice;
    @JsonProperty("quantity")
    private Long count;

    public BlizzardAuctionDto() {
    }

    public BlizzardAuctionDto(Long id, BlizzardAuctionItemDto blizzardAuctionItemDto, Long buyout, Long unitPrice, Long count) {
        this.id = id;
        this.blizzardAuctionItemDto = blizzardAuctionItemDto;
        this.buyout = buyout;
        this.unitPrice = unitPrice;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public BlizzardAuctionItemDto getBlizzardAuctionItemDto() {
        return blizzardAuctionItemDto;
    }

    public Long getBuyout() {
        return buyout;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public Long getCount() {
        return count;
    }
}