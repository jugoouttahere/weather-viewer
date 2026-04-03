package ru.rostislav.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rostislav.dto.LocationWeatherDto;
import ru.rostislav.dto.WeatherDto;
import ru.rostislav.model.Location;
import ru.rostislav.model.Session;
import ru.rostislav.model.User;
import ru.rostislav.service.LocationService;
import ru.rostislav.service.OpenWeatherService;
import ru.rostislav.service.SessionService;
import ru.rostislav.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private static final String COOKIE_NAME = "sessionId";
    private static final int COOKIE_MAX_AGE = 86400;

    private final UserService userService;
    private final SessionService sessionService;
    private final LocationService locationService;
    private final OpenWeatherService openWeatherService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        if (getCurrentUser(request) != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response, Model model) {
        try {
            Session session = userService.login(login, password);

            Cookie cookie = new Cookie(COOKIE_NAME, session.getId().toString());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE);

            response.addCookie(cookie);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request) {
        if (getCurrentUser(request) != null) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login, @RequestParam String password, HttpServletResponse response, Model model) {
        try {
            Session session = userService.register(login, password);

            Cookie cookie = new Cookie(COOKIE_NAME, session.getId().toString());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE);

            response.addCookie(cookie);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(value = COOKIE_NAME, required = false) UUID sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        sessionService.deleteSession(sessionId);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user == null) {
            return "redirect:/login";
        }
        List<Location> locations = locationService.getUserLocations(user);

        List<LocationWeatherDto> locationsWithWeather = new ArrayList<>();
        for (Location location : locations) {
            try {
                WeatherDto weather = openWeatherService.getWeather(
                        location.getLatitude().doubleValue(),
                        location.getLongitude().doubleValue()
                );
                locationsWithWeather.add(new LocationWeatherDto(location, weather));
            } catch (Exception e) {
                System.out.println("Weather error on " + location.getName() + ": " + e.getMessage());
            }
        }
        model.addAttribute("locations", locationsWithWeather);
        model.addAttribute("user", user);
        return "index";
    }

    private User getCurrentUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }
}
