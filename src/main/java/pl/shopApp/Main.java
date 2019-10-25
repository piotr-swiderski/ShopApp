package pl.shopApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JD;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main extends Application {

    private JdbcLogin jdbcLogin;

    @Override
    public void start(Stage stage) throws Exception{
       /* FXMLLoader loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                "shopApp\\resources\\mainScreen.fxml").toURI().toURL());
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(800);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(1200);
        primaryStage.setMaxWidth(1200);
        primaryStage.show();*/

        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\Login.fxml").toURI().toURL());
            root = loader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Login window wasn't open");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Login window wasn't open");
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Login window");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
