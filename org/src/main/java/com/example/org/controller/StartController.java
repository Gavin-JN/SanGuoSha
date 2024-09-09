package com.example.org.controller;

import com.example.org.Player;
import com.example.org.Room;
import com.example.org.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.org.fireWindow;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StartController extends Client{

    @FXML
    private Button StartGame;
    @FXML
    private Button ExitGame;

    //是否进入游戏
    public boolean isPlayGame;
    @FXML
    public void ExitGame(ActionEvent event){
        System.exit(0);

    }

    @FXML
    public void StartGame(ActionEvent event)throws IOException {
        try (Socket socket = new Socket("192.168.185.82", 1688);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            massage.put("NetCode", 1002);
            massage.put("count", 1);
            String jsonString = massage.toString();

            // 发送消息到服务器
            out.println(jsonString);

            // 读取并打印服务器的响应
            String response;
            while ((response = in.readLine()) != null) {
                if(response.equals("1")) {
                    break;
                }
            }

            out.close();
            in.close();
            socket.close();
            isPlayGame = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(isPlayGame){
            System.out.println("开始游戏了");
            List<Player> players = new ArrayList<Player>();

            //玩家1
            players.add(we);

            //玩家2
            players.add(enemy);

            Room room=new Room(1);
            room.Init(players);
            fireWindow player=new fireWindow(players.get(0),players.get(1));  //传入两个玩家

        }
    }
}
