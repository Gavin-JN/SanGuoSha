package com.example.org.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;

public class FireWindowController {
    public static final String SERVER_ADDRESS = "192.168.58.82";
    public static final int SERVER_PORT = 1688;
    public JSONObject massage = new JSONObject();

    public int curCardId = 0;
    public boolean GetMessage = false;
    private static final long TIMEOUT = 1; // 超时时间，单位为秒


    public FireWindowController() {



    }
//        System.out.println("FireWindowController Constructor");
//        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//
//            String jsonString = "0";
//
//            if(GetMessage) {
//                while (true){
//                    //收到信息
//                    jsonString = in.readLine();
//                    massage = new JSONObject(jsonString);
//                    //测试
//                    int response = 1000;
//                    if (response==massage.getInt("ResponseCode")) {
//                        System.out.println(response);
//                        break;
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    public void GetMessage(){

    };
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


                            while (true){
                                //收到信息
                                jsonString = in.readLine();
                                massage = new JSONObject(jsonString);
                                //测试
                                int response = 1000;
                                if (response==massage.getInt("ResponseCode")) {
                                    System.out.println(response+"收到信息了");
                                    break;
                                }

                            }

                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    //出闪
                    case 2:
                    {

                        break;
                    }
                    //桃
                    case 3:
                    {

                        break;
                    }
                    //酒
                    case 4:
                    {
                        break;
                    }
                    //顺手牵羊
                    case 5:
                    //
                    case 6:
                    case 7:
                    case 8:
                }
            }
        }

}
