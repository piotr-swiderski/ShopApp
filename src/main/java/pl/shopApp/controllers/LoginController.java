package pl.shopApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.shopApp.JdbcLogin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {

    private JdbcLogin jdbcLogin;
    private Statement dbStatement;
    private String loginService;
    private String passwordService;

    @FXML
    TextField loginLogin;
    @FXML
    PasswordField loginPassword;

    @FXML
    private void initialize() {
        dbStatement = jdbcLogin.logToDataBase();
    }

    @FXML
    private void loginToService() {
        try {
            String sql = String.format("SELECT Login, Password FROM tBBHPYyqTO.Users WHERE Login = '%s'", loginLogin.getText());
            ResultSet rs = dbStatement.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("Login isn't in base");
            } else {
                if (rs.getString("Password").equals(loginPassword.getText())) {
                    System.out.println("Login");
                } else {
                    System.out.println("Wrong password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't read from DB information from table User");
        }
    }

    @FXML
    private void buttonRegistration(){
        Stage stage = new Stage();
        Parent parent;
        try {
            FXMLLoader loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\Registration.fxml").toURI().toURL());
            parent = loader.load();
            stage.setTitle("Registration");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public Statement getDbStatement() {
        return dbStatement;
    }

    public void setDbStatement(Statement dbStatement) {
        this.dbStatement = dbStatement;
    }
}
