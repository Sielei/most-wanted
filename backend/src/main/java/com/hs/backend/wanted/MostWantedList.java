package com.hs.backend.wanted;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MostWantedList(Integer total, List<MostWanted> items) {
}
