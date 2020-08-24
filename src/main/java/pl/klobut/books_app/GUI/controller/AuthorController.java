package pl.klobut.books_app.GUI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.klobut.books_app.GUI.DTO.AuthorDTO;
import pl.klobut.books_app.GUI.db.AuthorRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthorController {

    AuthorRepo authorRepo;

    @Autowired
    public AuthorController(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @GetMapping("/getAuthorInfo")
    public List<AuthorDTO> getAuthorByCategory(){
        return authorRepo.getCategoryCount();
    }

    public Map<String, Integer> getCategorCount(){
        HashMap<String, Integer> categories = new HashMap<>();
        authorRepo.getCategoryCount().forEach(book->
                categories.put(book.getKategoria(), book.getCategoryCount()));
        return categories;
    }
}
