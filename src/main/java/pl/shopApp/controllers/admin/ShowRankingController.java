package pl.shopApp.controllers.admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.shopApp.controllers.LoginModel;
import pl.shopApp.objects.Bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowRankingController {


    List<Bill> billList;
    private double sumCost;

    @FXML
    ComboBox<Long> billID;
    @FXML
    ComboBox<String> billsUser;
    @FXML
    BarChart<String, Number> chartRanking;

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";


    @FXML
    private void initialize() {
        billList = LoginModel.getBills();

    }


    @FXML
    private void buttonShowUser() {
    }

    @FXML
    private void buttonShowID() {

    }

    @FXML
    private void buttonShowAll() {
        updateChart(billList);
    }


    public void updateChart(List<Bill> bills) {

        chartRanking.setVisible(false);
        chartRanking.getData().clear();
        chartRanking.setVisible(true);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        chartRanking.setCursor(null);
        chartRanking.setTitle("Ranking użytkownikow");
        xAxis.setLabel("Uzytkownik");
        yAxis.setLabel("Utarg");
        List<XYChart.Series> seriesList = new ArrayList<>();
        Map<String, Double> mapBills = new HashMap<>();

        for (Bill bill : bills) {
            if (mapBills.containsKey(bill.getWorker_name())) {
                String worker = bill.getWorker_name();
                System.out.println(worker);
                mapBills.put(worker, mapBills.get(worker) + bill.getCost());
            } else {
                mapBills.put(bill.getWorker_name(), bill.getCost());
            }
        }


        for (Map.Entry<String, Double> entry : mapBills.entrySet()) {
            XYChart.Series series = new XYChart.Series();
            series.setName(entry.getKey());
            series.getData().add(new XYChart.Data("", entry.getValue()));
            chartRanking.getData().add(series);
        }
        //bc.getData().addAll(seriesList);

    }


}
