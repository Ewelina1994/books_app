package pl.klobut.books_app.GUI.GUI.dashbord;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.klobut.books_app.GUI.DTO.OpinionDTO;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.service.AuthorService;
import pl.klobut.books_app.GUI.service.BookService;
import pl.klobut.books_app.GUI.service.BookViewService;

import java.util.List;
import java.util.Map;

@StyleSheet("/styles/styles.css")
@PageTitle("Dashbord")
@Route(value = "dashbord", layout = MainLayout.class)
public class DashbordGUI extends VerticalLayout {
    AuthorService authorService;
    BookViewService bookViewService;
    BookService bookService;
    Board board;
    public DashbordGUI(AuthorService authorService, BookViewService bookViewService, BookService bookService) {
        this.authorService = authorService;
        this.bookViewService = bookViewService;
        this.bookService = bookService;

        addClassName("dashbord-view");


        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(
               // getInformationAboutMe(),
                getCategoriesChart(),
                howManyBooksDontStopReading(),
                howManyBookInOpinionCount()
        );
    }

    private Component getCategoriesChart() {

        Chart chart= new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();

        conf.setTitle("Liczba książek w danej kategorii");

//        PlotOptionsPie plotOptions = new PlotOptionsPie();
//        plotOptions.setCursor(Cursor.POINTER);
//        DataLabels dataLabels = new DataLabels();
//        dataLabels.setEnabled(true);
//        dataLabels
//                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
//        plotOptions.setDataLabels(dataLabels);
//        conf.setPlotOptions(plotOptions);

        DataSeries dataSeries= new DataSeries();
        Map<String, Integer> categories= authorService.getCategorCount();
        categories.forEach((name, number)->
                dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);

        return chart;
    }

    private Component howManyBooksDontStopReading() {

        Chart chart = new Chart(ChartType.COLUMN);
        Configuration conf = chart.getConfiguration();

        conf.setTitle("Czy przeczytałeś książkę do końca");
        conf.getLegend().setEnabled(false);

        XAxis x = new XAxis();
        x.setType(AxisType.CATEGORY);
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setTitle("Ilość książek");
        conf.addyAxis(y);

        DataSeries dataSeries= new DataSeries();

        long isFinishReadingTrue= bookViewService.isFinishReading(true);
        long isFinishReadingFalse= bookViewService.isFinishReading(false);

        dataSeries.add(new DataSeriesItem("TAK", isFinishReadingTrue));
        dataSeries.add(new DataSeriesItem("NIE", isFinishReadingFalse));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Component howManyBookInOpinionCount(){
        Chart chart= new Chart(ChartType.COLUMN);
        Configuration conf= chart.getConfiguration();
        conf.setTitle("Liczba ksiażek z podziałem na liczbę punktów");

        YAxis y = new YAxis();
        y.setTitle("Ilość książek");
        conf.addyAxis(y);

        DataSeries dataSeries= new DataSeries();

        List<OpinionDTO> opinionDTO = bookViewService.countAllByOpinion_Points();
        opinionDTO.forEach(opinion->
                dataSeries.add(new DataSeriesItem(opinion.getPoints(), opinion.getIlosc())));

        chart.getConfiguration().setSeries(dataSeries);
        return  chart;

    }

    private Span getInformationAboutMe() {
        Span span = new Span(String.valueOf(bookService.getAllBooks().size()) + " books");
        span.addClassName("book-count");

        return span;
    }
}
