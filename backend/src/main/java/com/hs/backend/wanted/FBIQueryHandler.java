package com.hs.backend.wanted;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
class FBIQueryHandler {
    private static final Logger log = LoggerFactory.getLogger(FBIQueryHandler.class);
    private final RestClient restClient;
    private final RedisTemplate<String, MostWantedList> mostWantedListRedisTemplate;

    public FBIQueryHandler(RestClient restClient, RedisTemplate<String, MostWantedList> mostWantedListRedisTemplate) {
        this.restClient = restClient;
        this.mostWantedListRedisTemplate = mostWantedListRedisTemplate;
    }


    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void refreshCache() {
        fetchAndCacheMostWantedList(FilterParam.builder().page(1).pageSize(50).build());
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public MostWantedList fetchAndCacheMostWantedList(FilterParam filterParam){
        var cacheKey = WantedUtil.generateCacheKeyFromFilterParam(filterParam);
        var uri = WantedUtil.generateURIFromFilterParam(filterParam);
        var mostWantedList = restClient.get()
                .uri(uri)
                .retrieve()
                .body(MostWantedList.class);
        mostWantedListRedisTemplate.opsForValue().set(cacheKey, mostWantedList, 24, TimeUnit.HOURS);
        return mostWantedList;
    }
}
