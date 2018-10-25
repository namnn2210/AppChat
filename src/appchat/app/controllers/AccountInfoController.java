package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.UserModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountInfoController {
    public static User currentUserLogin;

    private UserModel userModel = new UserModel();
    private RegisterController registerController = new RegisterController();

    private Stage primaryStage;
    private Stage stage;
    private Parent root;

    private StringProperty username;
    private StringProperty password;
    private StringProperty fullName;
    private StringProperty email;
    private StringProperty gender;
    private StringProperty dob;
    private StringProperty phone;

    @FXML
    private TextField updateName;
    @FXML
    private TextField updateEmail;
    @FXML
    private TextField updateAddress;
    @FXML
    private DatePicker updateDate;
    @FXML
    private RadioButton updateMaleGender;
    @FXML
    private RadioButton updateFemaleGender;


    @FXML
    private ImageView backToChat;
    @FXML
    private Button logout;



    public AccountInfoController() {
        username = new SimpleStringProperty(currentUserLogin.getUserName());
        password = new SimpleStringProperty(currentUserLogin.getPassWord());
        fullName = new SimpleStringProperty(currentUserLogin.getFullName());
        email = new SimpleStringProperty(currentUserLogin.getEmail());
        dob = new SimpleStringProperty(currentUserLogin.getBirthDate());
        phone = new SimpleStringProperty(currentUserLogin.getPhone());
        if (currentUserLogin.getGender() == 1) {
            gender = new SimpleStringProperty("Male");
        } else if (currentUserLogin.getGender() == 2) {
            gender = new SimpleStringProperty("Female");
        }
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDob() {
        return dob.get();
    }

    public StringProperty dobProperty() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void backToChat(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() == backToChat) {
            primaryStage = (Stage) backToChat.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
            primaryStage.setTitle("Messages");
            primaryStage.setScene(new Scene(root, 1000, 800));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void updateInfo(ActionEvent actionEvent) {
//        String fullname = fullnameField.getText();
//        String email = emailField.getText();
//        String phone = phoneField.getText();
//        if(userModel.update(currentUserLogin,fullname,email,phone)) {
//            updatedAlert();
//        }
//        else {
//            updateFailedAlert();
//        }
    }

    private void updatedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Updating ");
        alert.setHeaderText(null);
        alert.setContentText("Update successful !");
        alert.showAndWait();
    }

    private void updateFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Updating ");
        alert.setHeaderText(null);
        alert.setContentText("Update failed !");
        alert.showAndWait();
    }

    public void changePasswordBtn(ActionEvent actionEvent) throws Exception {
        Stage secondStage = new Stage();
        Parent secondScene = FXMLLoader.load(getClass().getResource("/fxml/changePassword.fxml"));
        secondStage.initOwner(primaryStage);
        secondStage.initModality(Modality.WINDOW_MODAL);
        secondStage.setTitle("Change password");
        secondStage.setScene(new Scene(secondScene, 600, 400));
        secondStage.setResizable(false);
        secondStage.show();
    }

    private void logoutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logging out ");
        alert.setHeaderText(null);
        alert.setContentText("Logout successfully !");
        alert.showAndWait();
    }

    public void logout(ActionEvent actionEvent) {
        currentUserLogin = null;
        logoutAlert();
        try {
            if (actionEvent.getSource() == logout) {
                stage = (Stage) logout.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            }
            stage.setScene(new Scene(root, 550, 700));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void toUpdateForm(ActionEvent actionEvent) throws Exception {
        Stage updateStage = new Stage();
        Parent updateScene = FXMLLoader.load(getClass().getResource("/fxml/updateInfo.fxml"));
        updateStage.initOwner(primaryStage);
        updateStage.initModality(Modality.WINDOW_MODAL);
        updateStage.setTitle("Update account information");
        updateStage.setScene(new Scene(updateScene, 800, 600));
        updateStage.setResizable(false);
        updateStage.show();
    }
}
