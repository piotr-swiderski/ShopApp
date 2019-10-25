package pl.shopApp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.JdbcLogin;
import pl.shopApp.objects.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CashierAddController {


    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField login;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    PasswordField confirmPassword;
    @FXML
    ComboBox<String> role_user;
    @FXML
    TableView<UserAccount> tableUser;
    @FXML
    TableColumn<UserAccount, Number> tableID;
    @FXML
    TableColumn<Object, Object> tableName;
    @FXML
    TableColumn<Object, Object> tableSurname;
    @FXML
    TableColumn<Object, Object> tableEmail;
    @FXML
    TableColumn<Object, Object> tableLogin;
    @FXML
    TableColumn<Object, Object> tableRole;


    @FXML
    private void initialize() {
        role_user.getItems().addAll(
                "cashier",
                "admin"
        );
        role_user.setValue("cashier");
        setCellValueFactoryOfTable();
        updateTable();
    }


    @FXML
    private void buttonRegistry() {
        if (confirmPassword.getText().equals(password.getText())) {
            UserAccount userAccount = new UserAccount(
                    null,
                    name.getText(),
                    surname.getText(),
                    email.getText(),
                    login.getText(),
                    password.getText(),
                    role_user.getValue()
            );
            try {
                userAccount.addUserToBase();
            } catch (SQLException e) {
                System.out.println(e + "User isn't add");
            }
            updateTable();
        }
    }

    private void updateTable() {
        List<UserAccount> dataTable = new ArrayList<>();
        try {
            Statement statement = JdbcLogin.getStatement();
            String query = "SELECT ID, name, surname, email, login, USER_ROLE FROM tBBHPYyqTO.ShopUser";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                dataTable.add(new UserAccount(
                        rs.getLong("ID"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("login"),
                        "",
                        rs.getString("user_role")
                ));
            }
            tableUser.getItems().clear();
            tableUser.getItems().addAll(dataTable);
        } catch (SQLException e) {
            System.out.println(e + "User from base isn't download");
        }

    }


    private void setCellValueFactoryOfTable() {
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableRole.setCellValueFactory(new PropertyValueFactory<>("user_role"));
        tableID.setCellValueFactory(new PropertyValueFactory<UserAccount,Number>("ID"));

    }

}
