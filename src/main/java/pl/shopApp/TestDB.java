package pl.shopApp;

public class TestDB {
    public static void main(String[] args) {
      JdbcLogin jdbcLogin = new JdbcLogin();
        try {
            jdbcLogin.getDBLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
