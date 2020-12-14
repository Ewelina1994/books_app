package pl.klobut.books_app.GUI.GUI.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.klobut.books_app.GUI.GUI.MainLayout;
import pl.klobut.books_app.GUI.db.UserRepo;
import pl.klobut.books_app.GUI.entity.User;

import java.time.LocalDate;

@Route(value = "registration")
@PageTitle("Registration new user")
public class Registration extends VerticalLayout {
    FormLayout formLayout;
    TextField firstNameTF;
    TextField lastNameTF;
    TextField loginTF;
    PasswordField passwordTF;
    PasswordField confirmPasswordTF;

    Button saveUser;

    UserRepo userRepo;

    @Autowired
    public Registration(UserRepo userRepo) {
        userRepo=userRepo;
        formLayout= new FormLayout();
        loginTF= new TextField("Login");
        passwordTF= new PasswordField("Password");
        confirmPasswordTF= new PasswordField("Confirm password");

        saveUser= new Button("Save user", e->saveUser());
        formLayout.add(firstNameTF, lastNameTF, loginTF, passwordTF, confirmPasswordTF);
        add(formLayout, saveUser);
    }

    private void saveUser() {
        User newUser= new User( loginTF.getValue(), passwordTF.getValue(), LocalDate.now());
        userRepo.save(newUser);
    }
}
