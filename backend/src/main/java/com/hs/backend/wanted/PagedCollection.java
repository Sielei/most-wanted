package com.hs.backend.wanted;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PagedCollection<T>(List<T> data, long totalElements, Integer pageNumber, Integer totalPages,
                                 @JsonProperty("isFirst") boolean isFirst, @JsonProperty("isLast") boolean isLast,
                                 @JsonProperty("hasNext") boolean hasNext, @JsonProperty("hasPrevious") boolean hasPrevious) {
}
