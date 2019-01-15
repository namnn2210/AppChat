package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.UserModel;
import appchat.app.utility.Hash;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


public class ChangePasswordController {

    public static User currentLoggedIn = AccountInfoController.currentUserLogin;
    @FXML
    private Button change;

    private UserModel userModel = new UserModel();

    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private HashMap<String, String> errors = null;
    private Stage loginStage = null;
    private Parent root;
    private Stage currentStage = null;
    private Stage accountInfoStage;
    private ClientGUIController clientGUIController = new ClientGUIController();

    public Stage getAccountInfoStage() {
        return accountInfoStage;
    }

    public void setAccountInfoStage(Stage accountInfoStage) {
        this.accountInfoStage = accountInfoStage;
    }

    /*
        Hàm thay đổi mật khẩu sau khi ấn submit
        1. String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        -> Nhận các giá trị đã nhập
        2. errors = isValidChangePassword(currentPassword, newPassword, confirmPassword);
        -> Validate các giá trị đã nhập
        3.  if (errors.size() == 0) {
            if ((Hash.generateSaltedSHA1(currentPassword, currentLoggedIn.getSalt())).equals(currentLoggedIn.getPassWord())) {
                if (userModel.changePassword(currentLoggedIn, Hash.generateSaltedSHA1(newPassword, currentLoggedIn.getSalt()))) {
                    changedPasswordAlert();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/accountInfo.fxml"));
                    currentStage = (Stage) change.getScene().getWindow();
                    currentStage.setOnCloseRequest(event -> Platform.exit());
                    currentStage.close();
                } else {
                    matchPasswordAlert();
                }
            }
        } else {
            errorsAlert();
        }
        -> Nếu có lỗi khi nhập sẽ alert lỗi, nếu ok sẽ check với DB xem mật khẩu hiện tại có đúng vs trong DB không, nếu có thì báo thành công và quay về màn hình thông tin tk, nếu không thì báo lỗi
     */
    public void changePassword(ActionEvent actionEvent) {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        errors = isValidChangePassword(currentPassword, newPassword, confirmPassword);
        if (errors.size() == 0) {
            if ((Hash.generateSaltedSHA1(currentPassword, currentLoggedIn.getSalt())).equals(currentLoggedIn.getPassWord())) {
                if (userModel.changePassword(currentLoggedIn, Hash.generateSaltedSHA1(newPassword, currentLoggedIn.getSalt()))) {
                    changedPasswordAlert();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/accountInfo.fxml"));
                    currentStage = (Stage) change.getScene().getWindow();
                    currentStage.setOnCloseRequest(event -> Platform.exit());
                    currentStage.close();
                } else {
                    matchPasswordAlert();
                }
            }
        } else {
            errorsAlert();
        }
    }

    //Hàm đưa ra các lỗi nếu có
    private HashMap<String, String> isValidChangePassword(String currentPass, String newPass, String confirmPass) {
        errors = new HashMap<>();
        if (currentPass.length() == 0 || currentPass.equals("")) {
            errors.put("current", "Current password is empty!");
        } else if (newPass.length() == 0 || newPass.equals("")) {
            errors.put("new", "New password is empty!");
        } else if (confirmPass.length() == 0 || confirmPass.equals("")) {
            errors.put("confirm", "Confirm password is empty!");
        } else if (!(newPass.equals(confirmPass))) {
            errors.put("matchPass", "Password does not match");
        }

        return errors;
    }

    //Các hàm alert là các hàm in ra thông báo
    private void errorsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Change password failed");
        alert.setHeaderText(null);
        alert.setContentText("Please fix following errors and try again");
        for (String message : errors.values()) {
            alert.setContentText(message);
        }
        alert.showAndWait();
    }

    private void changedPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changing password");
        alert.setHeaderText(null);
        alert.setContentText("Password changed ! You have to login again !");
        alert.showAndWait();
    }

    private void matchPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Change password failed");
        alert.setHeaderText(null);
        alert.setContentText("Current password is incorrect");
        alert.showAndWait();
    }
}
