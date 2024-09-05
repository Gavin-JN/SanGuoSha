package com.example.org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer{
    //ServetSocket比Socket多的功能
    //ServetSocket可以在服务器端创建端口号以对应链接，还可以处理Client发送的用户请求，发出等待请求
    public static void main(String[] args) throws IOException {
        System.out.println("---服务端启动成功---");
        //创建服务端Socket对象，同时注册端口
        ServerSocket serverSocket = new ServerSocket(8888);
        //把这个客户端对应的通信管道交给一个独立的线程进行处理
        while(true){
            //等待连接 这里accept返回值是一个socket对象
            Socket socket =  serverSocket.accept();
            //提示有人上线了
            System.out.println("玩家上线:"+socket.getRemoteSocketAddress());
            //启动一个新线程
            new ServetReaderThread(socket).start();
        }
    }
}
