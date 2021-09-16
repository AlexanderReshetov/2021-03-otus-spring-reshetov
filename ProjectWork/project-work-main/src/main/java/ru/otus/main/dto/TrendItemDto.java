package ru.otus.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class TrendItemDto {
    @JsonProperty("localDateTime")
    private final LocalDateTime localDateTime;
    @JsonProperty("price")
    private final Long price;

    public TrendItemDto(LocalDateTime localDateTime, Long price) {
        this.localDateTime = localDateTime;
        this.price = price;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Long getPrice() {
        return price;
    }
}
