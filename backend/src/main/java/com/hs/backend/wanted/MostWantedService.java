package com.hs.backend.wanted;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MostWantedService {
    private static final Logger log = LoggerFactory.getLogger(MostWantedService.class);
    private final FBIQueryHandler fbiQueryHandler;
    private final RedisTemplate<String, MostWantedList> mostWantedListRedisTemplate;
    private final RedisTemplate<String, MostWanted> mostWantedRedisTemplate;

    public MostWantedService(FBIQueryHandler fbiQueryHandler, RedisTemplate<String,
            MostWantedList> mostWantedListRedisTemplate, RedisTemplate<String,
            MostWanted> mostWantedRedisTemplate) {
        this.fbiQueryHandler = fbiQueryHandler;
        this.mostWantedListRedisTemplate = mostWantedListRedisTemplate;
        this.mostWantedRedisTemplate = mostWantedRedisTemplate;
    }

    public PagedCollection<MostWanted> getMostWantedList(FilterParam filterParam) {
        try {
            var filteredMostWantedCacheKey = WantedUtil.generateCacheKeyFromFilterParam(filterParam);
            var cachedMostWantedList = mostWantedListRedisTemplate.opsForValue().get(filteredMostWantedCacheKey);
            if (cachedMostWantedList != null) {
                return WantedUtil.mapWantedListToPagedCollection(cachedMostWantedList, filterParam.page(),
                        filterParam.pageSize());
            }
            return WantedUtil.mapWantedListToPagedCollection(fbiQueryHandler.fetchAndCacheMostWantedList(filterParam), filterParam.page(),
                    filterParam.pageSize());
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error fetching most wanted", e);
        }
    }
}
