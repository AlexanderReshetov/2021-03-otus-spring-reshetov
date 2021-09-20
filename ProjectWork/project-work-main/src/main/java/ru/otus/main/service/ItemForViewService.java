package ru.otus.main.service;

import ru.otus.main.domain.Item;

import java.util.List;

public interface ItemForViewService {
    List<Item> getByRuName(String ruName);
    List<Item> getByEnName(String enName);
}
