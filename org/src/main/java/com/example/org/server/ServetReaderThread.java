package com.example.org.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServetReaderThread  extends Thread {

    private Socket socket;
    public ServetReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            //创建输入流
            InputStream is = socket.getInputStream();
            //包装为数据输入流
            DataInputStream dis = new DataInputStream(is);
            //收信息
            while(true) {
                try {
                    String str = dis.readUTF();
                    System.out.println(str);
                } catch (IOException e) {
                    System.out.println(socket.getRemoteSocketAddress()+"离线了");
                    dis.close();
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
