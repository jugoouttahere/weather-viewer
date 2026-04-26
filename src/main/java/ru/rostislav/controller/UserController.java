package ru.rostislav.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rostislav.dto.LocationWeatherDto;
import ru.rostislav.model.Session;
import ru.rostislav.model.User;
import ru.rostislav.service.LocationService;
import ru.rostislav.service.SessionService;
import ru.rostislav.service.UserService;
import ru.rostislav.utils.CookieUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;
    private final LocationService locationService;

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
            response.addCookie(CookieUtils.createCookie(session));
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Login error on user {}: ", login, e);
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
            response.addCookie(CookieUtils.createCookie(session));
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            log.error("Register error on user {}: ", login, e);
            return "register";
        }
    }

    @PostMapping("/logout")
    public String logout(@CookieValue(name = CookieUtils.COOKIE_NAME, required = false) UUID sessionId, HttpServletResponse response) {
        response.addCookie(CookieUtils.deleteCookie());

        sessionService.deleteSession(sessionId);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        User user = getCurrentUser(request);
        if (user == null) {
            return "redirect:/login";
        }
        List<LocationWeatherDto> locationsWithWeather = locationService.getLocationsWithWeather(user);
        model.addAttribute("locations", locationsWithWeather);
        model.addAttribute("user", user);
        return "index";
    }

    private User getCurrentUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }
}
