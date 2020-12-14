package pl.klobut.books_app.GUI.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String login;
    String password;
    @Transient
    private String passwordConfirm;
    LocalDate createAccount;
    @ManyToMany
    private Set<Role> roles;

    public User(String login, String password, LocalDate createAccount) {

        this.login = login;
        this.password = password;
        this.createAccount = createAccount;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(LocalDate createAccount) {
        this.createAccount = createAccount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;


    }
}
