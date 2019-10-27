package pl.shopApp.controllers;

import javafx.fxml.FXML;
import pl.shopApp.JdbcLogin;
import pl.shopApp.objects.Bill;
import pl.shopApp.objects.Product;
import pl.shopApp.objects.UserAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBQueries {

    static Connection connection = null;
    public static String userLogin = "";


    public DBQueries() {
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
                    userLogin = login;
                    return true;
                }
                if (resultSet.getString("USER_ROLE").equals(role_user)) {
                    userLogin = login;
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
                        resultSet.getDouble("price"),
                        resultSet.getInt("available_quantity")
                ));
            }
            return productData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean bookProduct(Long ID, int quantity) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productData = new ArrayList<>();
        String sql = String.format(
                "UPDATE tBBHPYyqTO.ShopProduct SET available_quantity = available_quantity - ? WHERE ID = ?"
        );
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setLong(2, ID);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean editQuantityProduct(List<Product> book_product) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productData = new ArrayList<>();

        for (Product product : book_product) {
            int quantity_bill = product.getQuantity_bill();
            Long ID = product.getID();

            String sql = String.format(
                    "UPDATE tBBHPYyqTO.ShopProduct SET quantity = quantity - ? WHERE ID = ?"
            );
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, quantity_bill);
                preparedStatement.setLong(2, ID);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean addBill(Bill bill) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = String.format(
                    "INSERT INTO tBBHPYyqTO.Bills(products, cost, worker_name) VALUES(?,?,?)"
            );
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, bill.getProducts());
            preparedStatement.setDouble(2, bill.getCost());
            preparedStatement.setString(3, bill.getWorker_name());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<Bill> getBills() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bill> billData = new ArrayList<>();
        String sql = String.format(
                "SELECT * FROM tBBHPYyqTO.Bills"
        );
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                billData.add(new Bill(
                        resultSet.getLong("ID"),
                        resultSet.getString("data"),
                        resultSet.getString("products"),
                        resultSet.getDouble("cost"),
                        resultSet.getString("worker_name")
                ));
            }
            return billData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addUserToBase(UserAccount user) {
        Statement dbStatement = JdbcLogin.getStatement();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String possibleToAdd = String.format(
                "SELECT email, login FROM tBBHPYyqTO.ShopUser where email = ? or login = ?"
        );
        try {
            preparedStatement = dbStatement.getConnection().prepareStatement(possibleToAdd);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                String addUser = String.format(
                        "insert into tBBHPYyqTO.ShopUser(name,surname,email,login,password,USER_ROLE) values ('%s','%s','%s','%s','%s','%s')",
                        user.getName(), user.getSurname(), user.getEmail(), user.getLogin(), user.getPassword(), user.getUser_role()
                );
                dbStatement.executeUpdate(addUser);

                return true;
            } else {
                System.out.println("Zly login lub email");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean correctBillBase(List<Product> bill) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        for (Product product : bill) {
            String sql = String.format(
                    "UPDATE tBBHPYyqTO.ShopProduct SET available_quantity = available_quantity + ? WHERE ID = ?"
            );
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, product.getQuantity_bill());
                preparedStatement.setLong(2, product.getID());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static UserAccount getUserData() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlQuery = String.format(
                "SELECT * FROM tBBHPYyqTO.ShopUser WHERE login = ?"
        );
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, getUserLogin());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new UserAccount(
                        resultSet.getLong("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        "",
                        resultSet.getString("USER_ROLE")
                );
            }
        } catch (SQLException e) {
            System.out.println("NIE MOZNA ZNALEZC UZYTKOWNIKA");
        }
        return null;
    }

    public static boolean setChangeAccountData(String name, String surname, String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = String.format(
                    "UPDATE tBBHPYyqTO.ShopUser SET name = ?, surname = ?, email = ? WHERE login = ?"
            );
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, getUserLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean updateAccountPassword(String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = String.format(
                    "UPDATE tBBHPYyqTO.ShopUser SET password = ? WHERE login = ?"
            );
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, getUserLogin());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    public static String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }


}
