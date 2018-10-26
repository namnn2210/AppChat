package appchat.app.server;

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
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = this.br.readLine();
                if (null == line) {
                    break;
                }
                System.out.println(line);
                ChatServer.publicMessage(line);
            } catch (IOException e) {
                System.out.println(socket.getInetAddress().getHostAddress() + " đã thoát.");
                break;
            }
        }
    }
}