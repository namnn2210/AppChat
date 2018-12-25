package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.UserModel;
import appchat.app.utility.Hash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.util.HashMap;

//Controller cho changePassword.fxml
public class ChangePasswordController {

    public static User currentLoggedIn = AccountInfoController.currentUserLogin;
    @FXML
    private Button change;

    private UserModel userModel = new UserModel();

    //Lấy các ô bên FXML
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private HashMap<String, String> errors = null;


    //Hàm đổi mật khẩu
    public void changePassword(ActionEvent actionEvent) {
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        errors = isValidChangePassword(currentPassword, newPassword, confirmPassword);
        if (errors.size() == 0) {
            if ((Hash.generateSaltedSHA1(currentPassword,currentLoggedIn.getSalt())).equals(currentLoggedIn.getPassWord())) {
                if (userModel.changePassword(currentLoggedIn, Hash.generateSaltedSHA1(newPassword, currentLoggedIn.getSalt()))) {
                    changedPasswordAlert();
                } else {
                    matchPasswordAlert();
                }
            }
        } else {
            errorsAlert();
        }
    }

    // Validate thông tin
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

    //Báo lỗi
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

    //Báo lỗi đổi password thành công
    private void changedPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changing password");
        alert.setHeaderText(null);
        alert.setContentText("Password changed !");
        alert.showAndWait();
    }

    //Báo lỗi pass hiện tại sai

    private void matchPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Change password failed");
        alert.setHeaderText(null);
        alert.setContentText("Current password is incorrect");
        alert.showAndWait();
    }
}
