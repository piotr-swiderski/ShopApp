package pl.shopApp.objects;

import javax.xml.crypto.Data;
import java.util.List;

public class Bill {

    private Long ID;
    private String products;
    private double cost;
    private String data;
    private String worker_name;

    public Bill(Long ID, String data, String products, double cost, String worker_name) {
        this.ID = ID;
        this.products = products;
        this.cost = cost;
        this.data = data;
        this.worker_name = worker_name;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    @Override
    public String toString() {
        return "ID=" + ID +
                ", products='" + products +
                ", cost=" + cost +
                ", data='" + data +
                ", worker_name='" + worker_name +
                '}';
    }
}
