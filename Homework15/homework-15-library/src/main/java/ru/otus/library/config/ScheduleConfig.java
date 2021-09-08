package ru.otus.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.library.service.LoadAuthorsService;

@Configuration
@ComponentScan(value = "ru.otus.library.service")
@EnableScheduling
@ConditionalOnProperty(prefix = "schedule", value = "enabled", havingValue = "true", matchIfMissing = true)
public class ScheduleConfig {
    private LoadAuthorsService loadAuthorsService;

    @Autowired
    public ScheduleConfig(LoadAuthorsService loadAuthorsService) {
        this.loadAuthorsService = loadAuthorsService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000 + 30 * 1000)
    @Bean
    public void loadAuthors() {
        loadAuthorsService.getAllAuthors();
    }

    @CacheEvict(value = "authors", allEntries = true)
    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 5 * 60 * 1000)
    public void invalidateCache() {
    }
}
