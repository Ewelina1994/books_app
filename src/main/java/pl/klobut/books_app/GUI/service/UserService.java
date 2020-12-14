package pl.klobut.books_app.GUI.service;

import org.springframework.stereotype.Service;
import pl.klobut.books_app.GUI.entity.User;

@Service
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
