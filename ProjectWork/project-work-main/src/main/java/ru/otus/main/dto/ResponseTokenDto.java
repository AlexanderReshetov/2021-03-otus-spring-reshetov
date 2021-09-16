package ru.otus.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.main.domain.Token;

import java.time.LocalDateTime;

public class ResponseTokenDto {
    @JsonProperty("token")
    private final String token;
    @JsonProperty("expiration_dt")
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

    public static Token toDomain(ResponseTokenDto responseTokenDto) {
        return new Token(null, responseTokenDto.token, responseTokenDto.getExpirationDt());
    }
}
