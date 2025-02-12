package com.hs.backend.wanted;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wanted")
class MostWantedController {

    private final MostWantedService mostWantedService;

    public MostWantedController(MostWantedService mostWantedService) {
        this.mostWantedService = mostWantedService;
    }

    @GetMapping
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
        return mostWantedService.getMostWantedList(filterParam);
    }
}
