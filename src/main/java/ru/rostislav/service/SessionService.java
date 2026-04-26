package ru.rostislav.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostislav.model.Session;
import ru.rostislav.model.User;
import ru.rostislav.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public Session createSession(User user) {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(24)
        );
        sessionRepository.save(session);
        return session;
    }

    @Transactional(readOnly = true)
    public Session getSession(UUID id) {
        Session session = sessionRepository.findById(id);
        if (session == null) {
            return null;
        }

        return session;
    }

    @Transactional
    public void deleteSession(UUID id) {
        sessionRepository.delete(id);
    }
}
