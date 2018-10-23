package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.UserModel;
import appchat.app.utility.Hash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

public class LoginController {

    Stage stage;
    Parent root;

    @FXML
    private Hyperlink register;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private UserModel userModel = new UserModel();
    private User user = null;
    private HashMap<String, String> errors = null;

    private User currentLoginUser = null;

    public void login(ActionEvent actionEvent) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        user = new User(username, password);
        errors = checkLogin();
        if (errors.size() > 0 ) {
            checkLoginAlert();
        }
        else {
            user = userModel.select(username);
            if (user == null) {
                usernameAlert();
            }
            else {
                if (!(user.getPassWord().equals(Hash.generateSaltedSHA1(password,user.getSalt())))) {
                    passwordAlert();
                }
                else {
                    loginAlert();
                    currentLoginUser = user;
                    showClientGUI();
                }
            }
        }
    }

    public User getCurrentLoginUserToGUI() {
        return currentLoginUser;
    }

    public HashMap<String, String> checkLogin() {
        errors = new HashMap<>();
        if (usernameField.getText().length() == 0 || usernameField.getText() == null) {
            errors.put("username", "Username can't be null or empty");
        } else if ((usernameField.getText().length() < 5)) {
            errors.put("username", "Username is too short");
        } else if (passwordField.getText().length() == 0 || passwordField.getText() == null) {
            errors.put("password", "Password can't be null or empty");
        } else if (passwordField.getText().length() < 8) {
            errors.put("password", "Password must be more than 8 characters");

        }
        return errors;
    }

    private void checkLoginAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Please fix following errors and try again");
        for (String message : errors.values()) {
            alert.setContentText(message);
        }
        alert.showAndWait();
    }

    private void loginAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logging in");
        alert.setHeaderText("Results:");
        alert.setContentText("Login successful !");

        alert.showAndWait();
    }

    private void usernameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Username doesn't exist");
        alert.showAndWait();
    }

    private void passwordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Password is incorrect");
        alert.showAndWait();
    }

    public void handleRegisterAction(ActionEvent actionEvent) throws Exception {

        if (actionEvent.getSource() == register) {
            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        }
        stage.setScene(new Scene(root, 550, 800));
        stage.setResizable(false);
        stage.show();
    }

    public void showClientGUI() throws Exception {
        stage = (Stage) register.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
        stage.setTitle("Messages");
        stage.setScene(new Scene(root, 1000, 800));
        stage.setResizable(false);
        stage.show();
    }
}
