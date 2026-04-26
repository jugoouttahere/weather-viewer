package ru.rostislav.dto.location;

public record LocationDto(
        String name,
        Double latitude,
        Double longitude,
        String country,
        String state) {
}
