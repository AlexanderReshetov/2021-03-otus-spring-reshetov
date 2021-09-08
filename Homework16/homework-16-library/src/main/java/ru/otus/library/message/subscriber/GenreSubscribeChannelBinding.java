package ru.otus.library.message.subscriber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "kafka", value = "enabled", havingValue = "true", matchIfMissing = true)
@EnableBinding(GenreSubscribeChannel.class)
public class GenreSubscribeChannelBinding {
}
