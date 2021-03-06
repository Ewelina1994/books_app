package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);

}
