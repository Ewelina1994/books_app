package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.DTO.BookViewDTO;
import pl.klobut.books_app.GUI.entity.BookView;

@Repository
public interface BookViewRepo extends JpaRepository<BookView, Long> {

    @Query(value = "SELECT BOOK_VIEW.ID, BOOK.TITLE AS TITLE, START_READING AS START_READING, IS_FINISH_READ AS ARE_YOU_FINISH_READING, YOU_STOPPED_READING, DESCRIPTION, POINTS \n" +
            "FROM BOOK_VIEW \n" +
            "INNER JOIN OPINION\n" +
            "ON OPINION.ID=OPINION_ID\n"+
            "INNER JOIN BOOK\n" +
            "ON BOOK.ID=BOOK_ID\n" +
            "WHERE BOOK_VIEW.ID = ?1",
            nativeQuery = true)
    BookViewDTO getBookViewByBook(Long id);
}
