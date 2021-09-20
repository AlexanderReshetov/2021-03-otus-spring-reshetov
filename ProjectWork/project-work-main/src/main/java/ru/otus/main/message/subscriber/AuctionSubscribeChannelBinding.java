package ru.otus.main.message.subscriber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "kafka", value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableBinding(AuctionSubscribeChannel.class)
public class AuctionSubscribeChannelBinding {
}
