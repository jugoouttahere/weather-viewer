package ru.rostislav.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @OneToMany(mappedBy = "user")
    private List<Session> sessions;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
