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
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            massage.put("NetCode", 1002);
            massage.put("count", 1);
            massage.put("ip", ipAddress);
            String jsonString = massage.toString();
            // 发送消息到服务器
            out.println(jsonString);

            // 读取并打印服务器的响应 得到1时开始游戏
            String response;
            while ((response = in.readLine()) != null) {
                if (response.equals("1")) {
                    break;
                }
            }
            massage.clear();
            isPlayGame = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isPlayGame) {
            System.out.println(ipAddress + "开始游戏了");
            List<Player> players = new ArrayList<Player>();
            //玩家1
            players.add(we);
            //玩家2
            players.add(enemy);
            Room room = new Room(1);
            room.Init(players);

            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                // 发送消息到服务器
                massage.put("NetCode",1010);
                String jsonString = massage.toString();
                out.println(jsonString);
                massage.clear();

                //读从服务器来的信息
                jsonString = in.readLine();
                massage = new JSONObject(jsonString);
                // 读取并打印服务器的响应
                int response;
                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
                    System.out.println(response);
                }

                System.out.println(jsonString);
                massage = new JSONObject(jsonString);

                Order = massage.getInt("Order");
                we.setSeatId(Order);
                we.setHero(hero.getHeroById(massage.getInt("HeroId")));
                if (Order == 0) {
                    enemy.setSeatId(1);
                } else {
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
            } catch (IOException e) {
                e.printStackTrace();
            }











            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                //传入两个玩家
                massage.put("NetCode",1111);
                String jsonString = massage.toString();
                out.println(jsonString);
                massage.clear();

                fireWindow game = new fireWindow(we, enemy);  //传入两个玩家
                System.out.println(room.status);
                //进行判定
                room.update();

                System.out.println(room.status);
                //进入摸牌阶段
                room.update();

                //测试手牌的数量
                for (int i = 0; i < we.handCardList.size(); i++) {
                    System.out.println(we.handCardList.get(i).getTypeId() + "");
                }
                //摸牌之后更新手牌
                game.renderPlayerCards(game.cardContainer, we);
                System.out.println(room.status);
                //进入出牌阶段

            } catch (IOException e) {
                e.printStackTrace();
            }


            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                System.out.println("Connected to server");
                // 启动一个线程用于读取服务器的响应
                new Thread(() -> {
                    try {
                        String response = in.readLine();
                        while ((response == "12345")) {
                            System.out.println("没有点击按钮也获得了回应");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }


//
//
//
//            //出牌之后更新curCarId
//            //这里要while一直进行
//            while(true){
//                if(game.IsPlay){
//                    game.IsPlay = false;
//                    curCardId = game.outCard;
//                    System.out.println(curCardId);
//                    switch (curCardId){
//                        //出杀
//                        case 1:
//                            System.out.println("出了杀 获得了杀信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        //出闪
//                        case 2:
//                            System.out.println("出了杀 获得了杀信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        //桃
//                        case 3:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        //酒
//                        case 4:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //顺手牵羊
//                        case 5:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //过河拆桥
//                        case 6:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //无中生有
//                        case 7:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //借刀杀人
//                        case 8:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //决斗
//                        case 9:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //无懈可击
//                        case 10:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //乐不思蜀
//                        case 11:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //兵粮寸断
//                        case 12:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //南蛮入侵
//                        case 13:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //万箭齐发
//                        case 14:
//                            System.out.println("出了闪 获得了闪信息");
//                            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                                // 发送消息到服务器
//                                massage.put("NetCode",1011);
//                                String jsonString = massage.toString();
//                                out.println(jsonString);
//                                massage.clear();
//
//                                //读从服务器来的信息
//                                jsonString = in.readLine();
//                                massage = new JSONObject(jsonString);
//                                //测试
//                                int response;
//                                if ((response = massage.getInt("Order")) == 0 || (response = massage.getInt("Order")) == 1) {
//                                    System.out.println(response);
//                                }
//
//                                System.out.println(jsonString);
//                                massage = new JSONObject(jsonString);
//
//                                out.close();
//                                in.close();
//                                socket.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                        //
//                        case 15:
//                        //
//                        case 16:
//                        //
//                        case 17:
//                            //发信息
//                        case 18:
//                            //发信息
//                        case 19:
//                            //发信息
//                        case 20:
//                            //发信息
//                        case 21:
//                            //发信息
//                        case 22:
//                            //发信息
//                        case 23:
//                            //发信息
//                        case 24:
//                            //发信息
//
//                    }
//
//
//
//
//                }
//                //
//                break;
//            }
//
//
//
//
//
//            //游戏一直循环 直到有玩家死亡游戏结束
////            while (true) {
////
//////            如果有玩家死亡结束游戏
////                if (false) {
////                    break;
////                }
////            }
//            //游戏结束
////            System.out.println("游戏结束");
        }
    }
}
