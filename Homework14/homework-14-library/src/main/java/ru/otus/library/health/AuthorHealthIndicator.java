package ru.otus.library.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class AuthorHealthIndicator implements HealthIndicator {
    private final RestOperations restOperations;

    @Autowired
    public AuthorHealthIndicator(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public Health health() {
        final boolean serverIsUp = healthCheck();
        if (serverIsUp) {
            return Health.up().withDetail("message", "Server is ok!").build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Server is down!")
                    .build();
        }
    }

    private boolean healthCheck() {
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            final RequestEntity<?> loginRequestEntity = RequestEntity
                    .post("http://localhost:8081/login")
                    .headers(headers)
                    .body("{\"login\": \"user\",\"password\": \"password\"}");
            final ResponseEntity<String> tokenResponse = restOperations.exchange(loginRequestEntity, String.class);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
