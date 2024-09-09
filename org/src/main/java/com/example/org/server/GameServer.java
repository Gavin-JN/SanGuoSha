package com.example.org.server;

import com.example.org.Player;
import com.example.org.Room;
import com.example.org.fireWindow;
import com.example.org.server.impl.GameEventHandlingImpl;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    private static final int PORT = 1688;

    //传输数据的JSON文件
    public static JSONObject massage = new JSONObject();

    //信号
    public static int netCode;

    //信号处理者
    public static GameEventHandlingImpl gameEventHandling = new GameEventHandlingImpl();

    //游戏玩家数
    public static int count = 0;

    public static int number = 0;

    //游戏玩家的ip
    public static String player0_ip;
    public static String player1_ip;

    //线程集存储所有的客户端
    private static final ConcurrentHashMap<Socket, PrintWriter> clientMap = new ConcurrentHashMap<>();
    //
    private static final ConcurrentHashMap<Integer, PrintWriter> clientMap_everyone = new ConcurrentHashMap<>();

    private static int clientIdCounter = 0;  // 用于分配唯一的客户端ID
    private static String[] clientIpMap = new String[100];

    //是否进行初始化
    public static Boolean isInitGame = true;
    //初始化获得的msg0，msg1信息
    public static JSONObject Game_msg[];
    //暴力使用参数
    public static int violent = 0;


    public static void main(String[] args) throws IOException {
        //线程集初始化
        count = 0;
        clientMap.clear();
        clientMap_everyone.clear();
        clientIdCounter = 0;
        //服务器启动提示
        System.out.println("---服务端启动成功---");
        //创建服务端Socket对象，同时注册端口
        ServerSocket serverSocket = new ServerSocket(1688);

        //把这个客户端对应的通信管道交给一个独立的线程进行处理
        while (true) {

            if(isInitGame){
                gameEventHandling.InitGame();
                Game_msg=new JSONObject[]{
                        gameEventHandling.Getmsg0(),
                        gameEventHandling.Getmsg1()
                };
                System.out.println(Game_msg[0].toString());
                System.out.println(Game_msg[1].toString());
                isInitGame = false;
            }
            //连接客户端
            Socket clientSocket = serverSocket.accept();
            //获取分配id
            int clientId = clientIdCounter++;
            //clientIpMap[clientId] = clientSocket.getInetAddress().getHostAddress();
            clientMap_everyone.put(clientId, new PrintWriter(clientSocket.getOutputStream(), true));
            new Thread(new ClientHandler(clientSocket,clientId)).start();
//
//            massage.put("MessageIdentified", "YES");
//            massage.put("Order",0);
//            massage.put("HeroId",1);
//            massage.put("enemyHeroId",2);
//            String jsonString = massage.toString();
//            sendMessageToClient(0,jsonString);
//            sendMessageToClient(1,jsonString);
//            sendMessageToClient(2,jsonString);
//            massage.clear();
//
//            massage.put("MessageIdentified", "YES");
//            massage.put("Order",1);
//            massage.put("HeroId",2);
//            massage.put("enemyHeroId",1);
//            jsonString = massage.toString();
//            sendMessageToClient(3,jsonString);
//            massage.clear();
        }
    }//

    //统一发信息
    public static void broadcastMessage(String message) {
        for (PrintWriter writer : clientMap.values()) {
            writer.println(message);
        }
    }

    public static void sendMessageToClient(Socket clientSocket, String message) {
        PrintWriter writer = clientMap.get(clientSocket);
        if (writer != null) {
            writer.println(message);
            writer.flush(); // 确保消息立即发送
        }
    }

    //给特定客户端发信息
    public static void sendMessageToClientById(int clientId, String message) {
        PrintWriter writer = clientMap_everyone.get(clientId);
        if (writer != null) {
            writer.println(message);
        }
    }

    //初始化单例客户端
    private static class ClientHandler implements Runnable {
        //客户端的Socket节点
        private final Socket clientSocket;
        //客户端在线程集中的编号
        private int clientId;
//        //客户端在线程集中的ip地址记录
//        private String clientIp;

        //构造器
        public ClientHandler(Socket socket,int clientId) {
            this.clientSocket = socket;
            this.clientId = clientId;
//            this.clientIp = clientIp;
        }


        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                clientMap.put(clientSocket, out);
                String jsonString = in.readLine();
                massage = new JSONObject(jsonString);
                netCode = massage.getInt("NetCode");

                switch(netCode){
                    //匹配
                    case 1002:
                        //收信息
                        //看人数够了没有
                        int msg = massage.getInt("count");
//                      Boolean is = gameEventHandling.Matching(msg);
                        count = count+msg;
                        System.out.println(count);

                        while(true){
                            if(count<2){
                                out.println("0");
//                                System.out.println(count);
                            }else {
//                                System.out.println(count);
                                count = 0;
                                System.out.println("游戏开始");
                                // 发送回消息或处理其他逻辑
                                // 1表示开始游戏
                                broadcastMessage("1");
                                out.close();
                                in.close();
                                clientSocket.close();
                                break;
                            }
                        }
                        break;
                    case 1010:    //第二个信息
                    {
                        System.out.println("执行了");
                        //一定是这样的
                        JSONObject tmp_massage;
                        //暴力算法
                        if((violent%2)==0){
                            tmp_massage = Game_msg[violent];
                            violent++;
                        }else{
                            tmp_massage = Game_msg[violent];
                        }
                        //
                        massage.put("MessageIdentified", "YES");
                        massage.put("Order",tmp_massage.getInt("Order"));
                        massage.put("HeroId",tmp_massage.getInt("HeroId"));
                        massage.put("enemyHeroId",tmp_massage.getInt("enemyHeroId"));
                        jsonString = massage.toString();
                        //这里应该会输出
                        System.out.println(jsonString);
                        massage.clear();
//                        broadcastMessage(jsonString);
                        sendMessageToClient(clientSocket, jsonString);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientMap.remove(clientSocket);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
