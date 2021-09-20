package ru.otus.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.main.domain.AuctionLot;

import java.time.LocalDateTime;

public class ResponseAuctionDto {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("realmId")
    private final Long realmId;
    @JsonProperty("itemId")
    private final Long itemId;
    @JsonProperty("itemPrice")
    private final Long itemPrice;
    @JsonProperty("count")
    private final Long count;
    @JsonProperty("localDateTime")
    private final LocalDateTime localDateTime;

    public ResponseAuctionDto(Long id, Long realmId, Long itemId, Long itemPrice, Long count, LocalDateTime localDateTime) {
        this.id = id;
        this.realmId = realmId;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.count = count;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public Long getRealmId() {
        return realmId;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public Long getCount() {
        return count;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public static AuctionLot toDomain(ResponseAuctionDto responseAuctionDto) {
        return new AuctionLot(
                null,
                responseAuctionDto.getId(),
                responseAuctionDto.getRealmId(),
                responseAuctionDto.getItemId(),
                responseAuctionDto.getItemPrice(),
                responseAuctionDto.getCount(),
                responseAuctionDto.getLocalDateTime());
    }
}