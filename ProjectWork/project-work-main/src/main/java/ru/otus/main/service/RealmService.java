package ru.otus.main.service;

import ru.otus.main.domain.Realm;

import java.util.List;

public interface RealmService {
    List<Realm> loadAllRealms();
}
