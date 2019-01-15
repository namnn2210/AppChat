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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.HashMap;

public class LoginController {

    private Stage stage;
    private Parent root;

    @FXML
    private Hyperlink register;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button login;

    private UserModel userModel = new UserModel();
    private User user = null;
    private HashMap<String, String> errors = null;

    /* Hàm login nhận nút enter sau khi nhập username + password
        1. keyEvent.getCode() == KeyCode.ENTER -> nhận nút enter
        2. String username = usernameField.getText();
            String password = passwordField.getText(); -> nhận giá trị từ 2 ô nhập
        3.
        errors = checkLogin();
        if (errors.size() > 0) {
                checkLoginAlert();
            } else {
                user = userModel.select(username);
                if (user == null) {
                    usernameAlert();
                } else {
                    if (!(user.getPassWord().equals(Hash.generateSaltedSHA1(password, user.getSalt())))) {
                        passwordAlert();
                    } else {
                        loginAlert();
                        ClientGUIController.currentUserLogin = user;
                        AccountInfoController.currentUserLogin = user;
                        showClientGUI();
                    }
                }
            }
          -> gọi hàm checklogin() xem có lỗi đăng nhập gì ko nếu có gọi hàm checkLoginAlert() để báo có lỗi, không thì gọi user = userModel.select(username) để lấy từ db ra,
          thành công thì hiện hàm loginAlert(), và showClientGUI() để hiện ra màn hình chat
     */
    public void loginEnter(KeyEvent keyEvent) throws Exception{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            user = new User(username, password);
            errors = checkLogin();
            if (errors.size() > 0) {
                checkLoginAlert();
            } else {
                user = userModel.select(username);
                if (user == null) {
                    usernameAlert();
                } else {
                    if (!(user.getPassWord().equals(Hash.generateSaltedSHA1(password, user.getSalt())))) {
                        passwordAlert();
                    } else {
                        loginAlert();
                        ClientGUIController.currentUserLogin = user;
                        AccountInfoController.currentUserLogin = user;
                        showClientGUI();
                    }
                }
            }
        }
    }

    /* Hàm login nhận nút login sau khi nhập username + password
        1. actionEvent.getSource() == login -> nhận nút click login
        2. String username = usernameField.getText();
            String password = passwordField.getText(); -> nhận giá trị từ 2 ô nhập
        3.
        errors = checkLogin();
        if (errors.size() > 0) {
                checkLoginAlert();
            } else {
                user = userModel.select(username);
                if (user == null) {
                    usernameAlert();
                } else {
                    if (!(user.getPassWord().equals(Hash.generateSaltedSHA1(password, user.getSalt())))) {
                        passwordAlert();
                    } else {
                        loginAlert();
                        ClientGUIController.currentUserLogin = user;
                        AccountInfoController.currentUserLogin = user;
                        showClientGUI();
                    }
                }
            }
          -> gọi hàm checklogin() xem có lỗi đăng nhập gì ko nếu có gọi hàm checkLoginAlert() để báo có lỗi, không thì gọi user = userModel.select(username) để lấy từ db ra,
          thành công thì hiện hàm loginAlert(), và showClientGUI() để hiện ra màn hình chat
     */
    public void login(ActionEvent actionEvent) throws Exception {
        if(actionEvent.getSource() == login) {
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
                        ClientGUIController.currentUserLogin = user;
                        AccountInfoController.currentUserLogin = user;
                        showClientGUI();
                    }
                }
            }
        }
    }



    //Hàm đưa ra các lỗi nếu có
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

    //Các hàm alert là các hàm in ra thông báo
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

    // Hàm chuyển sang màn hình đăng kí nếu chưa có tài khoản
    public void handleRegisterAction(ActionEvent actionEvent) throws Exception {

        if (actionEvent.getSource() == register) {
            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        }
        stage.setScene(new Scene(root, 550, 800));
        stage.setResizable(false);
        stage.show();
    }

    // Hàm chuyển sang màn hình chat
    public void showClientGUI() throws Exception {
        stage = (Stage) register.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
        stage.setTitle("Messages");
        stage.setScene(new Scene(root, 1000, 800));
        stage.setResizable(false);
        stage.show();
    }
}
