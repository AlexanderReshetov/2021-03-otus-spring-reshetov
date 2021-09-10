package ru.otus.genre.message.publisher;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableBinding(GenrePublishChannel.class)
public class GenrePublishChannelBinding {
    @Bean
    public NewTopic msgTopic() {
        return TopicBuilder.name("msgGenre")
                .partitions(1)
                .replicas(1)
                .build();
    }
}