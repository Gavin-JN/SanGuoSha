package com.example.org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {
    private int port = 2777 ;//65535
    private String host = "127.0.0.1" ;
    private ServerSocket serverSocket ;//在net包上面，用来启动服务器

    public GameServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//默认端口

    public GameServer(int port, String host) {
        this.port = port;
        this.host = host;
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        while(true){
            Socket socket = null;
            System.out.println("等待连接");
            try {
                socket = this.serverSocket.accept();    //这里会阻塞 要开一个新线程
                System.out.println("已连接");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//字节流和字符流
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String host = socket.getInetAddress().getHostName();

                User user =new User("",host,socket,in,out);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void run() {

    }
}
