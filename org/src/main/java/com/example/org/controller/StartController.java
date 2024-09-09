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

    InetAddress localhost = InetAddress.getLocalHost();
    String ipAddress = localhost.getHostAddress();

    try (Socket socket = new Socket("192.168.58.82", 1688);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
    {
        massage.put("NetCode", 1002);
        massage.put("count", 1);
        massage.put("ip", ipAddress);
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

        try (Socket socket = new Socket("192.168.58.82", 1688);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            // 发送消息到服务器
            massage.put("NetCode", 1010);
            String jsonString = massage.toString();
            out.println(jsonString);
            //out.close();
            massage.clear();
            //设置回复信息0
            massage.put("MassageIdentified", "YES");
            massage.put("Order",0);
            massage.put("HeroId",1);
            massage.put("enemyHeroId",2);
            String jsonString0 = massage.toString();
            massage.clear();

            //设置回复信息1
            massage.put("MassageIdentified", "YES");
            massage.put("Order",1);
            massage.put("HeroId",2);
            massage.put("enemyHeroId",1);
            String jsonString1 = massage.toString();
            massage.clear();

            jsonString = in.readLine();
            if(jsonString == null){
                System.out.println("error ");
            }else{
                massage = new JSONObject(jsonString);
                // 读取并打印服务器的响应
                String response;

                while ((response = massage.getString("MessageIdentified" )) != null) {
                    if(response.equals("YES")) {
                        System.out.println(response);
                        break;
                    }
                }

                if(jsonString==null) {
                    System.out.println("errornull");
                }else{
                    System.out.println(jsonString);
                    massage = new JSONObject(jsonString);
                    Order = massage.getInt("Order");
                    we.getHero().getHeroById(massage.getInt("HeroId"));
                    enemy.getHero().getHeroById(massage.getInt("enemyHeroId"));
            }
                out.close();
                in.close();
                socket.close();
                isPlayGame = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    //出牌顺序已经确定
    //确定角色
        fireWindow player=new fireWindow(we,enemy);  //传入两个玩家
        we.ip=ipAddress;
    }
        //先传入两个玩家
        //传入两个玩家之前要确定1出牌顺序2我方武将3敌方武将4自己手牌

    }
}
