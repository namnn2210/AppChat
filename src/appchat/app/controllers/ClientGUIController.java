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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class ClientGUIController implements Initializable {

    private Stage stage;
    private Parent root;
    private UserModel userModel = new UserModel();
    private ContactModel contactModel = new ContactModel();

    @FXML
    private ImageView accountInfo;

    @FXML
    private ImageView addFriendBtn;

    public static User currentUserLogin;

    private StringProperty mUsername;

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

    public void addFriend(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() == addFriendBtn) {
            Stage addFriendStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addFriendForm.fxml"));
            addFriendStage.setTitle("Add new friend");
            addFriendStage.setScene(new Scene(root, 382, 166));
            addFriendStage.setResizable(false);
            addFriendStage.show();
        }
    }

    //Chọn friend trong list để chat
    public void onClickItemListView(MouseEvent mouseEvent) throws Exception {
        System.out.println("Click on id =" + listFriend.getSelectionModel().getSelectedItem().getId());

//        User user = listFriend.getSelectionModel().getSelectedItem();
//        System.out.println(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> listFriendforView = listFriend.getItems();
        for (User user : userModel.getListUser(contactModel.getListContact(currentUserLogin.getId()))) {
            listFriendforView.add(user);
        }
        listFriend.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> cell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean bln) {
                        super.updateItem(user, bln);
                        if (user != null) {
                            setText(user.getUserName());
                        }
                    }
                };
                return cell;
            }
        });
    }
}
