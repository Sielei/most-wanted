package com.hs.backend.wanted;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MostWantedSummary(String uid, String title, String description, List<MostWantedImage> images) {
}
