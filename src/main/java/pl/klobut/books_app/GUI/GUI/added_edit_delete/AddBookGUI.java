package pl.klobut.books_app.GUI.GUI.added_edit_delete;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.db.AuthorRepo;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.Kategoria;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("add new book")
@Route(value = "addBook", layout = MainLayout.class)
public class AddBookGUI extends VerticalLayout {
    FormLayout formLayout;

    TextField title;
    TextField ISBN;
    Select<Kategoria> select;
    TextField authorName;
    TextField authorSurname;
    Button saveBook;
    Button cancel;
    Label validationInfoLabel;
    Dialog dialog;

    Binder<Book> binderBook = new Binder<>();
    Binder<Author> binderAuthor= new Binder<>();

    BookRepo bookRepo;
    AuthorRepo authorRepo;

    @Autowired
    public AddBookGUI(BookRepo bookRepo, AuthorRepo authorRepo) {
        this.bookRepo=bookRepo;
        this.authorRepo=authorRepo;

        initializeComponents();
        addComponents();

    }

    private void addComponents() {
        formLayout.add(title, authorName, select, authorSurname, ISBN);
        HorizontalLayout horizontalLayout= new HorizontalLayout();
        horizontalLayout.add(cancel, saveBook);
        add(formLayout, horizontalLayout, validationInfoLabel);
    }

    public void initializeComponents() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout =new FormLayout();
        title=new TextField("Title book", "title");
        ISBN=new TextField("ISBN book", "ISBN");

        select = new Select<>();
        select.setLabel("Category book");
     //   List<Kategoria> categorytList = Arrays.asList(Kategoria.values());
        select.setItems(EnumSet.allOf(Kategoria.class));

        authorName=new TextField("Author name", "name");
        authorSurname=new TextField("Author surname", "surname");
        saveBook =new Button("Save book", VaadinIcon.SAFE.create(), click -> saveBook());
        saveBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        cancel= new Button("Cancel", click->UI.getCurrent().navigate(""));
        validationInfoLabel=new Label();
//        foromLayout.add(title, ISBN, select, authorName, authorSurname);

       // saveButton.addClickListener(event -> dialog.open());
    }

    private void saveBook() {

        Author newAutor= new Author(authorName.getValue(), authorSurname.getValue());
        Book newBook = new Book(title.getValue(), ISBN.getValue(), select.getValue());
        validationFiels(newBook, newAutor);
            if(validationFiels(newBook, newAutor)==true){
                newAutor.setBookSet(Collections.singleton(newBook));

                bookRepo.save(newBook);
                authorRepo.save(newAutor);
                saveBook.addClickShortcut(Key.ENTER);
                showPopupWindow();
                UI.getCurrent().navigate("addBookView/"+ newBook.getId());
                //   showPopupWindow();
            }
    }
    private boolean validationFiels(Book b, Author a) {
        title.setRequiredIndicatorVisible(true);
        select.setRequiredIndicatorVisible(true);
        authorName.setRequiredIndicatorVisible(true);
        ISBN.setRequiredIndicatorVisible(true);
        authorSurname.setRequiredIndicatorVisible(true);

        binderBook.forField(title)
                .withValidator(new StringLengthValidator("Please add the title", 2, 100))
                .bind(Book::getTitle, Book::setTitle);
        binderAuthor.forField(authorName)
                .withValidator(new StringLengthValidator("Please add the author name", 2, 100))
                .bind(Author::getName, Author::setName);
        binderAuthor.forField(authorSurname)
                .withValidator(new StringLengthValidator("Please add the author surname", 2, 100))
                .bind(Author::getSurname, Author::setSurname);

        if (binderBook.writeBeanIfValid(b)&& binderAuthor.writeBeanIfValid(a)) {
            validationInfoLabel.setText("Saved bean values: book");
            return true;
        } else {
            BinderValidationStatus<Book> validateBook = binderBook.validate();
            BinderValidationStatus<Author> validateAuthor = binderAuthor.validate();
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
            validationInfoLabel.setText("There are errors: " + errorTextBook
            + ", "+errorTextAuthor);
            validationInfoLabel.addClassName("redComunikat");
            return false;
        }
    }

    private void showPopupWindow() {
        dialog = new Dialog();

        Button ok = new Button("Ok", e->dialog.close());
        ok.addClickShortcut(Key.ENTER);
        dialog.addDialogCloseActionListener(e->{
            dialog.close();
        });
        dialog.add(new Label("The book was added to database"));
        dialog.add(new Button("Ok", e->dialog.close()));

        dialog.setWidth("400px");
        dialog.setHeight("150px");

        add(dialog);

    }


}
