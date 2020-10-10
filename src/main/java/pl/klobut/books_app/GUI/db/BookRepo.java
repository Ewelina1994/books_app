package pl.klobut.books_app.GUI.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query(value = "SELECT BOOK.ID as Id, BOOK.TITLE as Title, BOOK.ISBN As Isbn, BOOK.KATEGORIA as Category, Author .name as AuthorName, Author.surname as AuthorSurname\n" +
            "FROM Author\n" +
            "INNER JOIN Book\n" +
            "ON Book.author_id=Author.ID", nativeQuery = true)
    List<BookDTO> getAllBook();

    @Query(value = "SELECT Kategoria, count(*)\n" +
            "from book\n" +
            "group by kategoria", nativeQuery = true)
    List<Book> categoriesCount();

    @Query(value = "select * from Book where id=?1", nativeQuery = true)
    Book findByIdMyImplementation(Long id);

    @Query(value = "select * from Book where title=?1", nativeQuery = true)
    Book findByTitle(String title);

    Optional<Book> findById(Long id);
//    @Query(value = "select id, title, isbn, kategoria\n" +
//            "from book\n" +
//            "where \n" +
//            "title = ?1\n" +
//            "or isbn=?1 \n"+
//            "or kategoria=?1", nativeQuery = true)
//    List<Book> getAllBook(String filtr);

//    @Query(value = "select id, title, isbn, kategoria\n" +
//            "from book\n" +
//            "where \n" +
//            "(CASE when ?1 IS NOT NULL OR ?1= '' THEN\n"+
//            "title = ?1\n" +
//            "or isbn=?1 \n"+
//            "or kategoria=?1\n"+
//            "ELSE ID<>0\n" +
//            "END);", nativeQuery = true)
//    List<Book> getAllBook(String filtr);
//
//    @Query(value = "select id, title, isbn, kategoria from book", nativeQuery = true)
//    List<Book> getAllBookWhenFilterIsNull();

}
