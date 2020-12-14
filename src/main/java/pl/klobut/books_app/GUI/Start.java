package pl.klobut.books_app.GUI;

import org.springframework.stereotype.Component;
import pl.klobut.books_app.GUI.db.*;
import pl.klobut.books_app.GUI.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Start {

    private AuthorRepo authorRepo;
    private BookRepo bookRepo;
    private BookViewRepo bookViewRepo;
    private OpinionRepo opinionRepo;
    UserRepo userRepo;

    public Start(AuthorRepo authorRepo, BookRepo bookRepo, BookViewRepo bookViewRepo, OpinionRepo opinionRepo, UserRepo userRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.bookViewRepo=bookViewRepo;
        this.opinionRepo=opinionRepo;
        this.userRepo=userRepo;

        Book book = new Book("Mroczna Wieża", "hdsjfak", Kategoria.FANTASY);
        Book book2 = new Book("Bazar złych snów", "45456", Kategoria.HORRORY);
        Book book3 = new Book("Czarna bezgwiezdna noc", "wqewew", Kategoria.FANTASY);

        Author author = new Author("Stephen", "King");
        author.setBookSet(Stream.of(book, book2, book3).collect(Collectors.toSet()));

        Book book4 = new Book("365 dni", "fytyery", Kategoria.ROMANTYCZNE);
        Book book5 = new Book("Ten dzień", "fhtgfghfg", Kategoria.ROMANTYCZNE);

        Author author1 = new Author("Blanka", "Lipińska");
        author1.setBookSet(Stream.of(book4, book5).collect(Collectors.toSet()));

        bookRepo.save(book);
        bookRepo.save(book2);
        bookRepo.save(book3);
        bookRepo.save(book4);
        bookRepo.save(book5);
        authorRepo.save(author);
        authorRepo.save(author1);

        Opinion opinion1= new Opinion(8, "this book is very nice and it is reading quickly");
        Opinion opinion2= new Opinion(4, "this book is hard to read and has very small letters");
        Opinion opinion3= new Opinion(10, "great book, I read it in one breath");
        Opinion opinion4= new Opinion(8, "I think it's good but the author may have devoted more time to it");
        Opinion opinion5= new Opinion(2, "didn't speak to me, I think it's boring");

        opinionRepo.save(opinion1);
        opinionRepo.save(opinion2);
        opinionRepo.save(opinion3);
        opinionRepo.save(opinion4);
        opinionRepo.save(opinion5);

        BookView bookView1= new BookView(book, LocalDate.of(2018, 3, 15), true, false, opinion1);
        BookView bookView2= new BookView(book2, LocalDate.of(2019, 5, 20), true, false, opinion2);
        BookView bookView3= new BookView(book3, LocalDate.of(2018, 10, 17), true, false, opinion3);
        BookView bookView4= new BookView(book4, LocalDate.of(2020, 3, 15), true, false, opinion4);
        BookView bookView5= new BookView(book5, LocalDate.of(2020, 5, 15), false, true, opinion5);


        bookViewRepo.save(bookView1);
        bookViewRepo.save(bookView2);
        bookViewRepo.save(bookView3);
        bookViewRepo.save(bookView4);
        bookViewRepo.save(bookView5);

        User user= new User("user", "1234", LocalDate.now());
        userRepo.save(user);
    }
}
