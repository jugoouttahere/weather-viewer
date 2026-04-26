package ru.rostislav.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.rostislav.model.Location;
import ru.rostislav.model.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final SessionFactory sessionFactory;

    public List<Location> findByUser(User user) {
        return sessionFactory.getCurrentSession()
                .createQuery("select l from Location l where l.user.id = :userId", Location.class)
                .setParameter("userId", user.getId())
                .getResultList();
    }

    public void save(Location location) {
        sessionFactory.getCurrentSession().save(location);
    }

    public Location findById(Integer id) {
        return sessionFactory.getCurrentSession().find(Location.class, id);
    }

    public void deleteById(Integer id) {
        Location location = findById(id);
        if (location != null) {
            sessionFactory.getCurrentSession().delete(location);
        }
    }

}
