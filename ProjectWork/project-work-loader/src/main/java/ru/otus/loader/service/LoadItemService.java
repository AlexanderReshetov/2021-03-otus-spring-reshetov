package ru.otus.loader.service;

import ru.otus.loader.dto.BlizzardItemDto;

public interface LoadItemService {
    BlizzardItemDto getItemById(String token, Long id);
}
