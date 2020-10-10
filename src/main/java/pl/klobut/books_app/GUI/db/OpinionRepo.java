package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.entity.Opinion;

import java.util.Optional;

@Repository
public interface OpinionRepo extends JpaRepository<Opinion, Long> {
    Optional<Opinion> findById(Long id);
}
