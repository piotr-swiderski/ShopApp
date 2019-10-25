package pl.shopApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.shopApp.JdbcLogin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Statement;

public class UserController {

    @FXML
    BorderPane mainPane;

    public void start() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\UserMainScreen.fxml").toURI().toURL());
            Parent root = loader.load();
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.setMinHeight(800);
            primaryStage.setMaxHeight(800);
            primaryStage.setMinWidth(1200);
            primaryStage.setMaxWidth(1200);
            primaryStage.show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonLogOff() {
        try {
            Statement st = JdbcLogin.getStatement();
            st.close();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
            openLoginWindow();
        } catch (SQLException e) {
            System.out.println("Filed to logoff");
        }
    }

    public void buttonProducts() {
        ProductAdd productAdd = new ProductAdd();
        mainPane.setCenter(productAdd);
    }

    public void buttonAddBill() {
        BillAdd billAdd = new BillAdd();
        mainPane.setCenter(billAdd);
    }

    private void openLoginWindow() {
        Stage stage = new Stage();
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\Login.fxml").toURI().toURL());
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Login window wasn't open");
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Login window");
        stage.show();
    }
}
