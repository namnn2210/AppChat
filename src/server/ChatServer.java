package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
    private static ArrayList<ClientThread> listClient;
//    private static ServerSocket serverSocket;


    public static void main(String[] args) throws IOException {
        listClient = new ArrayList<>();
        // Tạo server
        ServerSocket serverSocket = new ServerSocket(6565);
        System.out.println("Mở server thành công, đang chờ kết nối từ client");
        while (true){
            ClientThread ct = new ClientThread(serverSocket.accept());
            listClient.add(ct);
            ct.start();
        }
    }

    public static void publicMessage(String message) {
        try {
            for (ClientThread clientThread : listClient) {
                if (clientThread.getSocket().isConnected()) {
                    clientThread.getBw().write(message);
                    clientThread.getBw().newLine();
                    clientThread.getBw().flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}

