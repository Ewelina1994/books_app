package pl.klobut.books_app.GUI.GUI.dashbord;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.controller.AuthorController;
import pl.klobut.books_app.GUI.controller.BookController;

import java.util.Map;

@StyleSheet("/styles/styles.css")
@PageTitle("Dashbord")
@Route(value = "dashbord", layout = MainLayout.class)
public class DashbordGUI extends VerticalLayout {
    AuthorController authorController;
    BookController bookController;
    public DashbordGUI(AuthorController authorController, BookController bookController) {
        this.authorController=authorController;
        this.bookController=bookController;

        addClassName("dashbord-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(
                getInformationAboutMe(),
                getCategoriesChart()
        );
    }

    private Component getCategoriesChart() {

        Chart chart= new Chart(ChartType.PIE);
        DataSeries dataSeries= new DataSeries();
        Map<String, Integer> categories=authorController.getCategorCount();
        categories.forEach((name, number)->
                dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Span getInformationAboutMe() {
        Span span = new Span(String.valueOf(bookController.getAllBooks().size()) + " books");
        span.addClassName("book-count");

        return span;
    }
}
