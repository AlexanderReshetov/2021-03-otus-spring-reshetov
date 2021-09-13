package ru.otus.loader.service;

import ru.otus.loader.dto.BlizzardItemDto;

public interface LoadItemService {
    BlizzardItemDto getItemById(Long id);
}
