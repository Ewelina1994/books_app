package pl.klobut.books_app.GUI.GUI.list;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.service.BookService;
import pl.klobut.books_app.GUI.db.BookViewRepo;
import pl.klobut.books_app.GUI.db.OpinionRepo;
import pl.klobut.books_app.GUI.entity.BookView;
import pl.klobut.books_app.GUI.entity.Opinion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("details of book")
@Route(value = "details", layout = MainLayout.class)
public class DetailsOfBookGUI extends VerticalLayout
        implements HasUrlParameter<Long> {

   public Long idBook;
    String my;

    DatePicker startReading;
    Select<Boolean> isFinishRead;
    Select<Boolean> youStoppedReading;
    Select<Integer> points;
    TextArea opinion;
    Button saveBookView;
    Dialog dialog = new Dialog();
    Long parameterOfBook;

    OpinionRepo opinionRepo;
    BookViewRepo bookViewRepo;
    BookService bookService;

    @Autowired
    public DetailsOfBookGUI(OpinionRepo opinionRepo, BookViewRepo bookViewRepo, BookService bookService) {
        this.opinionRepo=opinionRepo;
        this.bookViewRepo= bookViewRepo;
        this.bookService = bookService;


    }

    private void initializeComponents() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        FormLayout formLayout= new FormLayout();
        formLayout.addClassName("form");
        startReading= new DatePicker("Start book read", LocalDate.parse(bookViewRepo.getBookViewByBook(idBook).getSTART_READING()));

        isFinishRead= new Select<>();
        isFinishRead.setLabel("Are you finished read this book");
        isFinishRead.setItems(true, false);
        isFinishRead.setValue(bookViewRepo.getBookViewByBook(idBook).getARE_YOU_FINISH_READING());

        youStoppedReading= new Select<>();
        youStoppedReading.setLabel("Are you stopped read this book");
        youStoppedReading.setItems(true, false);
        youStoppedReading.setValue(bookViewRepo.getBookViewByBook(idBook).getYOU_STOPPED_READING());
        List listPoints=new ArrayList<>();
        for(int i=0; i<10; i++){
            listPoints.add(i+1);
        }
        points= new Select<>();
        points.setLabel("Your points");
        points.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        points.setValue(bookViewRepo.getBookViewByBook(idBook).getPOINTS());
        opinion=new TextArea("Your opinion");
        opinion.setValue(bookViewRepo.getBookViewByBook(idBook).getDESCRIPTION());

        saveBookView= new Button("Save Book", VaadinIcon.SAFE.create(), clickEvent-> {
           // showPopupWindow();
            saveBookViewMethod();
            //dialog.open();
        });
        saveBookView.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        formLayout.add(startReading, isFinishRead, youStoppedReading, points, opinion);
        add(formLayout, saveBookView);
    }

    private void showPopupWindow() {
        dialog.open();
        Button confirmButton = new Button("Confirm", event -> {
            saveBookViewMethod();
            //dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> {
            dialog.close();
        });
        dialog.add(new Label("The book was added to database"));
        dialog.add(confirmButton, cancelButton);

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        add(dialog);
        UI.getCurrent().navigate("");
    }
    private void saveBookViewMethod() {
        BookView bookViewUpdate= (BookView) bookViewRepo.findByBookId(idBook);
        Optional<Opinion> opinionObiekt= opinionRepo.findById(bookViewUpdate.getOpinion().getId());

        opinionObiekt.get().setPoints(points.getValue());
        opinionObiekt.get().setDescription(opinion.getValue());
        opinionRepo.save(opinionObiekt.get());

        bookViewUpdate.setStartReading(startReading.getValue());
        bookViewUpdate.setFinishRead(isFinishRead.getValue());
        bookViewUpdate.setYouStoppedReading(youStoppedReading.getValue());
        bookViewRepo.save(bookViewUpdate);

        startReading.clear();
        isFinishRead.clear();
        youStoppedReading.clear();
        points.clear();
        opinion.clear();
        showPopupWindow();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        idBook=id;
        initializeComponents();
     //   bookViewRepo= bookViewRepo.getBookViewByBook(id);
//        Grid<BookViewDTO> grid= new Grid<>(BookViewDTO.class);
//        grid.setClassName("grid");

//        grid.setItems(bookView);
//        List<Grid.Column<BookViewDTO>> columns = grid.getColumns();
//        columns.forEach(
//        add(grid);
       // System.out.println("Przekazany parametr to: "+ this.id);
    }
}
