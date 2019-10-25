package pl.shopApp.objects;

import pl.shopApp.JdbcLogin;

import java.sql.SQLException;
import java.sql.Statement;

public class Product {

    private Long ID;
    private String name;
    private int quantity_available;
    private double price;

    public Product(Long ID, String name, int quantity_available, double price) {
        this.name = name;
        this.quantity_available = quantity_available;
        this.price = price;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getID(){
        return ID;
    }

    public boolean addProductToBase() throws SQLException {
        Statement dbStatement = JdbcLogin.getStatement();
        String addProduct = String.format(
                "insert into tBBHPYyqTO.ShopProduct(name,quantity,price) values ('%s','%d','" + price + "')",
                name, quantity_available
        );
        dbStatement.executeUpdate(addProduct);
        return true;
    }


}
