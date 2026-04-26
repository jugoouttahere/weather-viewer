package ru.rostislav.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rostislav.model.Session;
import ru.rostislav.model.User;
import ru.rostislav.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    public Session register(String login, String password) {
        if (userRepository.findByLogin(login) != null) {
            throw new IllegalArgumentException("Login already exists");
        }
        User user = new User(
                login,
                BCrypt.hashpw(password, BCrypt.gensalt())
        );
        userRepository.save(user);
        return sessionService.createSession(user);
    }

    public Session login(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password or username");
        }
        return sessionService.createSession(user);
    }

}
