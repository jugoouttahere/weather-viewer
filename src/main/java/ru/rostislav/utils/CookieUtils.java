package ru.rostislav.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.rostislav.model.Session;

@Component
public class CookieUtils {

    public static final String COOKIE_NAME = "sessionId";

    private static int COOKIE_MAX_AGE;

    public static Cookie createCookie(Session session) {
        Cookie cookie = new Cookie(COOKIE_NAME, session.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_MAX_AGE);
        return cookie;
    }

    public static Cookie deleteCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    @Value("${cookie.max-age}")
    public void setCookieMaxAge(int cookieMaxAge) {
        COOKIE_MAX_AGE = cookieMaxAge;
    }
}
