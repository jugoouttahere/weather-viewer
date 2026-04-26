package ru.rostislav.dto;

import ru.rostislav.model.Location;

public record LocationWeatherDto(
        Location location,
        WeatherDto weatherDto
) {

    public static LocationWeatherDto toDto(Location location, WeatherDto weather) {
        return new LocationWeatherDto(location, weather);
    }
}
