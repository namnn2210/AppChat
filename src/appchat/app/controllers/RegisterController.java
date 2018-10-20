package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.DBConnection;
import appchat.app.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class RegisterController {

    private UserModel userModel = new UserModel();

    @FXML
    private TextField userNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker date;

    private User user = null;

    public void register(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String fullName = fullNameField.getText();
        String birthDate = ((TextField) date.getEditor()).getText();
        int gender;
        if (male.isSelected()) {
            gender = 1;
        } else if (female.isSelected()) {
            gender = 2;
        } else {
            gender = 0;
        }
        String address = addressField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        user = new User(username, password, confirmPassword, fullName, birthDate, gender, address, email, phone);
        if (userModel.insert(user)) {
            signUpSuccessfulAlert();
        } else {

        }
    }

    private void signUpSuccessfulAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Signup Successfully");

        alert.setHeaderText(null);
        alert.setContentText("New account created ! Please login");

        alert.showAndWait();
    }


}
