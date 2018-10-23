package appchat.app.controllers;

import appchat.app.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientGUIController {
    private LoginController loginController = new LoginController();


    private User currentUserLogin = loginController.getCurrentLoginUserToGUI();


    private StringProperty mUsername;

    public ClientGUIController() {
        mUsername = new SimpleStringProperty();
        setmUsername("abbbb");
    }

    public String getmUsername() {
        return mUsername.get();
    }

    public StringProperty mUsernameProperty() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername.set(mUsername);
    }


}
