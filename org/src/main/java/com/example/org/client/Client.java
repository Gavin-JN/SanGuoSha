package com.example.org.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        //获取本机ip地址
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();

        //连接服务端，去找到指定服务器
        Socket socket = new Socket("127.0.0.1", 3333);

        //创建socket的输出流
        OutputStream os = socket.getOutputStream();
        //把低级的输出流包装成高级的数据输出流
        DataOutputStream dos = new DataOutputStream(os);

        //反复发消息
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请说");
            String msg = sc.nextLine();

            if (msg.equals("exit")) {
                System.out.println("欢迎下次使用");
                dos.close();
                socket.close();
                break;
            }
            dos.writeUTF(ipAddress + ":" + msg);
            dos.flush();

        }

    }
}