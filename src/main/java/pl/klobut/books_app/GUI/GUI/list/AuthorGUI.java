package pl.klobut.books_app.GUI.GUI.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.klobut.books_app.GUI.DTO.AuthorDTO;
import pl.klobut.books_app.GUI.db.AuthorRepo;

import java.util.List;

@Route
public class AuthorGUI extends VerticalLayout {

    private AuthorRepo authorRepo;

    public AuthorGUI(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;

        List<AuthorDTO> authorList = authorRepo.getCategoryCount();

        Grid<AuthorDTO> grid = new Grid<>(AuthorDTO.class);
        grid.setItems(authorList);
        add(grid);


    }
}
