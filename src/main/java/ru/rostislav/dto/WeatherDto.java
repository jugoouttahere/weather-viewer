package ru.rostislav.dto;

import ru.rostislav.dto.weather.WeatherDescriptionDto;
import ru.rostislav.dto.weather.WeatherMainInfoDto;

import java.util.List;

public record WeatherDto(
        WeatherMainInfoDto main,
        List<WeatherDescriptionDto> weather,
        String cityName
) {
}
