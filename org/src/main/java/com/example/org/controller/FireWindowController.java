package com.example.org.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FireWindowController {
    public static final String SERVER_ADDRESS = "192.168.58.82";
    public static final int SERVER_PORT = 1688;
    public JSONObject massage = new JSONObject();

    public int curCardId = 0;


    public FireWindowController() {}
    public void SendMessage(boolean IsPlay,int outCard){
            if (IsPlay) {
                curCardId = outCard;
                System.out.println("curCardId为："+curCardId);
                switch (curCardId) {
                    //出杀
                    case 1:
                        System.out.println("出了杀 获得了杀信息");
                        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                            // 发送消息到服务器 1011表示杀
                            massage.put("NetCode", 1011);
                            String jsonString = massage.toString();
                            out.println(jsonString);
                            massage.clear();
//
//                            //读从服务器来的信息
//                            jsonString = in.readLine();
//                            massage = new JSONObject(jsonString);
//                            //测试
//                            int response;
//                            if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                System.out.println(response);
//                            }
//
//                            System.out.println(jsonString);
//                            massage = new JSONObject(jsonString);

                            out.close();
                            in.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }

}
