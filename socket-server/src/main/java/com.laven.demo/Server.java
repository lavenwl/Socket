package com.laven.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    static ExecutorService service = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
             serverSocket = new ServerSocket(8088);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort());

                service.execute(new SocketThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
