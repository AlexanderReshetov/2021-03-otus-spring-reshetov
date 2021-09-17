package ru.otus.main.service;

import ru.otus.main.domain.Item;
import ru.otus.main.dto.ItemAndTokenDto;

import java.util.Optional;

public interface ItemService {
    Item loadItemById(Long id);
    Item loadItemById(String token, Long id);
    ItemAndTokenDto loadItemWithTokenById(Long id);
}