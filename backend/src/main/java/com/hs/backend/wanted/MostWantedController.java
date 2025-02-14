package com.hs.backend.wanted;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wanted")
@Tag(name = "FBI Most Wanted", description = "API endpoints for FBI Most Wanted information")
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
    @Operation(
            summary = "Get wanted list",
            description = "Retrieve a paginated list of wanted persons with optional filters"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved wanted list")
    @ApiResponse(responseCode = "429", description = "Too many requests")
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
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
