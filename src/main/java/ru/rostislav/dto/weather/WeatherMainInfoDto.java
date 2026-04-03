package ru.rostislav.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMainInfoDto {

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double feelsLike;

    @JsonProperty("humidity")
    private Integer humidity;
}
