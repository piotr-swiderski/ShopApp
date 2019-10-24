package pl.shopApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JD;

import java.io.File;

public class Main extends Application {

    private JdbcLogin jdbcLogin;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                "shopApp\\resources\\mainScreen.fxml").toURI().toURL());
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(800);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(1200);
        primaryStage.setMaxWidth(1200);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
