package pl.shopApp.objects;

import pl.shopApp.JdbcLogin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {

    private Long ID;
    private String name;
    private int quantity_available;
    private double price;
    private int quantity_bill;

    public Product(Long ID, String name, int quantity_available, double price, int quantity_bill) {
        this.name = name;
        this.quantity_available = quantity_available;
        this.price = price;
        this.ID = ID;
        this.quantity_bill = quantity_bill;
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

    public Long getID() {
        return ID;
    }

    public int getQuantity_bill() {
        return quantity_bill;
    }

    public void setQuantity_bill(int quantity_bill) {
        this.quantity_bill = quantity_bill;
    }

    public boolean addProductToBase() {
        Statement dbStatement = JdbcLogin.getStatement();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String presentProduct = String.format(
                    "SELECT name FROM tBBHPYyqTO.ShopProduct where name = ? "
            );

            preparedStatement = dbStatement.getConnection().prepareStatement(presentProduct);

            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String updateProduct = String.format(
                        "UPDATE tBBHPYyqTO.ShopProduct SET quantity = quantity + ?, " +
                                "available_quantity = available_quantity + ?, " +
                                "price = ? " +
                                "WHERE name = ?"
                );
                preparedStatement = dbStatement.getConnection().prepareStatement(updateProduct);
                preparedStatement.setInt(1, quantity_available);
                preparedStatement.setInt(2, quantity_available);
                preparedStatement.setDouble(3, price);
                preparedStatement.setString(4, name);
                preparedStatement.execute();
                return true;
            } else {
                String addProduct = String.format(
                        "insert into tBBHPYyqTO.ShopProduct(name,quantity,price) values ('%s','%d','" + price + "')",
                        name, quantity_available
                );
                dbStatement.executeUpdate(addProduct);
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e + "Product not upadate");
            return false;
        }

    }


    @Override
    public String toString() {
        return String.format("ID=%s %s x %d = %.2f\n", ID, name, quantity_available, price);
    }
}
