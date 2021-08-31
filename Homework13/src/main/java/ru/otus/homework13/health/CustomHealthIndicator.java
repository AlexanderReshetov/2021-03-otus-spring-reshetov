package ru.otus.homework13.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        final boolean serverIsUp = customHealthCheck();
        if (serverIsUp) {
            return Health.up().withDetail("message", "Server is ok!").build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Server is down!")
                    .build();
        }
    }

    private boolean customHealthCheck() {
        return true;
    }
}
