package appchat.app.controllers;

import appchat.app.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientGUIController {

    private Stage stage;
    private Parent root;

    @FXML
    private ImageView accountInfo;

    public static User currentUserLogin;

    private StringProperty mUsername;

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
}
