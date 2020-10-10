package pl.klobut.books_app.GUI.GUI.list;

import com.helger.commons.callback.exception.CollectingExceptionCallback;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.action.internal.CollectionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.GUI.popupWindow.EditBook;
import pl.klobut.books_app.GUI.controller.AuthorController;
import pl.klobut.books_app.GUI.controller.BookController;
import pl.klobut.books_app.GUI.entity.Author;
import pl.klobut.books_app.GUI.entity.Book;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Lists of all books")
public class getAllBook extends VerticalLayout{

    BookController bookController;
    AuthorController authorController;
//    List<BookDTO> getAllBooks= bookRepo.getAllBook(searchBox.getValue());
    Grid<BookDTO> grid= new Grid(Book.class);
    TextField filtrText = new TextField(null, "serch by keywords");
    ListDataProvider<BookDTO> dataProvider;
    EditBook editBook;


    @Autowired
    public getAllBook(BookController bookController,  AuthorController authorController) {
        this.bookController = bookController;
        this.authorController=authorController;
       // filtrText.setClearButtonVisible(true);
        grid.setClassName("grid");
        setHeaderInGridAndAddFilters();

        //wyrównanie auto szerokość kolumn
        grid.getColumns().forEach(col-> col.setAutoWidth(true));
        grid.addItemClickListener(
                event -> UI.getCurrent().navigate("details/" + event.getItem().getId())
                        //UI.getCurrent().navigate("details")
                        //detailsOfBook(event.getItem().getTitle())

        );

        for (Grid.Column<BookDTO> column : grid.getColumns()) {
            addClassName("with-hand");

        }
       // grid.addClassName("with-hand");

        add(grid);

        // dataProvider.addDataProviderListener(CollectionAction);

//        add(filtrText, grid);
//        filtrText.setValueChangeMode(ValueChangeMode.EAGER);
//        filtrText.addValueChangeListener(e -> updateList());
//        updateList();
    }

    private void setHeaderInGridAndAddFilters() {
        grid.removeAllColumns();
        dataProvider = new ListDataProvider<>(
                bookController.getAllBooks());
        grid.setDataProvider(dataProvider);
        Grid.Column<BookDTO> titleColumn = grid
                .addColumn(BookDTO::getTitle).setHeader("Title");
        Grid.Column<BookDTO> isbnColumn = grid.addColumn(BookDTO::getIsbn)
                .setHeader("ISBN");
        Grid.Column<BookDTO> kategoriaColumn = grid
                .addColumn(book -> book.getCategory())
                .setHeader("Category");
        Grid.Column<BookDTO> authorNameColumn = grid.
                addColumn(BookDTO::getAuthorName)
                .setHeader("Name Author");
        Grid.Column<BookDTO> authorSurnameColumn = grid
                .addColumn(book -> book.getAuthorSurname())
                .setHeader("Surname Author");

        grid.addComponentColumn(item->{
            Button editButton=new Button("Edit", VaadinIcon.EDIT.create(), e->updateBook(item));
            //editButton.addClickListener(click->{
            return editButton;
            });

        grid.addComponentColumn(item->{
            Button deleteButton= new Button(VaadinIcon.CLOSE_BIG.create(), e->deleteBook(item));
            return deleteButton;
        });

        HeaderRow filterRow = grid.appendHeaderRow();
// First filter
        TextField titleTF = new TextField();
        titleTF.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase(book.getTitle(),
                        titleTF.getValue())));

        titleTF.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(titleColumn).setComponent(titleTF);
        titleTF.setSizeFull();
        titleTF.setPlaceholder("Filter");

// Second filter
        TextField isbnTF = new TextField();
        isbnTF.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(
                        String.valueOf(book.getIsbn()), isbnTF.getValue())));

        isbnTF.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(isbnColumn).setComponent(isbnTF);
        isbnTF.setSizeFull();
        isbnTF.setPlaceholder("Filter");

// Third filter
        TextField categoryTF = new TextField();
        categoryTF.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(
                        book.getCategory(), categoryTF.getValue())));

        categoryTF.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(kategoriaColumn).setComponent(categoryTF);
        categoryTF.setSizeFull();
        categoryTF.setPlaceholder("Filter");

        // Four filter
        TextField authorNameTF = new TextField();
        isbnTF.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(
                        String.valueOf(book.getAuthorName()), authorNameTF.getValue())));

        authorNameTF.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(authorNameColumn).setComponent(authorNameTF);
        authorNameTF.setSizeFull();
        authorNameTF.setPlaceholder("Filter");

// Five filter
        TextField authorSurNameTF = new TextField();
        authorSurNameTF.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(
                        book.getAuthorSurname(), authorSurNameTF.getValue())));

        authorSurNameTF.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(authorSurnameColumn).setComponent(authorSurNameTF);
        authorSurNameTF.setSizeFull();
        authorSurNameTF.setPlaceholder("Filter");
    }

    private void deleteBook(BookDTO bookDTO) {
        Dialog deleteDialog= new Dialog();
        deleteDialog.add(new H6("Are you sure you want to delete this book along with the review?"));
        deleteDialog.add(new Button("Cancel", e->deleteDialog.close()));
        Button deleetButton= new Button("OK", e->deleteConfirme(bookDTO, deleteDialog));
        deleetButton.addClassName("deleteButton");
        deleteDialog.add(deleetButton);

        deleteDialog.open();
    }

    private void deleteConfirme(BookDTO bookDTO, Dialog dialog) {
        Book deleteBook=bookController.findByTitle(bookDTO.getTitle());
        Author deleteAuthor= authorController.finBySurname(bookDTO.getAuthorSurname());

        bookController.delete(deleteBook);
        dialog.close();
        updateList();
    }

    @EventListener
    private void updateBook(BookDTO item) {
        editBook=new EditBook(item, bookController, authorController, grid);

//        if(editBook.updateBook()){
//            updateList();
//        }
        dataProvider.refreshAll();
        grid.setDataProvider(dataProvider);
        grid.setDataProvider(grid.getDataProvider());

//        Registration registration = dataProvider.addDataProviderListener(event -> {
//            System.out.println("moj ewent: "+event);
//            dataProvider.refreshAll();
//            updateList();
//        });
      //  UI.getCurrent().getPage().reload();
    }

    private void updateList() {
        grid.setItems(bookController.getAllBooks());
        //dataProvider.refreshAll(); czemu to nie działa
    }


//    public void updateList() {
//        //grid.setItems(bookRepo.getAllBook(filtrText.getValue()));
//        //List<Book> booksList = bookRepo.getAllBook(filtrText.getValue());
//        grid.setItems(bookController.filtrSerchText(filtrText.getValue()));
////        grid.setItems(bookRepo.getAllBook(filtrText.getValue()));
//    }
}
