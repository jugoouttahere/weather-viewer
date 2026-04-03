package ru.rostislav.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rostislav.model.Location;

@Data
@AllArgsConstructor
public class LocationWeatherDto {
    private Location location;
    private WeatherDto weatherDto;
}
