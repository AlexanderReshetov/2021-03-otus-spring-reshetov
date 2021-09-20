package ru.otus.main.domain;

import javax.persistence.*;

@Entity
@Table(name = "realm")
public class Realm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blizzard_id")
    private Long blizzardId;
    @Column(name = "en_name")
    private String enName;
    @Column(name = "ru_name")
    private String ruName;

    public Realm() {
    }

    public Realm(Long id, Long blizzardId, String enName, String ruName) {
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