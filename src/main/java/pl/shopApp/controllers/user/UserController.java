package pl.shopApp.controllers.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.shopApp.JdbcLogin;
import pl.shopApp.controllers.LoginModel;
import pl.shopApp.controllers.ProductAdd;
import pl.shopApp.controllers.user.BillAdd;
import pl.shopApp.objects.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserController {

    @FXML
    BorderPane mainPane;
    @FXML
    TextField textLogin;

    @FXML
    private void initialize(){
        textLogin.setEditable(false);
        textLogin.setText(String.format("Zalogowany: %s",LoginModel.getUserLogin()));
    }

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
        billNotSend();
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
        billNotSend();
    }

    public void buttonAddBill() {
        BillAdd billAdd = new BillAdd();
        mainPane.setCenter(billAdd);
        billNotSend();
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

    private void billNotSend() {
        if (UserCreateBillController.billList.size() != 0) {
            List<Product> billList = UserCreateBillController.billList;
            LoginModel.correctBillBase(billList);
        }
    }

    @FXML
    private void buttonEditAccount(){
        EditAccount editAccount = new EditAccount();
        mainPane.setCenter(editAccount);
        billNotSend();
    }


}
