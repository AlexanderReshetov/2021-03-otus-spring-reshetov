package ru.otus.loader.dto;

import java.time.LocalDateTime;

public class ResponseTokenDto {
    private final String token;
    private final LocalDateTime expirationDt;

    public ResponseTokenDto(String token, LocalDateTime expirationDt) {
        this.token = token;
        this.expirationDt = expirationDt;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirationDt() {
        return expirationDt;
    }

    public static ResponseTokenDto toDto(BlizzardTokenDto blizzardTokenDto) {
        return new ResponseTokenDto("Bearer " + blizzardTokenDto.getToken(), LocalDateTime.now().plusSeconds(blizzardTokenDto.getExpiresIn()));
    }
}
