package ru.otus.loader.dto;

public class ResponseItemDto {
    private final Long id;
    private final String enName;
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

    public static ResponseItemDto toDto(BlizzardItemDto blizzardItemDto) {
        return new ResponseItemDto(blizzardItemDto.getId(), blizzardItemDto.getBlizzardNameDto().getEn_US(), blizzardItemDto.getBlizzardNameDto().getRu_RU());
    }

}
