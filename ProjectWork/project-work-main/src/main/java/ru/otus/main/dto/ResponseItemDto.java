package ru.otus.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.main.domain.Item;

public class ResponseItemDto {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("enName")
    private final String enName;
    @JsonProperty("ruName")
    private final String ruName;

    public ResponseItemDto(Long id, String enName, String ruName) {
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

    public static Item toDomain(ResponseItemDto responseItemDto) {
        return new Item(null, responseItemDto.getId(), responseItemDto.getEnName(), responseItemDto.getRuName());
    }
}
