package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.DTO.AuthorDTO;
import pl.klobut.books_app.GUI.DTO.BookViewDTO;
import pl.klobut.books_app.GUI.DTO.BookViewStopReadingDTO;
import pl.klobut.books_app.GUI.DTO.OpinionDTO;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.BookView;

import java.util.HashMap;
import java.util.List;

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

    BookView findByBookId(Long id);

    long countByIsFinishRead(boolean b);

    @Query(nativeQuery = true, value = "SELECT OPINION.POINTS as POINTS, COUNT(OPINION.POINTS) as ilosc\n" +
            "FROM BOOK_VIEW\n" +
            "Inner join OPINION\n" +
            "ON OPINION.ID=BOOK_VIEW.OPINION_ID\n" +
            "GROUP BY OPINION.POINTS")
    List<OpinionDTO> countAllByOpinion_Points();

    @Query(value = "SELECT BOOK_VIEW.YOU_STOPPED_READING as Are_you_stop_readin, COUNT(BOOK_VIEW.YOU_STOPPED_READING) as ilosc\n" +
            "            FROM BOOK_VIEW\n" +
            "GROUP BY BOOK_VIEW.YOU_STOPPED_READING", nativeQuery = true)
    List<BookViewStopReadingDTO> listaBookViewStopReading();
}
