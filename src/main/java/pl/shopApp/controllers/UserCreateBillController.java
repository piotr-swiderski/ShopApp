package pl.shopApp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class UserCreateBillController {

    private List<Product> productList;


    @FXML
    ComboBox<Long> productID;
    @FXML
    ComboBox<String> productName;
    @FXML
    TextField productQuantity;
    @FXML
    TextField productPrice;
    @FXML
    TableView<Product> tableProducts;
    @FXML
    TableColumn<Long, Number> tableProductsID;
    @FXML
    TableColumn<Object, Object> tableProductsName;
    @FXML
    TableColumn<Integer, Number> tableProductsQuantity;
    @FXML
    TableColumn<Double, Number> tableProductsPrice;
    @FXML
    TableView<Product> tableBill;
    @FXML
    TableColumn<Long, Number> tableBillID;
    @FXML
    TableColumn<Object, Object> tableBillName;
    @FXML
    TableColumn tableBillQuantity;
    @FXML
    TableColumn tableBillPrice;



    @FXML
    private void initialize() {
        productList = LoginModel.getProducts();
        setCellValueFactoryOfTable();
        updateTableProducts();
        //productQuantity.setEditable(false);
        productPrice.setEditable(false);
        for (Product pr : productList) {
            productID.getItems().add(pr.getID());
            productName.getItems().add(pr.getName());
            //productQuantity.setText(String.valueOf(pr.getQuantity_available()));
            productPrice.setText(String.valueOf(pr.getPrice()));
        }
        productID.getSelectionModel().select(0);
        productName.getSelectionModel().select(0);
    }

    public void buttonAddProduct(ActionEvent event) {
        int index = productID.getSelectionModel().getSelectedIndex();
        List<Product> addToBillProduct = new ArrayList<>();
        addToBillProduct.add(new Product(
                productID.getValue(),
                productName.getValue(),
                Integer.parseInt(productQuantity.getText()),
                Double.parseDouble(productPrice.getText())
        ));
        tableBill.getItems().addAll(addToBillProduct);



    }

    public void actionSelect(ActionEvent event) {
        int index;
        if (event.getSource().equals(productID)) {
            index = productID.getSelectionModel().getSelectedIndex();
            productName.getSelectionModel().select(index);
        } else {
            index = productName.getSelectionModel().getSelectedIndex();
            productID.getSelectionModel().select(index);
        }
        productQuantity.setText(String.valueOf(productList.get(index).getQuantity_available()));
        productPrice.setText(String.valueOf(productList.get(index).getPrice()));
    }


    private void updateTableProducts() {
        tableProducts.getItems().clear();
        tableProducts.getItems().addAll(productList);
    }


    private void setCellValueFactoryOfTable() {
        tableProductsID.setCellValueFactory(new PropertyValueFactory<Long, Number>("ID"));
        tableProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProductsQuantity.setCellValueFactory(new PropertyValueFactory<Integer, Number>("quantity_available"));
        tableProductsPrice.setCellValueFactory(new PropertyValueFactory<Double, Number>("price"));

        tableBillID.setCellValueFactory(new PropertyValueFactory<Long, Number>("ID"));
        tableBillName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableBillQuantity.setCellValueFactory(new PropertyValueFactory<Integer, Number>("quantity_available"));
        tableBillPrice.setCellValueFactory(new PropertyValueFactory<Double, Number>("price"));
    }

}
