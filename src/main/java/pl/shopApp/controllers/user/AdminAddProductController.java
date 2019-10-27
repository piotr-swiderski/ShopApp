package pl.shopApp.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.JdbcLogin;
import pl.shopApp.objects.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminAddProductController {

    @FXML
    TextField productName;
    @FXML
    TextField productQuantityAvailable;
    @FXML
    TextField productPrice;
    @FXML
    TableView<Product> tableProduct;
    @FXML
    TableColumn<Product, Number> tableProductID;
    @FXML
    TableColumn<Object, Object> tableProductName;
    @FXML
    TableColumn<Product, Number> tableProductQuantityAvailable;
    @FXML
    TableColumn<Product, Number> tableProductPrice;

    @FXML
    private void initialize() {
        setCellValueFactoryOfTable();
        updateTable();
    }

    @FXML
    private void buttonAddProduct() {
        Product product = new Product(
                null,
                productName.getText(),
                Integer.parseInt(productQuantityAvailable.getText()),
                Double.parseDouble(productPrice.getText()),
                -1
        );

        product.addProductToBase();

        updateTable();
    }

    private void updateTable() {
        List<Product> dataTable = new ArrayList<>();
        try {
            Statement statement = JdbcLogin.getStatement();
            String query = "SELECT * FROM tBBHPYyqTO.ShopProduct";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                dataTable.add(new Product(
                        rs.getLong("ID"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("available_quantity")
                ));
            }
            tableProduct.getItems().clear();
            tableProduct.getItems().addAll(dataTable);
        } catch (SQLException e) {
            System.out.println(e + " User from base isn't download");
        }

    }


    private void setCellValueFactoryOfTable() {
        tableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProductQuantityAvailable.setCellValueFactory(new PropertyValueFactory<Product, Number>("quantity_available"));
        tableProductPrice.setCellValueFactory(new PropertyValueFactory<Product, Number>("price"));
        tableProductID.setCellValueFactory(new PropertyValueFactory<Product, Number>("ID"));


    }

}
