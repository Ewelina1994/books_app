package pl.klobut.books_app.GUI.GUI;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.db.BookViewRepo;
import pl.klobut.books_app.GUI.db.OpinionRepo;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.BookView;
import pl.klobut.books_app.GUI.entity.Opinion;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PageTitle("add new book")
@Route(value = "addBookView", layout = MainLayout.class)
public class AddBookViewGUI extends VerticalLayout implements HasUrlParameter<Long> {
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
    BookRepo bookRepo;

    @Autowired
    public AddBookViewGUI(OpinionRepo opinionRepo, BookViewRepo bookViewRepo, BookRepo bookRepo) {
        this.opinionRepo=opinionRepo;
        this.bookViewRepo= bookViewRepo;
        this.bookRepo=bookRepo;
        initializeComponents();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long l) {
        this.parameterOfBook= l;
    }

    private void initializeComponents() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        startReading= new DatePicker("Start book read", LocalDate.now());

        isFinishRead= new Select<>();
        isFinishRead.setLabel("Are you finished read this book");
        isFinishRead.setItems(true, false);

        youStoppedReading= new Select<>();
        youStoppedReading.setLabel("Are you stopped read this book");
        youStoppedReading.setItems(true, false);
        List listPoints=new ArrayList<>();
        for(int i=0; i<10; i++){
            listPoints.add(i+1);
        }
        points= new Select<>();
        points.setLabel("Your points");
        points.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        opinion=new TextArea("Your opinion", "I think this book..");

        saveBookView= new Button("Save Book", VaadinIcon.SAFE.create(), clickEvent-> {showPopupWindow();
        dialog.open();
        });
        saveBookView.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        add(startReading, isFinishRead, youStoppedReading, points, opinion, saveBookView);
    }

    private void saveBookViewMethod() {
        Opinion opinionObiekt= new Opinion((int)points.getValue(), opinion.getValue());
        opinionRepo.save(opinionObiekt);
        BookView bookView= new BookView(bookRepo.findByIdMyImplementation(this.parameterOfBook), startReading.getValue(), isFinishRead.getValue(), youStoppedReading.getValue(), opinionObiekt);
                //startReading.getValue(), isFinishRead.getValue(), youStoppedReading.getValue(), opinionObiekt);
        bookViewRepo.save(bookView);
        startReading.clear();
        isFinishRead.clear();
        youStoppedReading.clear();
        points.clear();
        opinion.clear();
        showPopupWindow();
    }
    private void showPopupWindow() {
        dialog.open();
        Button confirmButton = new Button("Confirm", event -> {
            saveBookViewMethod();
            dialog.close();
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
}
