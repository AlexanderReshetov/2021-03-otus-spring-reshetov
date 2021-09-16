package ru.otus.main.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blizzard_id")
    private Long blizzardId;
    @Column(name = "en_name")
    private String enName;
    @Column(name = "ru_name")
    private String ruName;

    public Item() {
    }

    public Item(Long blizzardId) {
        this.blizzardId = blizzardId;
   }

    public Item(Long id, Long blizzardId, String enName, String ruName) {
        this.id = id;
        this.blizzardId = blizzardId;
        this.enName = enName;
        this.ruName = ruName;
    }

    public Long getId() {
        return id;
    }

    public Long getBlizzardId() {
        return blizzardId;
    }

    public String getEnName() {
        return enName;
    }

    public String getRuName() {
        return ruName;
    }
}