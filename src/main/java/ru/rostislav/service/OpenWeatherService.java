package ru.rostislav.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rostislav.dto.LocationDto;
import ru.rostislav.dto.WeatherDto;
import ru.rostislav.dto.weather.WeatherDescriptionDto;
import ru.rostislav.dto.weather.WeatherMainInfoDto;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenWeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.geo}")
    private String geoUrl;

    @Value("${openweather.api.weather}")
    private String weatherUrl;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public WeatherDto getWeather(double latitude, double longitude) {
        String url = String.format(weatherUrl, latitude, longitude, apiKey);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException(String.valueOf(response.statusCode()) + " - " + response.body());
            }

            return objectMapper.readValue(response.body(), WeatherDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationDto> getLocations(String location) {
        String encoded = URLEncoder.encode(location, StandardCharsets.UTF_8);
        String url = String.format(geoUrl, encoded, apiKey);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException(String.valueOf(response.statusCode()));
            }

            List<LocationDto> locationDtoList = objectMapper.readValue(
                    response.body(),
                    new TypeReference<List<LocationDto>>() {
                    }
            );

            return locationDtoList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}