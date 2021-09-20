package ru.otus.loader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlizzardTokenDto {
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("expires_in")
    private Long expiresIn;

    public BlizzardTokenDto() {
    }

    public BlizzardTokenDto(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }
}
