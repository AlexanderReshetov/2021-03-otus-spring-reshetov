package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.main.domain.Item;
import ru.otus.main.repository.ItemRepository;

import java.util.List;

@Service
public class ItemForViewServiceImpl implements ItemForViewService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemForViewServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getByRuName(String ruName) {
        return itemRepository.findAllByRuName(ruName);
    }

    public List<Item> getByEnName(String enName) {
        return itemRepository.findAllByEnName(enName);
    }
}
