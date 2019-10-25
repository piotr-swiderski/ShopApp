package pl.shopApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcLogin {
    private static Connection connection = null;
    private static Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    private static String DBLogin;

    public static Statement logToDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setup the connection with the DB
            DBLogin = "tBBHPYyqTO";
            String password = "ZBuzFC5SDN";
            connection = DriverManager
                    .getConnection("jdbc:mysql://remotemysql.com:3306/tBBHPYyqTO", DBLogin, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Not find database driver");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No connect to database");
        }
        System.out.println("login to database");
        return statement;
    }

    public static Connection getConnection() {
        return connection;
    }


    public static String getDBLogin() {
        return DBLogin;
    }

    public static Statement getStatement() {
        return statement;
    }
}