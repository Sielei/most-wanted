package com.hs.backend.common;

import com.hs.backend.wanted.MostWanted;
import com.hs.backend.wanted.MostWantedList;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.spring6.ratelimiter.configure.RateLimiterAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.function.Supplier;

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

    @Bean
    public RateLimiter rateLimiter (){
        var rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(10)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(10))
                .build();
        return RateLimiter.of("fbiMostWanted", rateLimiterConfig);
    }

//    @Bean
//    public RateLimiterAspect rateLimiterAspect() {
//        return new RateLimiterAspect();
//    }
}
