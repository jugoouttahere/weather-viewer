package ru.rostislav.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostislav.dto.LocationWeatherDto;
import ru.rostislav.dto.WeatherDto;
import ru.rostislav.model.Location;
import ru.rostislav.model.User;
import ru.rostislav.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherService openWeatherService;


    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public List<Location> getUserLocations(User user) {
        return locationRepository.findByUser(user);
    }

    public void deleteLocationById(Integer id) {
        locationRepository.deleteById(id);
    }

    public List<LocationWeatherDto> getLocationsWithWeather(User user) {
        List<Location> locations = getUserLocations(user);
        List<LocationWeatherDto> locationsWithWeather = new ArrayList<>();
        for (Location location : locations) {
            try {
                WeatherDto weather = openWeatherService.getWeather(
                        location.getLatitude().doubleValue(),
                        location.getLongitude().doubleValue()
                );
                locationsWithWeather.add(LocationWeatherDto.toDto(location,weather));
            } catch (Exception e) {
                log.error("Error to get weather on location {}: ", location.getName(), e);
            }
        }
        return locationsWithWeather;
    }

}
