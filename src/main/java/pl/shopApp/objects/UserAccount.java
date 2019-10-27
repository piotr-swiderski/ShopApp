package pl.shopApp.objects;

import pl.shopApp.JdbcLogin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccount {

    private Long ID;
    private String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String user_role;

    public UserAccount(Long ID, String name, String surname, String email, String login, String password, String user_role) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.login = login;
        this.password = password;
        this.user_role = user_role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_role() {
        return user_role;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

}

