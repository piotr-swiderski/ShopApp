package pl.shopApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class JdbcLogin {
    private static Connection connect = null;
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
            connect = DriverManager
                    .getConnection("jdbc:mysql://remotemysql.com:3306/tBBHPYyqTO", DBLogin, password);
            statement = connect.createStatement();
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

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    public static String getDBLogin() {
        return DBLogin;
    }

    public static Statement getStatement() {
        return statement;
    }
}