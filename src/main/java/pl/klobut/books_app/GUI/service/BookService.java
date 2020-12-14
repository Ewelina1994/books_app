package pl.klobut.books_app.GUI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<BookDTO> getAllBooks(){
        return bookRepo.getAllBook();
    }

    public Book findByTitle(String title){
        return bookRepo.findByTitle(title);
    }

    public void save(Book book){
        bookRepo.save(book);
    }

    public void delete(Book deleteBook) {
        bookRepo.delete(deleteBook);
    }

    public Book findByIdMyImplementation(Long id){
        return bookRepo.findByIdMyImplementation(id);
    }

    public Optional<Book> findById(Long id){
        return bookRepo.findById(id);
    }



//    @GetMapping
//    public Optional<Book> getById(@RequestParam Long id) {
//        return bookRepo.findById(id);
//    }

//    @PutMapping
//    public Book updateBook(@RequestBody Book book) {
//       return bookRepo.save(book);
//    }
//
//    @DeleteMapping
//    public void deleteBook(@RequestParam Long id) {
//        bookRepo.deleteById(id);
//    }
//    public List<Book> filtrSerchText(String findPosition) {
//        if(findPosition==null||findPosition==""){
//            return bookRepo.getAllBookWhenFilterIsNull();
//        }
//        else{
//            return bookRepo.getAllBook(findPosition);
//        }
//
//    }

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
