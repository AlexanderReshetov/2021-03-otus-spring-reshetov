package ru.otus.main.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.main.service.TokenService;

@Component
public class LoaderHealthIndicator implements HealthIndicator {
    private final TokenService tokenService;

    @Autowired
    public LoaderHealthIndicator(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Health health() {
        final boolean serverIsUp = healthCheck();
        if (serverIsUp) {
            return Health.up().withDetail("message", "Loader is ok!").build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Loader is down!")
                    .build();
        }
    }

    private boolean healthCheck() {
        try {
            tokenService.loadToken();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
