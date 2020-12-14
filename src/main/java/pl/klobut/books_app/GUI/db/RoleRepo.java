package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
