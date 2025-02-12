package com.hs.backend.config;

import com.hs.backend.wanted.MostWanted;
import com.hs.backend.wanted.MostWantedList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfig {
    @Value("${most-wanted-config.fbi-wanted-base-url}")
    private String fbiBaseUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(fbiBaseUrl)
                .build();
    }

    @Bean
    public RedisTemplate<String, MostWantedList> mostWantedListTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, MostWantedList> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<MostWantedList> serializer = new Jackson2JsonRedisSerializer<>(MostWantedList.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }

    @Bean
    public RedisTemplate<String, MostWanted> mostWantedTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, MostWanted> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<MostWanted> serializer = new Jackson2JsonRedisSerializer<>(MostWanted.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        return template;
    }
}
