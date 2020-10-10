package pl.klobut.books_app.GUI.GUI.added_edit_delete;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.db.BookViewRepo;
import pl.klobut.books_app.GUI.db.OpinionRepo;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.BookView;
import pl.klobut.books_app.GUI.entity.Opinion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("add new book")
@Route(value = "addBookView", layout = MainLayout.class)
public class AddBookViewGUI extends VerticalLayout implements HasUrlParameter<Long> {

    DatePicker startReading;
    ComboBox<Boolean> isFinishRead;
    Select<Boolean> youStoppedReading;
    Select<Integer> points;
    TextArea opinionTextArea;
    Button saveButton;
    Dialog dialog = new Dialog();
    Long parameterOfBook;

    OpinionRepo opinionRepo;
    BookViewRepo bookViewRepo;
    BookRepo bookRepo;

    Binder<BookView> binderBookView = new Binder<>();
    Binder<Opinion> binderOpinion= new Binder<>();

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
        FormLayout formLayout=new FormLayout();
        startReading= new DatePicker("Start book read", LocalDate.now());

        isFinishRead= new ComboBox<>();
        isFinishRead.setLabel("Are you finished read this book");
        isFinishRead.setItems(true, false);
        isFinishRead.setValue(false);

        youStoppedReading= new Select<>();
        youStoppedReading.setLabel("Are you stopped read this book");
        youStoppedReading.setItems(true, false);
        youStoppedReading.setValue(false);

        List listPoints=new ArrayList<>();
        for(int i=0; i<10; i++){
            listPoints.add(i+1);
        }
        points= new Select<>();
        points.setLabel("Your points");
        points.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        points.setValue(5);

        opinionTextArea =new TextArea("Your opinion", "I think this book..");
        saveButton = new Button("Save Book", VaadinIcon.SAFE.create(), clickEvent-> {
            saveBookViewMethod();
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        });
        formLayout.add(startReading, isFinishRead, youStoppedReading, points, opinionTextArea);
        Button cancelButton= new Button("Cancel", VaadinIcon.BACKSPACE.create(), click-> UI.getCurrent().navigate(""));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(cancelButton, saveButton);

        add(formLayout, horizontalLayout);
    }

    private void saveBookViewMethod() {
        Opinion opinionObiekt= new Opinion();
        if(points.getValue()!=null){
            opinionObiekt.setPoints(points.getValue());
        }

        opinionObiekt.setDescription(opinionTextArea.getValue());
        BookView bookView= new BookView(bookRepo.findByIdMyImplementation(this.parameterOfBook), startReading.getValue(), isFinishRead.getValue(), youStoppedReading.getValue(), opinionObiekt);

        if(validationFiels(bookView, opinionObiekt)==true) {

            opinionRepo.save(opinionObiekt);

            bookViewRepo.save(bookView);
            startReading.clear();
            isFinishRead.clear();
            youStoppedReading.clear();
            points.clear();
            opinionTextArea.clear();
            showPopupWindow();
        }
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

    private boolean validationFiels(BookView bookView, Opinion opinion) {
        startReading.setRequired(true);
        isFinishRead.setRequired(true);
        isFinishRead.setErrorMessage("This field is require");
        youStoppedReading.setRequiredIndicatorVisible(true);
        points.setRequiredIndicatorVisible(true);
        opinionTextArea.setRequired(true);


        binderBookView.forField(startReading)
                .withValidator(new DateRangeValidator("\n" +
                        "Date cannot be less than 01-01-2000 and later than now", LocalDate.of(2000, 1, 1), LocalDate.now()))
                .bind(BookView::getStartReading, BookView::setStartReading);

        startReading.setRequired(true);


        binderOpinion.forField(opinionTextArea)
                .withValidator(new StringLengthValidator("Please add the opinion", 5, 300))
                .bind(Opinion::getDescription, Opinion::setDescription);

        if (binderBookView.writeBeanIfValid(bookView)&& binderOpinion.writeBeanIfValid(opinion)) {
            return true;
        } else {
            BinderValidationStatus<BookView> validateBook = binderBookView.validate();
            BinderValidationStatus<Opinion> validateAuthor = binderOpinion.validate();
            String errorTextBook = validateBook.getFieldValidationStatuses()
                    .stream().filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(Optional::get).distinct()
                    .collect(Collectors.joining(", "));
            String errorTextAuthor= validateAuthor.getFieldValidationStatuses()
                    .stream().filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(Optional::get).distinct()
                    .collect(Collectors.joining(", "));
            return false;
        }
    }

}
