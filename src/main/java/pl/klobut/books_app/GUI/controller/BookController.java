package pl.klobut.books_app.GUI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.db.BookRepo;

import java.util.List;

@RestController
public class BookController {
    BookRepo bookRepo;

    @Autowired
    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


//    public List<Book> filtrSerchText(String findPosition) {
//        if(findPosition==null||findPosition==""){
//            return bookRepo.getAllBookWhenFilterIsNull();
//        }
//        else{
//            return bookRepo.getAllBook(findPosition);
//        }
//
//    }
    public List<BookDTO> getAllBooks(){
        return bookRepo.getAllBook();
    }

//    public Map<String, Integer> getCategorCount(){
//        HashMap<String, Integer> categories = new HashMap<>();
//        bookRepo.categoriesCount().forEach(book->
//                categories.putAll(categories));
//       return categories;
//    }
//    public Map<String, Integer> getCategory() {
//        HashMap<String, Integer>category= new HashMap<>();
//        getAllBooks().forEach(book-> category.put(book.getCategory(), book.getCategory().));
//    }
}