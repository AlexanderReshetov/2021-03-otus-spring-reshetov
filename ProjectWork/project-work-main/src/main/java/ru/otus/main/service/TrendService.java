package ru.otus.main.service;

import ru.otus.main.dto.TrendItemDto;

import java.util.List;

public interface TrendService {
    List<TrendItemDto> getTrendByItemBlizzardId(Long realmId, Long itemBlizzardId);
    List<TrendItemDto> getTrendByItemId(Long realmId, Long itemId);
}
