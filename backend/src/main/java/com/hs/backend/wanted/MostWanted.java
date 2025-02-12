package com.hs.backend.wanted;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MostWanted(@JsonProperty("title") String name, List<MostWantedFile> files, List<MostWantedImage> images, @JsonProperty("age_range") String ageRange, String uid,
                         String weight, List<String> occupations, @JsonProperty("field_offices") List<String> fieldOffices,
                         List<String> locations, @JsonProperty("reward_text") String rewardText, String sex, String hair, String eyes,
                         String ncic, @JsonProperty("dates_of_birth_used") List<String> datesOfBirthUsed, String caution,
                         String nationality, @JsonProperty("scars_and_marks") String scarsAndMarks, @JsonProperty("age_min") Integer ageMin,
                         @JsonProperty("age_max") Integer ageMax, @JsonProperty("warning_message") String warningMessage,
                         @JsonProperty("height_max") Integer maxHeight, String description, String race,
                         List<String> subjects, @JsonProperty("place_of_birth") String placeOfBirth,
                         String remarks, String details) {
}
