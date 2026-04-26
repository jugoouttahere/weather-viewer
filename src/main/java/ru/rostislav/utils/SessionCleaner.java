package ru.rostislav.utils;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessionCleaner {

    private final SessionFactory sessionFactory;

    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void deleteExpiredSession() {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Session WHERE expiresAt < :now")
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
    }
}
