package ru.otus.loader.dto;

public class ResponseAuctionDto {
    private final Long id;
    private final Long itemId;
    private final Long itemPrice;
    private final Long count;

    public ResponseAuctionDto(Long id, Long itemId, Long itemPrice, Long count) {
        this.id = id;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public Long getId() {
        return id;
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

    public static ResponseAuctionDto toDto(BlizzardAuctionDto blizzardAuctionDto) {
        return new ResponseAuctionDto(blizzardAuctionDto.getId(), blizzardAuctionDto.getBlizzardItemDto().getId(), blizzardAuctionDto.getItemPrice(), blizzardAuctionDto.getCount());
    }
}
