package ru.rostislav.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rostislav.client.OpenWeatherClient;
import ru.rostislav.dto.WeatherDto;
import ru.rostislav.dto.location.LocationDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenWeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.geo}")
    private String geoUrl;

    @Value("${openweather.api.weather}")
    private String weatherUrl;

    private final ObjectMapper objectMapper;
    private final OpenWeatherClient openWeatherClient;

    public WeatherDto getWeather(double latitude, double longitude) {
        String url = String.format(weatherUrl, latitude, longitude, apiKey);

        try {
            return objectMapper.readValue(openWeatherClient.get(url), WeatherDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationDto> getLocations(String location) {
        String encoded = URLEncoder.encode(location, StandardCharsets.UTF_8);
        String url = String.format(geoUrl, encoded, apiKey);

        try {
            return objectMapper.readValue(openWeatherClient.get(url), new TypeReference<List<LocationDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}