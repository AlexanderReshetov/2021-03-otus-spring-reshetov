package ru.otus.main.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedEntityGraph(name = "AuctionForView.fields", attributeNodes = {@NamedAttributeNode("realm"), @NamedAttributeNode("item")})
@Table(name = "auction")
public class AuctionForView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blizzard_id")
    private Long blizzardId;
    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private Realm realm;
    @ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_blizzard_id", referencedColumnName = "blizzard_id")
    private Item item;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "local_datetime")
    private LocalDateTime localDateTime;

    public AuctionForView() {
    }

    public AuctionForView(Long id, Long blizzardId, Realm realm, Item item, Long price, Long quantity, LocalDateTime localDateTime) {
        this.id = id;
        this.blizzardId = blizzardId;
        this.realm = realm;
        this.item = item;
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

    public Realm getRealm() {
        return realm;
    }

    public Item getItem() {
        return item;
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