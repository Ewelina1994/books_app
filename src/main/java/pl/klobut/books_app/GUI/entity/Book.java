package pl.klobut.books_app.GUI.entity;

import com.vaadin.flow.function.ValueProvider;
import pl.klobut.books_app.GUI.DTO.BookDTO;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String ISBN;
    @Enumerated(EnumType.STRING)
    private Kategoria kategoria;

    public Book(String title, String ISBN, Kategoria kategoria) {
        this.title = title;
        this.ISBN = ISBN;
        this.kategoria = kategoria;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }


}
