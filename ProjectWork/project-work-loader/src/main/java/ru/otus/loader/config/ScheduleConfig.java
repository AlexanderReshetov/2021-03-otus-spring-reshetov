package ru.otus.loader.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.loader.service.AuctionService;

@Configuration
@ComponentScan(value = "ru.otus.loader.service")
@EnableScheduling
@ConditionalOnProperty(prefix = "schedule", value = "enabled", havingValue = "true", matchIfMissing = true)
public class ScheduleConfig {
    private AuctionService auctionService;

    @Autowired
    public ScheduleConfig(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void sendAuctions() {
        auctionService.sendAuctions();
    }
}