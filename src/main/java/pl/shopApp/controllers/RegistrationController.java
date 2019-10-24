package pl.shopApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.shopApp.JdbcLogin;
import pl.shopApp.PasswordEncoding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RegistrationController {

    private Statement dbStatement;
    private String email;
    private PasswordEncoding passwordEncoding;

    @FXML
    TextField registrationLogin;
    @FXML
    PasswordField registrationPassword;
    @FXML
    PasswordField registrationConfirmPassword;
    @FXML
    TextField registrationEmail;

    @FXML
    private void initialize() {
        dbStatement = JdbcLogin.getStatement();
        passwordEncoding = new PasswordEncoding();
    }

    @FXML
    private void registrationToService() {

        String login = getServiceLogin();
        String password = passwordEncoding.getHashPassword(getServicePassword());
        String emailToBase = getEmail();
        addUserToBase(login, password, emailToBase);
    }


    private void addUserToBase(String login, String password, String email) {
        try {
            String loginIsFree = String.format(
                    "SELECT Login, Email FROM tBBHPYyqTO.Users WHERE Login = '%s' or Email = '%s'", login, email
            );

            ResultSet loginQuery = dbStatement.executeQuery(loginIsFree);
            if (!loginQuery.next()) {
                System.out.println("You can regist..");
                String addUser = String.format(
                        "insert into tBBHPYyqTO.Users(Login,Password,Email) values ('%s','%s','%s')",
                        login, password, email
                );
                    dbStatement.executeUpdate(addUser);
                System.out.println("registration your account");
                // todo
            } else {
                System.out.println("That login or email is in base");
                // todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        if (registrationEmail.getText().matches("[\\w]+@[A-Za-z0-9]+[.][A-Za-z]+")) {
            this.email = registrationEmail.getText();
        } else {
            throw new IllegalArgumentException("Email is incorrect");
        }
        return email;
    }

    private String getServicePassword() {
        if(!registrationPassword.getText().equals(registrationConfirmPassword.getText())){
            throw new IllegalArgumentException("The passwords are not equals");
        }
        if(!registrationPassword.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_.,])(?=\\S+$).{8,}$")){
            throw  new IllegalArgumentException("The password must have letters, numbers and special character and least 8 chars");
        }
        return registrationPassword.getText();
    }

    private String getServiceLogin() {
        if(!registrationLogin.getText().matches("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")){
            throw  new IllegalArgumentException("The login is wrong");
        }
        return registrationLogin.getText();
    }

}
