package ru.otus.homework12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.homework12.service.IntegrationTestService;
import ru.otus.homework12.service.UnitTestService;

@Configuration
@EnableIntegration
public class IntegrationFlowConfig {
    private final UnitTestService unitTestService;
    private final IntegrationTestService integrationTestService;

    public static final String UNIT_CHANNEL = "unitChannel";
    public static final String UNIT_TEST_CHANNEL = "unitTestChannel";
    public static final String UNIT_TEST_SERVICE_METHOD_NAME = "test";
    public static final String INTEGRATION_TEST_SERVICE_METHOD_NAME = "test";

    @Autowired
    public IntegrationFlowConfig(UnitTestService unitTestService, IntegrationTestService integrationTestService) {
        this.unitTestService = unitTestService;
        this.integrationTestService = integrationTestService;
    }

    @Bean
    public PublishSubscribeChannel unitChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel unitTestChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow unitTestFlow() {
        return IntegrationFlows.from(unitChannel())
                .split()
                .handle(unitTestService, UNIT_TEST_SERVICE_METHOD_NAME)
                .aggregate()
                .channel(UNIT_TEST_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow integrationTestFlow() {
        return IntegrationFlows.from(unitTestChannel())
                .handle(integrationTestService, INTEGRATION_TEST_SERVICE_METHOD_NAME)
                .get();
    }
}
