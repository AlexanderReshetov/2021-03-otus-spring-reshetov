package ru.otus.loader.dto;

public class ResponseRealmDto {
    private final Long id;
    private final String enName;
    private final String ruName;

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

    public static ResponseRealmDto toDto(BlizzardRealmDto blizzardRealmDto) {
        return new ResponseRealmDto(blizzardRealmDto.getId(), blizzardRealmDto.getBlizzardNameDto().getEn_US(), blizzardRealmDto.getBlizzardNameDto().getRu_RU());
    }

}
