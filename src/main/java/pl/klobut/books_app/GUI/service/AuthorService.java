package pl.klobut.books_app.GUI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.klobut.books_app.GUI.DTO.AuthorDTO;
import pl.klobut.books_app.GUI.db.AuthorRepo;
import pl.klobut.books_app.GUI.entity.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorService {

    AuthorRepo authorRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<AuthorDTO> getAuthorByCategory(){
        return authorRepo.getCategoryCount();
    }

    public Map<String, Integer> getCategorCount(){
        HashMap<String, Integer> categories = new HashMap<>();
        authorRepo.getCategoryCount().forEach(book->
                categories.put(book.getKategoria(), book.getCategoryCount()));
        return categories;
    }

    public Author finBySurname(String surname){
        return authorRepo.finBySurname(surname);
    }

    public void saveAuthor(Author author){
        authorRepo.save(author);
    }
}
