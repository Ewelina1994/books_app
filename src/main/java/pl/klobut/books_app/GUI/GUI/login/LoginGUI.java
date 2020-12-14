package pl.klobut.books_app.GUI.GUI.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route
@PageTitle("Login")
public class LoginGUI extends VerticalLayout implements BeforeEnterListener{
    LoginForm loginForm= new LoginForm();
    Button registrationBT;

    public LoginGUI() {
        registrationBT= new Button("Create new account", e ->
                registrationBT.getUI().ifPresent(ui ->
                        ui.navigate("registration")));
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        loginForm.setAction("login");
        add(
                new H1("Hi my friends!"),
                loginForm, registrationBT
        );
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .getOrDefault("error", Collections.emptyList())
        .isEmpty()
        )
        {
            loginForm.setError(true);
        }
    }
}
