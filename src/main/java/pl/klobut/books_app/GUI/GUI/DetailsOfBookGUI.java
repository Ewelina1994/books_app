package pl.klobut.books_app.GUI.GUI;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.klobut.books_app.GUI.DTO.BookViewDTO;
import pl.klobut.books_app.GUI.db.BookViewRepo;

import java.util.List;

@PageTitle("details of book")
@Route(value = "details", layout = MainLayout.class)
public class DetailsOfBookGUI extends Div
        implements HasUrlParameter<Long> {

   BookViewRepo bookViewRepo;

   private Long id;
    String my;
    public DetailsOfBookGUI(BookViewRepo bookViewRepo) {
        this.bookViewRepo= bookViewRepo;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        this.id=id;
        Grid<BookViewDTO> grid= new Grid<>(BookViewDTO.class);
        grid.setClassName("grid");
        BookViewDTO bookView= bookViewRepo.getBookViewByBook(id);
        grid.setItems(bookView);
//        List<Grid.Column<BookViewDTO>> columns = grid.getColumns();
//        columns.forEach(
        add(grid);
        System.out.println("Przekazany parametr to: "+ this.id);
    }
}
