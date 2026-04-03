package ru.rostislav.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rostislav.model.Session;
import ru.rostislav.model.User;
import ru.rostislav.service.SessionService;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/logout") ||
                uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = request.getCookies();
        UUID sessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    try {
                        sessionId = UUID.fromString(cookie.getValue());
                    } catch (IllegalArgumentException ignored) {

                    }
                    break;
                }
            }
        }

        if (sessionId != null) {
            Session session = sessionService.getSession(sessionId);
            if (session != null) {
                User user = session.getUser();
                request.setAttribute("user", user);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        response.sendRedirect("/login");
    }
}
