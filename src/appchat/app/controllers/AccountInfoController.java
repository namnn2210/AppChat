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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AccountInfoController implements Initializable {
    public static User currentUserLogin;

    private UserModel userModel = new UserModel();
    private RegisterController registerController = new RegisterController();

    private Stage primaryStage;
    private Stage stage;
    private Parent root;
    private Parent updateScene;
    private Stage updateStage;
    private Stage loginStage = null;

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
    private TextField updatePhone;
    @FXML
    private DatePicker updateDate;
    @FXML
    private RadioButton updateMaleGender;
    @FXML
    private RadioButton updateFemaleGender;

    private HashMap<String, String> errors = null;


    @FXML
    private ImageView backToChat;
    @FXML
    private Button logout;

    // Khi màn hình thông tin tài khoản được bật lên, đối tượng user đc tạo ra và lấy các thông tin theo thông tin của người user đang đăng nhập
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

    //Hàm ấn nút quay lại màn hình chat
    public void backToChat(MouseEvent mouseEvent) throws Exception {
        primaryStage = (Stage) backToChat.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
        primaryStage.setTitle("Messages");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Hàm đưa ra các lỗi nếu có
    private HashMap<String, String> isValidUpdateInfo() {
        errors = new HashMap<>();
        if (updateName.getText().length() == 0 || updateName.getText().equals("")) {
            errors.put("name", "New full name is empty!");
        } else if (updateEmail.getText().length() == 0 || updateEmail.getText().equals("")) {
            errors.put("email", "New email is empty!");
        } else if (updateAddress.getText().length() == 0 || updateAddress.getText().equals("")) {
            errors.put("address", "New address is empty!");
        } else if (updateDate.getEditor().getText().length() == 0 || updateDate.getEditor().getText().equals("")) {
            errors.put("date", "New date of birth is empty!");
        } else if (!(updateMaleGender.isSelected() || updateFemaleGender.isSelected())) {
            errors.put("gender", "Please choose your gender");
        } else if (updatePhone.getText().length() == 0 || updatePhone.getText().equals("")) {
            errors.put("phone", "New phone is empty");
        }

        return errors;
    }

    /*
        Hàm update thông tin tài khoản
        1. String fullname = updateName.getText();
        String email = updateEmail.getText();
        String phone = updatePhone.getText();
        String address = updateAddress.getText();
        String date = updateDate.getEditor().getText();
        int gender;
        if (updateMaleGender.isSelected()) {
            gender = 1;
        } else if (updateFemaleGender.isSelected()) {
            gender = 2;
        } else {
            gender = 0;
        }
        -> Lấy các thông tin sau khi chỉnh sửa
        2. errors = isValidUpdateInfo() -> gọi hàm validate các thông tin chỉnh sửa
        3. if (errors.size() == 0) {
            if (userModel.update(currentUserLogin, fullname, email, phone, address, date, gender)) {
                updatedAlert();
            } else {
                updateFailedAlert();
            }
        } else {
            errorsAlert();
        }
        -> Nếu không có lỗi thì gọi hàm update để thay đổi thông tin và đưa ra thông báo thành công, ko thì sẽ báo sai
     */
    public void updateInfo(ActionEvent actionEvent) {
        String fullname = updateName.getText();
        String email = updateEmail.getText();
        String phone = updatePhone.getText();
        String address = updateAddress.getText();
        String date = updateDate.getEditor().getText();
        int gender;
        if (updateMaleGender.isSelected()) {
            gender = 1;
        } else if (updateFemaleGender.isSelected()) {
            gender = 2;
        } else {
            gender = 0;
        }
        errors = isValidUpdateInfo();
        if (errors.size() == 0) {
            if (userModel.update(currentUserLogin, fullname, email, phone, address, date, gender)) {
                updatedAlert();
            } else {
                updateFailedAlert();
            }
        } else {
            errorsAlert();
        }
    }

    // Các hàm alert đưa ra thông báo
    private void errorsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Update information failed");
        alert.setHeaderText(null);
        alert.setContentText("Please fix following errors and try again");
        for (String message : errors.values()) {
            alert.setContentText(message);
        }
        alert.showAndWait();
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

    // Hàm hiện lên cửa sổ thay đổi password
    public void changePasswordBtn(ActionEvent actionEvent) throws Exception {
        Stage secondStage = new Stage();
        Parent secondScene = FXMLLoader.load(getClass().getResource("/fxml/changePassword.fxml"));
        secondStage.initOwner(primaryStage);
        secondStage.initModality(Modality.APPLICATION_MODAL);
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

    // Hàm logout
    public void logout(ActionEvent actionEvent) {
        currentUserLogin = null;
        logoutAlert();
        loginStage = loginStage();
        loginStage.show();
    }

    //Hàm hiện ra cửa sổ thay đổi thông tin tk
    public void toUpdateForm(ActionEvent actionEvent) throws Exception {
        updateStage = new Stage();
        updateScene = FXMLLoader.load(getClass().getResource("/fxml/updateInfo.fxml"));
        updateStage.initOwner(primaryStage);
        updateStage.initModality(Modality.APPLICATION_MODAL);
        updateStage.setTitle("Update account information");
        updateStage.setScene(new Scene(updateScene, 800, 600));
        updateStage.setResizable(false);
        updateStage.show();
    }

    // Hàm hiện ra cửa sổ login sau khi logout
    public Stage loginStage() {
        try {
            loginStage = (Stage) logout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            loginStage.setScene(new Scene(root, 550, 700));
            loginStage.setResizable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return loginStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
