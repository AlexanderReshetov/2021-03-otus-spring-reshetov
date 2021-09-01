package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.library.controller.AuthorController;

@Service
public class LoadAuthorsService {
    private final AuthorController authorController;

    @Autowired
    public LoadAuthorsService(AuthorController authorController) {
        this.authorController = authorController;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000 + 30 * 1000)
    public void loadAuthors() {
        authorController.getAllAuthors();
    }

    @CacheEvict(value = "authors", allEntries = true)
    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 5 * 60 * 1000)
    public void invalidateCache() {
    }
}
