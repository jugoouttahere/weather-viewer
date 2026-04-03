package ru.rostislav.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostislav.model.Location;
import ru.rostislav.model.User;
import ru.rostislav.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;

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

}
