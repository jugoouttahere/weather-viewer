package ru.rostislav.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.rostislav.model.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    public User findByLogin(String login) {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult();
    }
}
