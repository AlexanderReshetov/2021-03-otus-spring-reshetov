package ru.otus.main.dto;

import ru.otus.main.domain.Item;
import ru.otus.main.domain.Token;

public class ItemAndTokenDto {
    private final Item item;
    private final Token token;

    public ItemAndTokenDto(Item item, Token token) {
        this.item = item;
        this.token = token;
    }

    public Item getItem() {
        return item;
    }

    public Token getToken() {
        return token;
    }
}
