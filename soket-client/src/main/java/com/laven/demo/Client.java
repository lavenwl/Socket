package com.laven.demo;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try {
            Socket socket = new Socket("localhost", 8088);

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("我是客户端, 这是我发送的第一条信息\n");
            bufferedWriter.flush();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverString = bufferedReader.readLine();
            System.out.println("服务端返回的信息:" + serverString);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
