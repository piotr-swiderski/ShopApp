package pl.shopApp.controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.controllers.LoginController;
import pl.shopApp.controllers.LoginModel;
import pl.shopApp.objects.Bill;
import pl.shopApp.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class UserCreateBillController {

    private List<Product> productList;
    public static List<Product> billList = new ArrayList<>();
    private double cost;




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
    TableColumn<Integer, Number> tableBillQuantity;
    @FXML
    TableColumn<Double, Number> tableBillPrice;
    @FXML
    TextField textError;
    @FXML
    TextField textCost;


    @FXML
    private void initialize() {
        updateProductDB();
        productPrice.setEditable(false);
        for (Product pr : productList) {
            productID.getItems().add(pr.getID());
            productName.getItems().add(pr.getName());
        }
        productPrice.setText(String.valueOf(productList.get(0).getPrice()));
        productID.getSelectionModel().select(0);
        productName.getSelectionModel().select(0);
        textError.setVisible(false);
    }

    private void updateProductDB() {
        productList = LoginModel.getProducts();
        setCellValueFactoryOfTable();
        updateTableProducts();
    }

    public void buttonAddProduct(ActionEvent event) {

        if (productQuantity.getText().equals("")) {
            textError.setVisible(true);
            textError.setText("ERROR: ZLA WARTOSC POLA {ILOSC}");
        } else {
            textError.setVisible(false);
            int index = productID.getSelectionModel().getSelectedIndex();
            if (Integer.valueOf(productQuantity.getText()) <= productList.get(productID.getSelectionModel().getSelectedIndex()).getQuantity_bill()) {
                List<Product> addToBillProduct = new ArrayList<>();
                addToBillProduct.add(new Product(
                        productID.getValue(),
                        productName.getValue(),
                        Integer.parseInt(productQuantity.getText()),
                        Double.parseDouble(productPrice.getText()) * Double.parseDouble(productQuantity.getText()),
                        Integer.parseInt(productQuantity.getText())
                ));
                tableBill.getItems().addAll(addToBillProduct);
                billList.add(addToBillProduct.get(0));
                cost = cost + Double.parseDouble(productPrice.getText()) * Double.parseDouble(productQuantity.getText());
                cost = (int) (cost * 100 + 0.5) / 100.0;
                textCost.setText(String.valueOf(cost));
                LoginModel.bookProduct(productID.getValue(), Integer.parseInt(productQuantity.getText()));
                updateProductDB();
            } else {
                textError.setVisible(true);
                textError.setText("ERROR: BRAK TAKIEJ ILOSCI PRODUKTU");
            }
        }
    }

    public void buttonCreateBill() {
        if (billList.size() > 0) {
            billList.stream().forEach(System.out::println);
            LoginModel.editQuantityProduct(billList);
            LoginModel.addBill(new Bill(null, "", billList.toString(), cost, LoginController.login));
            billList = new ArrayList<>();
            cost = 0;
            textCost.setText(String.valueOf(cost));
            tableBill.getItems().clear();
        }else{
            textError.setVisible(true);
            textError.setText("ERROR: Dodaj produkt do rachunku");
        }
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
        productQuantity.setText("1");
        productPrice.setText(String.valueOf(productList.get(index).getPrice()));
    }

    private void updateTableProducts() {
        tableProducts.getItems().clear();
        tableProducts.getItems().addAll(productList);
    }

    private void setCellValueFactoryOfTable() {
        tableProductsID.setCellValueFactory(new PropertyValueFactory<Long, Number>("ID"));
        tableProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableProductsQuantity.setCellValueFactory(new PropertyValueFactory<Integer, Number>("quantity_bill"));
        tableProductsPrice.setCellValueFactory(new PropertyValueFactory<Double, Number>("price"));

        tableBillID.setCellValueFactory(new PropertyValueFactory<Long, Number>("ID"));
        tableBillName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableBillQuantity.setCellValueFactory(new PropertyValueFactory<Integer, Number>("quantity_bill"));
        tableBillPrice.setCellValueFactory(new PropertyValueFactory<Double, Number>("price"));
    }
}
