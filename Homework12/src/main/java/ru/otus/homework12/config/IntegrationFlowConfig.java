package ru.otus.homework12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
@EnableIntegration
public class IntegrationFlowConfig {
    public static final String UNIT_CHANNEL = "unitChannel";
    public static final String UNIT_TEST_CHANNEL = "unitTestChannel";

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
        return IntegrationFlows.from(UNIT_CHANNEL)
                .split()
                .handle("unitTestService", "test")
                .aggregate()
                .channel(UNIT_TEST_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow integrationTestFlow() {
        return IntegrationFlows.from(UNIT_TEST_CHANNEL)
                .handle("integrationTestService", "test")
                .get();
    }
}
