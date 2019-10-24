package pl.shopApp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Controller {


    @FXML
    BorderPane mainPane;

    @FXML
    public void initialize() {
    }

    @FXML
    private void buttonLogin(){
        openLoginWindow();
    }

    @FXML
    private void buttonItems(){
        CashierAdd cashierAdd = new CashierAdd();
        mainPane.setCenter(cashierAdd);
    }


    private void openLoginWindow() {
        Stage stage = new Stage();
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\Login.fxml").toURI().toURL());
            root = loader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Login window wasn't open");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Login window wasn't open");
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Login window");
        stage.show();
    }

}
