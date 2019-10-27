package pl.shopApp.controllers.user;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.shopApp.controllers.LoginModel;
import pl.shopApp.objects.UserAccount;

public class UserEditAccountController {

    private UserAccount userData;

    @FXML
    TextField accountCurrentName;
    @FXML
    TextField accountCurrentSurname;
    @FXML
    TextField accountCurrentEmail;
    @FXML
    TextField accountCurrentLogin;
    @FXML
    TextField accountCurrentRole;
    @FXML
    TextField changeName;
    @FXML
    TextField changeSurname;
    @FXML
    TextField changeEmail;
    @FXML
    TextField textError;
    @FXML
    TextField password;
    @FXML
    TextField confirmPassword;


    @FXML
    private void initialize() {
        takeAccountData();
        textError.setVisible(false);
    }

    @FXML
    private boolean acceptChanges() {
        String editName;
        String editSurname;
        String editEmail;
        textError.setVisible(false);

        if (changeName.getText().equals("")) {
            editName = userData.getName();
        } else {
            editName = changeName.getText();
        }
        if (changeSurname.getText().equals("")) {
            editSurname = userData.getSurname();
        } else {
            editSurname = changeSurname.getText();
        }
        if (changeEmail.getText().equals("")) {
            editEmail = userData.getEmail();
        } else if (changeEmail.getText().matches("[\\w]+@[a-zA-Z0-9]+.[a-z]+")) {
            editEmail = changeEmail.getText();
        } else {
            textError.setVisible(true);
            textError.setText("Zly adres email");
            changeEmail.setPromptText("przyklad: user@poczta.pl");
            return false;
        }

        LoginModel.setChangeAccountData(editName, editSurname, editEmail);
        takeAccountData();
        return true;

    }

    @FXML
    private void acceptChangePassword() {
        textError.setVisible(false);
        if (!confirmPassword.getText().equals(password.getText())) {
            textError.setVisible(true);
            textError.setText("Hasla sa rozne");
        } else {
            if (password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_.,])(?=\\S+$).{8,}$")) {
                LoginModel.updateAccountPassword(password.getText());
                textError.setVisible(true);
                textError.setText("Haslo zostalo edytowane");
                confirmPassword.setText("");
            } else {
                textError.setVisible(true);
                textError.setText("Haslo musi zawierac duza litere, liczbe i znak");
            }
        }


    }


    private void takeAccountData() {
        userData = LoginModel.getUserData();
        accountCurrentName.setEditable(false);
        accountCurrentSurname.setEditable(false);
        accountCurrentEmail.setEditable(false);
        accountCurrentLogin.setEditable(false);
        accountCurrentRole.setEditable(false);
        accountCurrentName.setText(userData.getName());
        accountCurrentSurname.setText(userData.getSurname());
        accountCurrentEmail.setText(userData.getEmail());
        accountCurrentLogin.setText(userData.getLogin());
        accountCurrentRole.setText(userData.getUser_role());
    }


}
