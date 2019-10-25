package pl.shopApp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
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
    private LoginModel loginModel = new LoginModel();

    @FXML
    TextField loginLogin;
    @FXML
    PasswordField loginPassword;
    @FXML
    Button loginToService;
    @FXML
    RadioButton loginAsAdmin;
    @FXML
    TextField text_conection;
    @FXML
    TextField database_connection;

    @FXML
    private void initialize() {
        dbStatement = jdbcLogin.logToDataBase();
        if(dbStatement != null){
            database_connection.setText("Connected to the database");
        }else{
            database_connection.setText("Not connected to the database");
        }
    }

    @FXML
    private void loginToService(ActionEvent event) {
        System.out.println(getRole());
        try {
            if (this.loginModel.isLogin(loginLogin.getText(), loginPassword.getText(), getRole())) {
                Stage stage = (Stage) this.loginToService.getScene().getWindow();
                stage.close();
                switch (getRole()) {
                    case "ROLE_USER":
                        UserController userController = new UserController();
                        userController.start();
                        break;
                    case "ROLE_ADMIN":
                        Controller controller = new Controller();
                        controller.start();
                        break;
                }
            }else{
                text_conection.setText("Not connection, check your login or password");
            }
        } catch (Exception e) {
            System.out.println(e + "Not login");
            text_conection.setText("Not connection");
        }
    }

    @FXML
    private void buttonRegistration() {
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

    private String getRole() {
        if (loginAsAdmin.isSelected()) {
            return "ROLE_ADMIN";
        } else {
            return "ROLE_USER";
        }
    }


    public Statement getDbStatement() {
        return dbStatement;
    }

    public void setDbStatement(Statement dbStatement) {
        this.dbStatement = dbStatement;
    }
}
