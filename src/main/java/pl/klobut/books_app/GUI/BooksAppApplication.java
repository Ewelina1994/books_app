package pl.klobut.books_app.GUI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class BooksAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksAppApplication.class, args);
    }

}
