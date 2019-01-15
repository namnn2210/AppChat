package appchat.app.controllers;

import appchat.app.entity.Message;
import appchat.app.entity.User;
import appchat.app.model.ContactModel;
import appchat.app.model.MessageModel;
import appchat.app.model.UserModel;
import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import server.ChatServer;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class ClientGUIController implements Initializable {

    private Stage stage;
    private Parent root;
    private UserModel userModel = new UserModel();
    private ContactModel contactModel = new ContactModel();

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String message;

    @FXML
    private ImageView accountInfo;

    @FXML
    private ImageView addFriendBtn;

    @FXML
    private TextField txtchat;

    public static User currentUserLogin;

    private StringProperty mUsername;

    private MessageModel messageModel = new MessageModel();
    private Message messages = null;


    @FXML
    ListView<User> listFriend;

    @FXML
    private Button addBtn;

    @FXML
    private VBox msgField;

    public ClientGUIController() {
        mUsername = new SimpleStringProperty();
        setUsername(currentUserLogin.getUserName());
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

    class ChatClientReaderThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    //Đọc dòng từ server
                    String line = br.readLine();
                    //Tách chuỗi qua kí tự dấu cách (" ") để lấy được tên người gửi message lên server
                    String name[] = line.split(" ");
                    //Nếu có dòng được truyền tới thì in ra console
                    if (line != null) {
                        Platform.runLater(() -> {
                            /* Nếu dòng nhận được từ server đọc được ( khác null )
                            Nếu dòng nhận được không phải từ chính client đang chạy, in ra màn hình với màu mặc định
                            là màu đen
                            */
                            if(!(currentUserLogin.getUserName().equals(name[0]))){
                                Text text = new Text(line);
                                msgField.getChildren().add(text);
                            }

                        });
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
        // Lấy dữ liệu contact (list friend )
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

        //Khi đăng nhập thành công, tạo 1 socket kết nối với server Chat theo cổng 6565 tại máy local
        try {
            socket = new Socket("localhost", 6565);

            //tạo BufferWriter và Reader để ghi và đọc dữ liệu từ client gửi đi và server gửi về
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.ois = new ObjectInputStream(new ObjectInputStream(socket.getInputStream()));
//            this.oos = new ObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
            txtchat.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        try {
                            // lấy dữ liệu từ textfield chat
                            message = txtchat.getText();
                            txtchat.setText("");
                            //nếu có dòng được nhập và Enter
                            if (message != null && !message.equals("")) {
                                //ghi 1 dòng chữ như dưới
                                bw.write(currentUserLogin.getUserName() + " : " + message);
                                //tạo dòng mới
                                bw.newLine();
                                //đẩy lên server
                                bw.flush();
                                // set màu cho dòng vừa nhập hiện lên màn hình là màu đỏ
                                Text text = new Text(currentUserLogin.getUserName() + " : "+message);
                                text.setFill(Color.RED);
                                msgField.getChildren().add(text);
                            }
                            txtchat.clear();
//                            messages = new Message();
//                            messages.setSenderid(currentUserLogin.getId());
//                            messages.setContent(message);
//                            messages.setCreatedat(String.valueOf(LocalDateTime.now()));
//                            messageModel.insert(messages);
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            });
            // Luồng đọc dữ liệu từ server
            ChatClientReaderThread ccrt = new ChatClientReaderThread();
            ccrt.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
