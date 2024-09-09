package com.example.org.server;

import com.example.org.server.impl.GameEventHandlingImpl;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


public class GameServetReaderThread extends GameServer implements Runnable{


    private static Socket socket;
    public GameServetReaderThread(Socket socket) throws IOException {
        GameServetReaderThread.socket = socket;
        if (GameServetReaderThread.socket == null) {
            throw new IllegalArgumentException("Socket cannot be null");
        }
    }



    //信号
    public int netCode;
    //信号处理者
    public GameEventHandlingImpl gameEventHandling = new GameEventHandlingImpl();


    @Override
    public void run() {
        System.out.println("Game ServetReader thread started");
        try {

            //输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            massage = new JSONObject(br.readLine());
            netCode = massage.getInt("NetCode");
            System.out.println(netCode);

            //创建socket的输出流
            OutputStream os= socket.getOutputStream();
            //把低级的输出流包装成高级的输出流
            PrintWriter pw = new PrintWriter(os, true);



//            String line = reader.readLine();
//            JSONObject jsonObject = new JSONObject(line);
//
//            String ipAddress = jsonObject.getString("ipAddress");
//            String player = jsonObject.getString("player");
//            int score = jsonObject.getInt("score");
//
//            System.out.println(ipAddress + " " + player + " " + score);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}





//
//    //创建输入流
//        try {
//        InputStream is = socket.getInputStream();
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }
//    BufferedReader reader = null;
//        try {
//        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }
//    String line = null;
//        try {
//        line = reader.readLine();
//    } catch (IOException e) {
//        throw new RuntimeException(e);
//    }
//    //用JSONObject类实例获取数据
//    JSONObject jsonObject = new JSONObject(line);
//    String ipAddress = jsonObject.getString("ipAddress");
//    String player = jsonObject.getString("player");
//    int score = jsonObject.getInt("score");
//
//        System.out.println(ipAddress + " " + player + " " + score);

