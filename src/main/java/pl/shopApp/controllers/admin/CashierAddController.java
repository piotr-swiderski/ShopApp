package pl.shopApp.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.JdbcLogin;
import pl.shopApp.controllers.DBQueries;
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
    TextField textError;


    @FXML
    private void initialize() {
        role_user.getItems().addAll(
                "ROLE_USER",
                "ROLE_ADMIN"
        );
        role_user.setValue("ROLE_USER");
        setCellValueFactoryOfTable();
        textError.setVisible(false);
        updateTable();
    }


    @FXML
    private void buttonRegistry() {
        textError.setVisible(false);
        if (checkUserProperties()) {
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

                if (!DBQueries.addUserToBase(userAccount)) {
                    System.out.println("user isn't add");
                    textError.setVisible(true);
                    textError.setText("Nie dodano, email lub login jest juz w bazie");
                }
                updateTable();
            }
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
        tableID.setCellValueFactory(new PropertyValueFactory<UserAccount, Number>("ID"));

    }


    private boolean checkUserProperties() {
        if (name.getText().equals("") || surname.getText().equals("")) {
            textError.setVisible(true);
            textError.setText("Podaj imie i nazwisko");
            return false;
        }
        if (!email.getText().matches("[\\w]+@[a-zA-Z0-9]+.[a-z]+")) {
            textError.setVisible(true);
            textError.setText("Zly adres email");
            return false;
        }
        if(login.getText().equals("")){
            textError.setVisible(true);
            textError.setText("Wpisz login");
            return false;
        }
        if (!password.getText().matches(
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_.,])(?=\\S+$).{8,}$")) {
            textError.setVisible(true);
            textError.setText("Blad: haslo bez cyfry i znaku specjalnego");
            return false;
        }
        return true;

    }

}
