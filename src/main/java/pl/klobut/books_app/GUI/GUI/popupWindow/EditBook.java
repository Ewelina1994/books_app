package pl.klobut.books_app.GUI.GUI.popupWindow;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.service.AuthorService;
import pl.klobut.books_app.GUI.service.BookService;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.Kategoria;

import java.util.*;
import java.util.stream.Collectors;

public class EditBook extends Dialog {
    BookDTO bookDTO;

    BookService bookService;

    AuthorService authorService;

    VerticalLayout verticalLayout= new VerticalLayout();
    TextField editTitle= new TextField("Tittle");
    TextField editISBN= new TextField("ISBN");
    TextField editAuthorName= new TextField("Author Name");
    TextField editAuthorSurname= new TextField("Author Surname");
    Select<Kategoria> editCategory=new Select<Kategoria>();
    Button saveButton=new Button("Save", e->updateBook());
    Button cancelButton= new Button("Cancel", e->close());

    Grid grid;

    @Autowired
    public EditBook(BookDTO item, BookService bookService, AuthorService authorService, Grid grid) {
        this.bookDTO=item;
        this.bookService = bookService;
        this.authorService = authorService;
        this.grid=grid;
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        init();
    }

    public void init() {

        editTitle.setValue(bookDTO.getTitle());

        editISBN.setValue(bookDTO.getIsbn());

        editAuthorName.setValue(bookDTO.getAuthorName());

        editAuthorSurname.setValue(bookDTO.getAuthorSurname());

        editCategory.setLabel("Category");
        editCategory.setItems(EnumSet.allOf(Kategoria.class));
        editCategory.setValue(Kategoria.valueOf(bookDTO.getCategory()));
//        verticalLayout.add(
        verticalLayout.add(
                editTitle,
                editCategory,
                editISBN,
                editAuthorName,
                editAuthorSurname,
                saveButton,
                cancelButton
        );
        setLayout();
        this.add(verticalLayout);

        this.open();
    }

    private void setLayout() {
        editTitle.setWidth("500px");
        editCategory.setWidth("500px");
        editISBN.setWidth("500px");
        editAuthorName.setWidth("500px");
        editAuthorSurname.setWidth("500px");

        this.setCloseOnOutsideClick(true);
        this.setCloseOnEsc(true);
        this.setMinWidth("720px");
        this.setWidth("720px");
        this.setModal(false);
        this.setDraggable(true);
        this.setResizable(true);
    }

    public boolean updateBook() {
        Book updateBook= bookService.findByTitle(bookDTO.getTitle());
                //new Book(editTitle.getValue(), editISBN.getValue(), editCategory.getValue());
        Author updateAuthor= authorService.finBySurname(bookDTO.getAuthorSurname());
                //new Author(editAuthorName.getValue(), editAuthorSurname.getValue());

        validationFiels(updateBook, updateAuthor);
        if(validationFiels(updateBook, updateAuthor)) {
            updateBook.setISBN(editISBN.getValue());
            updateBook.setTitle(editTitle.getValue());
            updateBook.setKategoria(editCategory.getValue());

            //updateAuthor.setBookSet(Collections.singleton(updateBook));

            updateAuthor.setName(editAuthorName.getValue());
            updateAuthor.setSurname(editAuthorSurname.getValue());

            bookService.save(updateBook);
            authorService.saveAuthor(updateAuthor);
            saveButton.addClickShortcut(Key.ENTER);
            close();

            //rochę nie ładne rozwiązanie ale nie mogę tego wdrozyć w getAll
            grid.setItems(bookService.getAllBooks());
            //UI.getCurrent().getPage().reload();
            return true;
        }
        return false;
    }

    private boolean validationFiels(Book b, Author a) {
        Binder<Book> binderBook = new Binder<>();
        Binder<Author> binderAuthor= new Binder<>();

        editTitle.setRequiredIndicatorVisible(true);
        editCategory.setRequiredIndicatorVisible(true);
        editAuthorName.setRequiredIndicatorVisible(true);
        editISBN.setRequiredIndicatorVisible(true);
        editAuthorSurname.setRequiredIndicatorVisible(true);

        binderBook.forField(editTitle)
                .withValidator(new StringLengthValidator("Please add the title", 2, 100))
                .bind(Book::getTitle, Book::setTitle);
        binderAuthor.forField(editAuthorName)
                .withValidator(new StringLengthValidator("Please add the author name", 2, 100))
                .bind(Author::getName, Author::setName);
        binderAuthor.forField(editAuthorSurname)
                .withValidator(new StringLengthValidator("Please add the author surname", 2, 100))
                .bind(Author::getSurname, Author::setSurname);

        if (binderBook.writeBeanIfValid(b)&& binderAuthor.writeBeanIfValid(a)) {
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

            return false;
        }
    }
}
