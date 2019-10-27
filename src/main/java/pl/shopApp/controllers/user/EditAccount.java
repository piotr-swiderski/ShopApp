package pl.shopApp.controllers.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class EditAccount extends AnchorPane {

    FXMLLoader fxmlLoader;
    {
        try {
            fxmlLoader = new FXMLLoader(new File("D:\\JavaFX\\ShopApp\\src\\main\\java\\pl\\" +
                    "shopApp\\resources\\UserEditAccount.fxml").toURI().toURL());
            fxmlLoader.setRoot(this);
            //fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
