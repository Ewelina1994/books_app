package pl.klobut.books_app.GUI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import org.junit.jupiter.api.BeforeAll;
import pl.klobut.books_app.GUI.GUI.AddBookGUI;
import pl.klobut.books_app.GUI.db.AuthorRepo;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.Kategoria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddBookTest {
    Book book;
    Author author;

    BookRepo bookRepo;
    AuthorRepo authorRepo;

    @BeforeAll
    public void setupData() {
        book= new Book("Test Book", "45613", Kategoria.DOKUMENTALNE);
        author= new Author("Jan", "Brzechwa");
        author.setBookSet(Collections.singleton(book));
    }

    public void formFieldPopulated() {

    }
}
