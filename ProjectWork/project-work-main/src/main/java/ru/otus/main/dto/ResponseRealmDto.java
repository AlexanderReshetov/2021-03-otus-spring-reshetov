package ru.otus.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.main.domain.Realm;

public class ResponseRealmDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("enName")
    private String enName;
    @JsonProperty("ruName")
    private String ruName;

    public ResponseRealmDto() {
    }

    public ResponseRealmDto(Long id, String enName, String ruName) {
        this.id = id;
        this.enName = enName;
        this.ruName = ruName;
    }

    public Long getId() {
        return id;
    }

    public String getEnName() {
        return enName;
    }

    public String getRuName() {
        return ruName;
    }

    public static Realm toDomain(ResponseRealmDto responseRealmDto) {
        return new Realm(null, responseRealmDto.getId(), responseRealmDto.getEnName(), responseRealmDto.getRuName());
    }
}
