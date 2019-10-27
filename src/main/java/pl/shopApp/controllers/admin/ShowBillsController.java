package pl.shopApp.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.shopApp.controllers.DBQueries;
import pl.shopApp.objects.Bill;

import java.util.List;

public class ShowBillsController {


    List<Bill> billList;
    private double sumCost;

    @FXML
    ComboBox<Long> billID;
    @FXML
    ComboBox<String> billsUser;
    @FXML
    TableView<Bill> tableBills;
    @FXML
    TableColumn<Long, Number> tableBillsID;
    @FXML
    TableColumn<Object, Object> tableBillsData;
    @FXML
    TableColumn<Object, Object> tableBillsText;
    @FXML
    TableColumn<Integer, Number> tableBillsUser;
    @FXML
    TableColumn<Double, Number> tableBillsCost;
    @FXML
    TextField textSumCost;


    @FXML
    private void initialize() {
        billList = DBQueries.getBills();
        for (Bill bill : billList) {
            billID.getItems().add(bill.getID());
            if (!billsUser.getItems().contains(bill.getWorker_name())) {
                billsUser.getItems().add(bill.getWorker_name());
            }
        }
        setCellValueFactoryOfTable();
        tableBillsText.isResizable();
        textSumCost.setEditable(false);
    }


    @FXML
    private void buttonShowUser() {
        tableBills.getItems().clear();
        sumCost = 0;
        for (Bill bill : billList) {
            if (bill.getWorker_name().equals(billsUser.getValue())) {
                tableBills.getItems().add(bill);
                sumCost = sumCost + bill.getCost();
            }
        }
        textSumCost.setText(String.format("%.2f", sumCost));
    }

    @FXML
    private void buttonShowID() {
        tableBills.getItems().clear();
        sumCost = 0;
        for (Bill bill : billList) {
            if (bill.getID().equals(billID.getValue())) {
                tableBills.getItems().add(bill);
                sumCost = sumCost + bill.getCost();
            }
        }
        textSumCost.setText(String.format("%.2f", sumCost));
    }

    @FXML
    private void buttonShowAll() {
        sumCost = 0;
        tableBills.getItems().clear();
        tableBills.getItems().addAll(billList);
        for (Bill bill : billList) {
            sumCost = sumCost + bill.getCost();
        }
        textSumCost.setText(String.format("%.2f", sumCost));
    }

    private void setCellValueFactoryOfTable() {
        tableBillsID.setCellValueFactory(new PropertyValueFactory<Long, Number>("ID"));
        tableBillsText.setCellValueFactory(new PropertyValueFactory<>("products"));
        tableBillsUser.setCellValueFactory(new PropertyValueFactory<Integer, Number>("worker_name"));
        tableBillsData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableBillsCost.setCellValueFactory(new PropertyValueFactory<Double, Number>("cost"));
    }

}
