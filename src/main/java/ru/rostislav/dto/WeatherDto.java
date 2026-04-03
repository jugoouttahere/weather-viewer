package ru.rostislav.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.rostislav.dto.weather.WeatherDescriptionDto;
import ru.rostislav.dto.weather.WeatherMainInfoDto;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("main")
    private WeatherMainInfoDto main;

    @JsonProperty("weather")
    private List<WeatherDescriptionDto> weather;

    @JsonProperty("name")
    private String cityName;
}
