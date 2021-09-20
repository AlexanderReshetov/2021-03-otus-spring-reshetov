package ru.otus.main.service;

import ru.otus.main.domain.Token;

public interface TokenService {
    Token loadToken();
    Token getToken();
}
