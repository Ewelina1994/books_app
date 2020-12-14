package pl.klobut.books_app.GUI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import pl.klobut.books_app.GUI.DTO.OpinionDTO;
import pl.klobut.books_app.GUI.db.BookViewRepo;
import pl.klobut.books_app.GUI.entity.BookView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookViewService {

    BookViewRepo bookViewRepo;

    @Autowired
    public BookViewService(BookViewRepo bookViewRepo) {
        this.bookViewRepo = bookViewRepo;
    }

    public Map<String, Integer> getStopReadingBookCount(){
        HashMap<String, Integer> stopRedaingBook = new HashMap<>();
        bookViewRepo.listaBookViewStopReading().forEach(book->
                stopRedaingBook.put(book.getAre_you_stop_readin(), book.getilosc()));

        return stopRedaingBook;
    }

    public long isFinishReading(boolean b){
        return bookViewRepo.countByIsFinishRead(b);
    }

    public List<OpinionDTO> countAllByOpinion_Points(){
        return bookViewRepo.countAllByOpinion_Points();
    }
}
