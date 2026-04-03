package ru.rostislav.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.rostislav.model.Session;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final SessionFactory sessionFactory;

    public void save(Session session) {
        sessionFactory.getCurrentSession().save(session);
    }

    public Session findById(UUID id) {
        return sessionFactory.getCurrentSession().find(Session.class, id);
    }

    public void delete(UUID id) {
        Session session = findById(id);
        if (session != null) {
            sessionFactory.getCurrentSession().delete(session);
        }
    }
}
