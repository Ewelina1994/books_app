package pl.klobut.books_app.GUI.GUI.list;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.DTO.BookDTO;
import pl.klobut.books_app.GUI.GUI.DetailsOfBookGUI;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.controller.BookController;
import pl.klobut.books_app.GUI.db.BookRepo;
import pl.klobut.books_app.GUI.entity.Book;

@Route(value = "all_books", layout = MainLayout.class)
@PageTitle("Lists of all books")
public class getAllBook extends VerticalLayout {

    BookController bookController;
    BookRepo bookRepo;
//    List<BookDTO> getAllBooks= bookRepo.getAllBook(searchBox.getValue());
    Grid<BookDTO> grid= new Grid(Book.class);
    TextField filtrText = new TextField(null, "serch by keywords");
    ListDataProvider<BookDTO> dataProvider;
    @Autowired
    public getAllBook(BookController bookController, BookRepo bookRepo) {
        this.bookController = bookController;
        this.bookRepo=bookRepo;
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

        add(grid);

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
                .addColumn(book -> book.getAuthorName())
                .setHeader("Category");
        Grid.Column<BookDTO> authorNameColumn = grid.
                addColumn(BookDTO::getIsbn)
                .setHeader("Name Author");
        Grid.Column<BookDTO> authorSurnameColumn = grid
                .addColumn(book -> book.getAuthorSurname())
                .setHeader("Surname Author");

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

//    public void updateList() {
//        //grid.setItems(bookRepo.getAllBook(filtrText.getValue()));
//        //List<Book> booksList = bookRepo.getAllBook(filtrText.getValue());
//        grid.setItems(bookController.filtrSerchText(filtrText.getValue()));
////        grid.setItems(bookRepo.getAllBook(filtrText.getValue()));
//    }
}
