package appchat.app.controllers;

import appchat.app.entity.Contact;
import appchat.app.entity.User;
import appchat.app.model.ContactModel;
import appchat.app.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Controller điều khiển addFriendForm.fxml
public class addFriendFormController {
    Stage stage;
    Parent root;
    UserModel userModel = new UserModel();
    ContactModel contactModel = new ContactModel();

    public User currentUserLogin = ClientGUIController.currentUserLogin;

    //Lấy các ô bên file FXML
    @FXML
    private Button addFriendBtn;

    @FXML
    private TextField username;

    //Hàm add friend
    public void addHandle(ActionEvent actionEvent) throws Exception{
        String username = this.username.getText();
        User friend = userModel.select(username);
        if (friend != null){
            Contact newfriend = new Contact(currentUserLogin.getId(), friend.getId());
            if(!contactModel.checkExistContact(newfriend)){
                contactModel.insert(newfriend);
                alertSuccess();
            }
            else alertExist();
        }
        else alertUnExistUsername(username);
        stage = (Stage) addFriendBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/clientGUI.fxml"));
        stage.setTitle("Messages");
        stage.setScene(new Scene(root, 1000, 800));
        stage.setResizable(false);
        stage.show();
    }

    // Báo đã là bạn
    public void alertExist(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Friend Failed");
        alert.setHeaderText(null);
        alert.setContentText("You guys are already friend ");
        alert.showAndWait();
    }

    // Báo không tìm thấy user
    public void alertUnExistUsername(String name){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Friend Failed");
        alert.setHeaderText(null);
        alert.setContentText("There's no user name : "+ name);
        alert.showAndWait();
    }

    //Báo add thành công
    public void alertSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Friend Failed");
        alert.setHeaderText(null);
        alert.setContentText("Add friend success ");
        alert.showAndWait();
    }
}
