package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.DTO.AuthorDTO;
import pl.klobut.books_app.GUI.entity.Author;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query(value = "SELECT AUTHOR.NAME as Author, COUNT(AUTHOR.NAME) as CategoryCount, BOOK.KATEGORIA as Kategoria\n" +
            "FROM AUTHOR\n" +
            " INNER JOIN BOOK\n" +
            "ON AUTHOR.ID= BOOK.AUTOR_ID\n" +
            "GROUP BY BOOK.KATEGORIA, AUTHOR.NAME", nativeQuery = true)
    List<AuthorDTO>getCategoryCount();
}
