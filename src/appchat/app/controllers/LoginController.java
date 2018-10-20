package appchat.app.controllers;

import appchat.app.model.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;

public class LoginController {

    Stage stage;
    Parent root;

    @FXML
    private Button register;

    public void login(ActionEvent actionEvent) {

    }

    public void testConnection(ActionEvent actionEvent) {
        Connection connection = DBConnection.getInstance().getConnection();
        if (connection != null) {
            System.out.println("Connected!");
        } else {
            System.out.println("Connect failed!");
        }
    }

    @FXML
    public void handleRegisterAction(ActionEvent actionEvent) throws Exception {

        if (actionEvent.getSource() == register) {
            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
        }
        stage.setScene(new Scene(root,400,700));
        stage.setResizable(false);
        stage.show();
    }
}
