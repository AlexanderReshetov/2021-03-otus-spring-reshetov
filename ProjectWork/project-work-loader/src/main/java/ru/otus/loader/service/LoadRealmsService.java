package ru.otus.loader.service;

import ru.otus.loader.dto.BlizzardRealmsDto;

public interface LoadRealmsService {
    BlizzardRealmsDto getAllRealms(String token);
}
