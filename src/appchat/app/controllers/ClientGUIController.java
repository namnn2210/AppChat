package appchat.app.controllers;

import appchat.app.entity.User;
import appchat.app.model.ContactModel;
import appchat.app.model.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

//Controller cho giao diện chat, clientGUI.fxml
public class ClientGUIController implements Initializable {

    private Stage stage;
    private Parent root;
    private UserModel userModel = new UserModel();
    private ContactModel contactModel = new ContactModel();

    private BufferedWriter bw;
    private BufferedReader br;
    private String message;

    // Lấy các ô bên file FXML
    @FXML
    private ImageView accountInfo;

    @FXML
    private ImageView addFriendBtn;

    @FXML
    private TextField txtchat;

    public static User currentUserLogin;

    private StringProperty mUsername;

    @FXML
    ListView<User> listFriend;

    @FXML
    private Button addBtn;


    public ClientGUIController() {
        mUsername = new SimpleStringProperty();
        setUsername(currentUserLogin.getUserName());
    }

    //Gõ tin nhắn
    public void sendMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.message = txtchat.getText();
            System.out.println(message);
            txtchat.clear();
        }
    }

    private void getConnect(Socket socket) {

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

    //Ấn vào icon để hiện account info
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

    //Ấn nút add friend
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

    //client chat hiện ra console server
    class ChatClientReaderThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    //Đọc dòng từ server
                    String line = br.readLine();
                    //Nếu có dòng được truyền tới thì in ra console
                    if (line != null) {
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

        try {
            Socket socket = new Socket("localhost", 6565);
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ChatClientReaderThread ccrt = new ChatClientReaderThread();
            ccrt.start();
            txtchat.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        try {
                            message = txtchat.getText();
                            txtchat.setText("");
                            System.out.println(message);
                            if (message != null && !message.equals("")) {
                                bw.write(currentUserLogin.getUserName() + " : " + message);
                                bw.newLine();
                                bw.flush();
                            }
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
