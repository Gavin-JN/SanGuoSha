package com.example.org.controller;

import com.example.org.*;
import com.example.org.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StartController extends Client{

    public static final String SERVER_ADDRESS = "192.168.58.82";
    public static final int SERVER_PORT = 1688;
    //我方玩家
    public Player we =new Player();
    //敌对玩家
    public Player enemy =new Player();

    //上帝hero
    public Heroes hero = new Heroes();
    //上帝Card
    public Card card = new Card();

    //传输数据的JSON文件
    public JSONObject massage = new JSONObject();

    //出牌顺序 0先手 1后手
    public int Order;

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
    //ip地址
    InetAddress localhost = InetAddress.getLocalHost();
    String ipAddress = localhost.getHostAddress();
    //连接服务器
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {
        massage.put("NetCode", 1002);
        massage.put("count", 1);
        massage.put("ip", ipAddress);
        String jsonString = massage.toString();
        // 发送消息到服务器
        out.println(jsonString);

        // 读取并打印服务器的响应 得到1时开始游戏
        String response;
        while ((response = in.readLine()) != null) {
            if(response.equals("1")) {
                break;
            }
        }
        massage.clear();
        out.close();
        in.close();
        socket.close();
        isPlayGame = true;
    } catch (IOException e) {
            e.printStackTrace();
    }

    if(isPlayGame){
        System.out.println(ipAddress+"开始游戏了");

        List<Player> players = new ArrayList<Player>();
        //玩家1
        players.add(we);
        //玩家2
        players.add(enemy);
        Room room=new Room(1);
        room.Init(players);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            // 发送消息到服务器
            massage.put("NetCode", 1010);
            String jsonString = massage.toString();
            out.println(jsonString);
//            out.close();
            massage.clear();


            //读从服务器来的信息
            jsonString = in.readLine();
            massage = new JSONObject(jsonString);
            // 读取并打印服务器的响应
            int response;
            if((response = massage.getInt("Order" ))==0||(response = massage.getInt("Order" ))==1){
                System.out.println(response);
            }
//            while ((response = massage.getString("Order" )) != null) {
//                if(response.equals("0")||response.equals("1")) {
//
//                    break;
//                }
//            }

            System.out.println(jsonString);
            massage = new JSONObject(jsonString);

            Order = massage.getInt("Order");
            we.setSeatId(Order);
            we.setHero(hero.getHeroById(massage.getInt("HeroId")));
            if(Order==0){
                enemy.setSeatId(1);
            }else{
                enemy.setSeatId(0);
            }
            enemy.setHero(hero.getHeroById(massage.getInt("enemyHeroId")));
            JSONArray jsonArray = massage.getJSONArray("HandCardList");
            we.handCardList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                we.handCardList.add(card.getCardByType(jsonArray.getInt(i)));
            }
            System.out.println(jsonArray.length());
            System.out.println(we.handCardList.size());

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    //出牌顺序已经确定
    //确定角色
    //先传入两个玩家
    //传入两个玩家之前要确定1出牌顺序2我方武将3敌方武将4自己手牌
        fireWindow player=new fireWindow(we,enemy);  //传入两个玩家
        System.out.println(room.status);
        //进入判定阶段
        room.update();
        System.out.println(room.status);
        //进入摸牌阶段
        room.update();
        System.out.println(room.status);

//
//        while(true){
//        }
    }


    }
}
