package ru.otus.genre.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.genre.service.GenreService;

@Configuration
@ComponentScan(value = "ru.otus.genre.service")
@EnableScheduling
@ConditionalOnProperty(prefix = "schedule", value = "enabled", havingValue = "true", matchIfMissing = true)
public class ScheduleConfig {
    private GenreService genreService;

    @Autowired
    public ScheduleConfig(GenreService genreService) {
        this.genreService = genreService;
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void sendGenreList() {
        genreService.broadcastGenres();
    }
}