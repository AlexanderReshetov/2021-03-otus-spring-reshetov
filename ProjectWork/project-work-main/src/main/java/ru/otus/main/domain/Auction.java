package ru.otus.main.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blizzard_id")
    private Long blizzardId;
    @Column(name = "realm_id")
    private Long realmId;
    @Column(name = "item_blizzard_id")
    private Long itemBlizzardId;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "local_datetime")
    private LocalDateTime localDateTime;

    public Auction() {
    }

    public Auction(Long id, Long blizzardId, Long realmId, Long itemBlizzardId, Long price, Long quantity, LocalDateTime localDateTime) {
        this.id = id;
        this.blizzardId = blizzardId;
        this.realmId = realmId;
        this.itemBlizzardId = itemBlizzardId;
        this.price = price;
        this.quantity = quantity;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public Long getBlizzardId() {
        return blizzardId;
    }

    public Long getRealmId() {
        return realmId;
    }

    public Long getItemBlizzardId() {
        return itemBlizzardId;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}