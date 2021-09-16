package ru.otus.main.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiration_dt")
    private LocalDateTime expirationDt;

    public Token() {
    }

    public Token(Long id, String token, LocalDateTime expirationDt) {
        this.id = id;
        this.token = token;
        this.expirationDt = expirationDt;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirationDt() {
        return expirationDt;
    }
}
