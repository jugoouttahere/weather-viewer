package ru.rostislav.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherMainInfoDto(
        Double temperature,
        @JsonProperty("feels_like") Double feelsLike,
        Integer humidity) {
}
