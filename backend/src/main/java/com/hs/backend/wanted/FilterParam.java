package com.hs.backend.wanted;

import lombok.Builder;

@Builder
public record FilterParam(Integer page, Integer pageSize, String sex, String hairColor, String eyeColor, String race, String name, String fieldOffice, String status) {
}
