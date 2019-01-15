package server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public ClientThread(Socket socket) {
        System.out.println(socket.getInetAddress().getHostAddress() + " đã kết nối.");
        this.socket = socket;
        try {
            //Ghi để gửi tin từ client lên server
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            //Đọc để nhận tin từ server gửi về client
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //hàm run() để chạy luồng song song nhiều client
    @Override
    public void run() {
        while (true) {
            try {
                //line là dòng mới đọc được từ server gửi về
                String line = this.br.readLine();
                if (null == line) {
                    break;
                }
                //nếu có dữ liệu truyền về, ghi dữ liệu ra view tại server
                System.out.println(line);
                ChatServer.publicMessage(line);
            } catch (IOException e) {
                System.out.println(socket.getInetAddress().getHostAddress() + " đã thoát.");
                break;
            }
        }
    }
}