package pl.shopApp.controllers;

import pl.shopApp.JdbcLogin;
import pl.shopApp.objects.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginModel {

    static Connection connection = null;


    public LoginModel() {
        JdbcLogin.logToDataBase();
        connection = JdbcLogin.getConnection();

    }

    public boolean isDataBaseConnect() {
        return connection != null;
    }

    public boolean isLogin(String login, String password, String role_user) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = String.format(
                "SELECT login, password, USER_ROLE FROM tBBHPYyqTO.ShopUser WHERE login = ? and password = ?"
        );
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("USER_ROLE").equals("ROLE_ADMIN")) {
                    return true;
                }
                if (resultSet.getString("USER_ROLE").equals(role_user)) {
                    return true;
                }
                return false;
            } else {
                System.out.println("Login or password is wrong. Or check your role");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e + " login or password is wrong");
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static List<Product> getProducts() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productData = new ArrayList<>();
        String sql = String.format(
                "SELECT * FROM tBBHPYyqTO.ShopProduct"
        );
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productData.add(new Product(
                        resultSet.getLong("ID"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                ));
            }
            return productData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
