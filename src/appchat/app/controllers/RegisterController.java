package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.UserModel;
import appchat.app.utility.Hash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


public class RegisterController {

    private UserModel userModel = new UserModel();

    Stage stage;
    Parent root;

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
    @FXML
    private Button register;
    @FXML
    private Hyperlink login;

    private User user = null;
    private HashMap<String, String> errors = null;

    /* Hàm đăng kí
        1. String username = userNameField.getText();
        String password = passwordField.getText();
        String salt = Hash.randomString(7);
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
        -> Nhận các giá trị từ ô đăng kí
        2. user = new User(username, Hash.generateSaltedSHA1(password, salt), salt, fullName, birthDate, gender, address, email, phone) -> tạo đối tượng user với password mã hoá
        3. errors = isValid();
        if (errors.size() == 0) {
            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                if (userModel.checkExistEmail(user.getEmail())) {
                    if(userModel.checkExistUser(user.getUserName())) {
                        if (userModel.insert(user)) {
                            signUpSuccessfulAlert();
                            try {
                                if (actionEvent.getSource() == register) {
                                    stage = (Stage) register.getScene().getWindow();
                                    root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                                }
                                stage.setScene(new Scene(root, 550, 700));
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            signUpFailedAlert();
                        }
                    }
                    else {
                        checkExistUserAlert();
                    }
                }
                else {
                    checkExistEmailAlert();
                }
            }
            else {
                checkPasswordAlert();
            }
        } else {
            errorsAlert();
        }
        -> hàm isValid : check các lỗi khi nhập dữ liệu
        -> check các lỗi : lỗi trùng password, lỗi đã có tên tài khoản với DB -> in ra alert
        -> nếu không có lỗi thì gọi userModel.insert(user) để đưa đối tượng user vào DB và báo ra đăng kí thành công và chuyển sang màn hình login

     */
    public void register(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        String salt = Hash.randomString(7);
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

        user = new User(username, Hash.generateSaltedSHA1(password, salt), salt, fullName, birthDate, gender, address, email, phone);
        errors = isValid();
        if (errors.size() == 0) {
            if (passwordField.getText().equals(confirmPasswordField.getText())) {
                if (userModel.checkExistEmail(user.getEmail())) {
                    if(userModel.checkExistUser(user.getUserName())) {
                        if (userModel.insert(user)) {
                            signUpSuccessfulAlert();
                            try {
                                if (actionEvent.getSource() == register) {
                                    stage = (Stage) register.getScene().getWindow();
                                    root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
                                }
                                stage.setScene(new Scene(root, 550, 700));
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            signUpFailedAlert();
                        }
                    }
                    else {
                        checkExistUserAlert();
                    }
                }
                else {
                    checkExistEmailAlert();
                }
            }
            else {
                checkPasswordAlert();
            }
        } else {
            errorsAlert();
        }


    }

    //Hàm đưa ra các lỗi nếu có
    public HashMap<String, String> isValid() {
        HashMap<String, String> errors = new HashMap<>();
        if (userNameField.getText().length() == 0 || userNameField.getText() == null) {
            errors.put("username", "Username can't be null or empty");
        } else if ((userNameField.getText().length() < 5)) {
            errors.put("username", "Username is too short");
        } else if (passwordField.getText().length() == 0 || passwordField.getText() == null) {
            errors.put("password", "Password can't be null or empty");
        } else if (passwordField.getText().length() < 8) {
            errors.put("password", "Password must be more than 8 characters");
        }  else if (fullNameField.getText().length() == 0 || fullNameField.getText() == null) {
            errors.put("fullName", "Your name can't be null or empty");
        } else if (date.getEditor().getText().length() == 0 || date.getEditor().getText().equals("")) {
            errors.put("date", "Please choose your date of birth");
        } else if (addressField.getText().length() == 0 || addressField.getText() == null) {
            errors.put("address", "Address can't be null or empty");
        } else if (phoneField.getText().length() == 0 || phoneField.getText() == null) {
            errors.put("phone", "Phone can't be null or empty");
        } else if (emailField.getText().matches("\t^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            errors.put("email", "Email is not in correct format");
        } else if (emailField.getText().length() == 0 || emailField.getText() == null) {
            errors.put("email", "Email can't be null or empty");
        } else if (!(male.isSelected() || female.isSelected())) {
            errors.put("gender", "Please choose your gender");
        }
        return errors;
    }

    //Các hàm alert là các hàm in ra thông báo
    private void signUpFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Signup Failed");
        alert.setHeaderText(null);
        alert.setContentText("Failed");

        alert.showAndWait();
    }

    private void errorsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Signup Failed");
        alert.setHeaderText(null);
        alert.setContentText("Please fix following errors and try again");
        for (String message : errors.values()) {
            alert.setContentText(message);
        }
        alert.showAndWait();
    }

    private void checkExistUserAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Username existed");
        alert.setHeaderText(null);
        alert.setContentText("Username is existed. Please try another username");
        alert.showAndWait();
    }

    private void checkExistEmailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Email existed");
        alert.setHeaderText(null);
        alert.setContentText("Email is existed. Please try another email");
        alert.showAndWait();
    }

    private void checkPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password");
        alert.setHeaderText(null);
        alert.setContentText("Password does not match");
        alert.showAndWait();
    }

    private void signUpSuccessfulAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Signup Successfully");

        alert.setHeaderText(null);
        alert.setContentText("New account created ! Please login");

        alert.showAndWait();
    }


    // Hàm chuyển sang màn hình đăng nhập nếu đã có tài khoản
    public void handleLoginAction(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource() == login) {
            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        }
        stage.setScene(new Scene(root, 550, 700));
        stage.setResizable(false);
        stage.show();
    }
}
