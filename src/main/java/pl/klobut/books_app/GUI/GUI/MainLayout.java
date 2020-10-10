package pl.klobut.books_app.GUI.GUI;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import pl.klobut.books_app.GUI.GUI.added_edit_delete.AddBookGUI;
import pl.klobut.books_app.GUI.GUI.dashbord.DashbordGUI;
import pl.klobut.books_app.GUI.GUI.list.getAllBook;

@PWA(
        name = "My books",
        shortName = "books",
        offlineResources = {
                "public/styles/offline.css",
        "./images/offline.png"}
)
@StyleSheet("/styles/styles.css")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
public class MainLayout extends AppLayout {
    
    public MainLayout() {
        crateteHeader();
        crateteDrawer();
    }

    private void crateteDrawer() {
        RouterLink listLink = new RouterLink("List", getAllBook.class);
        RouterLink dashbord = new RouterLink("Dashbord", DashbordGUI.class);
        RouterLink addBook = new RouterLink("Add new book", AddBookGUI.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(listLink, dashbord, addBook));
    }

    private void crateteHeader() {
        H2 logo = new H2("MyBooks");
        logo.addClassName("logo");

        Anchor logout= new Anchor("/logout", "Log out");
        logout.addClassName("logout");
        HorizontalLayout header= new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.setHeight("70px");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        
        addToNavbar(header);
    }
}
