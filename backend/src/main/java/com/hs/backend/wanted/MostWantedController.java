package com.hs.backend.wanted;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wanted")
class MostWantedController {

    private final Timer mostWantedListRequestTimer;
    private final MostWantedService mostWantedService;
    private final RateLimiter rateLimiter;

    public MostWantedController(MostWantedService mostWantedService, MeterRegistry meterRegistry, RateLimiter rateLimiter) {
        this.mostWantedService = mostWantedService;
        this.mostWantedListRequestTimer = Timer.builder("fbi.wanted.requests")
                .tag("type", "list")
                .description("Timer for most wanted list requests")
                .register(meterRegistry);
        this.rateLimiter = rateLimiter;
    }

    @GetMapping
    @Timed(value = "fbi.wanted.list.time", description = "Time taken to fetch most wanted list")
    PagedCollection<MostWanted> mostWantedSummary(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize,
            @RequestParam(value = "name", required = false) String name, @RequestParam(value = "fieldOffice", required = false) String fieldOffice,
            @RequestParam(value = "sex", required = false) String sex, @RequestParam(value = "hairColor", required = false) String hair,
            @RequestParam(value = "eyeColor", required = false) String eyes, @RequestParam(value = "race", required = false) String race,
            @RequestParam(value = "status", required = false) String status) {
        var filterParam = FilterParam.builder()
                .page(page)
                .pageSize(pageSize)
                .sex(sex)
                .hairColor(hair)
                .eyeColor(eyes)
                .race(race)
                .name(name)
                .fieldOffice(fieldOffice)
                .status(status)
                .build();
        try {
            return mostWantedListRequestTimer.recordCallable(() -> {
                return RateLimiter.decorateSupplier(rateLimiter, () -> {
                    return mostWantedService.getMostWantedList(filterParam);
                }).get();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
