package pl.klobut.books_app.GUI;

import org.junit.jupiter.api.BeforeAll;
import pl.klobut.books_app.GUI.db.AuthorRepo;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.Kategoria;

import java.util.Collections;

public class AddBookTest {
    Book book;
    Author author;

    BookRepo bookRepo;
    AuthorRepo authorRepo;

    @BeforeAll
    public void setupData() {
        author= new Author("Jan", "Brzechwa");
        book= new Book("Test Book", "45613", Kategoria.DOKUMENTALNE);

        //author.setBookSet(Collections.singleton(book));
    }

    public void formFieldPopulated() {

    }
}
