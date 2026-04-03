package ru.rostislav.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rostislav.dto.LocationDto;
import ru.rostislav.model.Location;
import ru.rostislav.model.User;
import ru.rostislav.service.LocationService;
import ru.rostislav.service.OpenWeatherService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationController {

    public final LocationService locationService;
    public final OpenWeatherService openWeatherService;

    @GetMapping("/search")
    public String search(@RequestParam String city, HttpServletRequest request, Model model) {
        User user = getCurrentUser(request);
        if (user == null) {
            return "redirect:/login";
        }
        List<LocationDto> locations = openWeatherService.getLocations(city);
        model.addAttribute("cities", locations);
        model.addAttribute("searchCity", city);
        return "search";
    }

    @PostMapping("/add-location")
    public String addLocation(@RequestParam String name, @RequestParam BigDecimal lat, @RequestParam BigDecimal lon, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user == null) {
            return "redirect:/login";
        }
        Location location = new Location(name, user, lat, lon);
        locationService.addLocation(location);
        return "redirect:/";
    }

    @PostMapping("/delete-location")
    public String deleteLocation(@RequestParam Integer locationId, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user == null) {
            return "redirect:/login";
        }
        List<Location> userLocations = locationService.getUserLocations(user);
        boolean owns = userLocations.stream().anyMatch(loc -> loc.getId().equals(locationId));
        if (owns) {
            locationService.deleteLocationById(locationId);
        }
        return "redirect:/";
    }

    private User getCurrentUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }
}
