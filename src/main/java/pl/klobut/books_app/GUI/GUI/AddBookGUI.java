package pl.klobut.books_app.GUI.GUI;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.db.AuthorRepo;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;
import pl.klobut.books_app.GUI.entity.Kategoria;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@PageTitle("add new book")
@Route(value = "addBook", layout = MainLayout.class)
public class AddBookGUI extends VerticalLayout {
    FormLayout foromLayout;
    Binder<Book> binder = new Binder<>();
    TextField title;
    TextField ISBN;
    Select<Kategoria> select;
    TextField authorName;
    TextField authorSurname;
    Button saveBook;
    Dialog dialog;

    BookRepo bookRepo;
    AuthorRepo authorRepo;
    @Autowired
    public AddBookGUI(BookRepo bookRepo, AuthorRepo authorRepo) {
        this.bookRepo=bookRepo;
        this.authorRepo=authorRepo;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        initializeComponents();

       // GridLayout grid = new GridLayout(3, 2);

//        addClassName("contact-form");
//        add(title);
//        add(ISBN);
//        add(select);
//        add(authorName);
//        add(authorSurname);
//        add(saveBook);
    }

    public void initializeComponents() {
        foromLayout=new FormLayout();
        title=new TextField("Title book", "title");
        ISBN=new TextField("ISBN book", "ISBN");

        select = new Select<>();
        select.setLabel("Category book");
        List<Kategoria> categorytList = Arrays.asList(Kategoria.values());
        select.setItems(categorytList);

        authorName=new TextField("Author name", "name");
        authorSurname=new TextField("Author surname", "surname");
        saveBook =new Button("Save book", click -> saveBook());
        saveBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

//        foromLayout.add(title, ISBN, select, authorName, authorSurname);
        add(foromLayout);
       // saveButton.addClickListener(event -> dialog.open());
    }

    private void saveBook() {


        Book newBook = new Book(title.getValue(), ISBN.getValue(), select.getValue());
        Author autor= new Author(authorName.getValue(), authorSurname.getValue());
        autor.setBookSet(Collections.singleton(newBook));

        bookRepo.save(newBook);

        authorRepo.save(autor);


//        title.clear();
//        ISBN.clear();
//        select.clear();
//        authorName.clear();
//        authorSurname.clear();
        saveBook.addClickShortcut(Key.ENTER);
        UI.getCurrent().navigate("addBookView/"+ newBook.getId());
     //   showPopupWindow();
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
