package pl.klobut.books_app.GUI.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class BookView {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @ManyToOne
    Book book;
    LocalDate startReading;
    Boolean isFinishRead;
    Boolean youStoppedReading;
    @ManyToOne
    Opinion opinion;

    public BookView(Book book, LocalDate startReading, Boolean isFinishRead, Boolean youStoppedReading, Opinion opinion) {
        this.book = book;
        this.startReading = startReading;
        this.isFinishRead = isFinishRead;
        this.youStoppedReading = youStoppedReading;
        this.opinion = opinion;
    }

    public BookView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getStartReading() {
        return startReading;
    }

    public void setStartReading(LocalDate startReading) {
        this.startReading = startReading;
    }

    public Boolean getFinishRead() {
        return isFinishRead;
    }

    public void setFinishRead(Boolean finishRead) {
        isFinishRead = finishRead;
    }

    public Boolean getYouStoppedReading() {
        return youStoppedReading;
    }

    public void setYouStoppedReading(Boolean youStoppedReading) {
        this.youStoppedReading = youStoppedReading;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }
}
