package ru.rostislav.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDescriptionDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;
}
