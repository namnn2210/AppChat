package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
    private static ArrayList<ClientThread> listClient;
//    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        //Tạo list client kết nối với server
        listClient = new ArrayList<>();
        // Tạo server cổng 6565
        ServerSocket serverSocket = new ServerSocket(6565);
        System.out.println("Mở server thành công, đang chờ kết nối từ client");
        while (true){
            //tạo 1 client mới kết nối tới server
            //nếu tạo được sẽ add client vào list Client
            //sử dụng thread để mỗi client có thể chạy độc lập
            ClientThread ct = new ClientThread(serverSocket.accept());
            listClient.add(ct);
            ct.start();
        }
    }

    public static void publicMessage(String message) {
        try {
            for (ClientThread clientThread : listClient) {
                //với mỗi client, nếu client có gửi tin
                if (clientThread.getSocket().isConnected()) {
                    //ghi message
                    clientThread.getBw().write(message);
                    //tạo 1 dòng mới
                    clientThread.getBw().newLine();
                    //đẩy lên server
                    clientThread.getBw().flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}

