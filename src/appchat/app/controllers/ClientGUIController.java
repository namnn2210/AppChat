package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.ContactModel;
import appchat.app.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;

public class ClientGUIController {

    private Stage stage;
    private Parent root;
    private UserModel userModel = new UserModel();
    private ContactModel contactModel = new ContactModel();

    @FXML
    private ImageView accountInfo;

    public static User currentUserLogin;

    private StringProperty mUsername;

//    private ObservableList<String> listFriendforView = FXCollections.observableArrayList();
//            userModel.getListUser(contactModel.getListContact(currentUserLogin.getId()));

    @FXML
    ListView<User> listFriend;

    @FXML
    private Button addBtn;



    public ClientGUIController() {
        mUsername = new SimpleStringProperty();
        setUsername(currentUserLogin.getUserName());
    }

    public String getUsername() {
        return mUsername.get();
    }

    public StringProperty usernameProperty() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername.set(username);
    }

    public void handleAccountInfo(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() == accountInfo) {
            stage = (Stage) accountInfo.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/accountInfo.fxml"));
            stage.setTitle("Account Information");
            stage.setScene(new Scene(root, 1000, 800));
            stage.setResizable(false);
            stage.show();
        }
    }

    public void addFriend(ActionEvent actionEvent) throws Exception {
        stage = (Stage) addBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/addFriendForm.fxml"));
        stage.setTitle("Add new friend");
        stage.setScene(new Scene(root, 382, 166));
        stage.setResizable(false);
        stage.show();
    }
}
