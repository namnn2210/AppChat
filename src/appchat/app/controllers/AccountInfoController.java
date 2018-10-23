package appchat.app.controllers;

import appchat.app.entity.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccountInfoController {
    public static User currentUserLogin;

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
    private ImageView backToChat;

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

    public void backToChat(MouseEvent mouseEvent) throws Exception{
        if (mouseEvent.getSource() == backToChat) {
            stage = (Stage) backToChat.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
            stage.setTitle("Messages");
            stage.setScene(new Scene(root, 1000, 800));
            stage.setResizable(false);
            stage.show();
        }
    }
}
