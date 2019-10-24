package pl.shopApp.objects;

import pl.shopApp.JdbcLogin;

import java.sql.SQLException;
import java.sql.Statement;

public class UserAccount {

    private final String name;
    private String surname;
    private String email;
    private String login;
    private String password;
    private String user_role;

    public UserAccount(String name, String surname, String email, String login, String password, String user_role) {
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

    public boolean addUserToBase() throws SQLException {
            Statement dbStatement = JdbcLogin.getStatement();
            String addUser = String.format(
                    "insert into tBBHPYyqTO.ShopUser(name,surname,email,login,password,USER_ROLE) values ('%s','%s','%s','%s','%s','%s')",
                    name,surname,email,login,password,user_role
            );
            dbStatement.executeUpdate(addUser);

        return true;
    }
}
