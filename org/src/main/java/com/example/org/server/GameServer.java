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
            //连接客户端
            Socket clientSocket = serverSocket.accept();
            //获取分配id
            int clientId = clientIdCounter++;
//            clientIpMap[clientId] = clientSocket.getInetAddress().getHostAddress();

            clientMap_everyone.put(clientId, new PrintWriter(clientSocket.getOutputStream(), true));

            new Thread(new ClientHandler(clientSocket,clientId,clientIpMap[clientId])).start();

            massage.put("MessageIdentified", "YES");
            massage.put("Order",0);
            massage.put("HeroId",1);
            massage.put("enemyHeroId",2);
            String jsonString = massage.toString();
            sendMessageToClient(0,jsonString);
            sendMessageToClient(2,jsonString);
            massage.clear();

            massage.put("MessageIdentified", "YES");
            massage.put("Order",1);
            massage.put("HeroId",2);
            massage.put("enemyHeroId",1);
            jsonString = massage.toString();
            sendMessageToClient(3,jsonString);
            massage.clear();
        }
    }

    //统一发信息
    public static void broadcastMessage(String message) {
        for (PrintWriter writer : clientMap.values()) {
            writer.println(message);
        }
    }

    //给特定客户端发信息
    public static void sendMessageToClient(int clientId, String message) {
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
        //客户端在线程集中的ip地址记录
        private String clientIp;

        //构造器
        public ClientHandler(Socket socket, int clientId,String clientIp) {
            this.clientSocket = socket;
            this.clientId = clientId;
            this.clientIp = clientIp;
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
                        Player player0 =new Player();
                        Player player1 =new Player();
                        List<Player> players = new ArrayList<Player>();
                        players.add(player0);
                        players.add(player1);
                        if(players.get(0).seatId==0)
                        {
                            players.get(1).setSeatId(1);
                        }
                        else {
                            players.get(1).setSeatId(0);
                        }

                        Room room = new Room(1);
                        room.Init(players);

                        player0.ip = player0_ip;
                        player1.ip = player1_ip;



//                        massage.put("Order",-1);
//                        jsonString = massage.toString();
//                        sendMessageToClient(-1,jsonString);
//                        massage.clear();
//
//                        massage.put("Order",0);
//                        jsonString = massage.toString();
//                        sendMessageToClient(0,jsonString);
//                        massage.clear();
//
//                        massage.put("Order",1);
//                        jsonString = massage.toString();
//                        sendMessageToClient(1,jsonString);
//
//                        massage.put("Order",2);
//                        jsonString = massage.toString();
//                        sendMessageToClient(2,jsonString);
//
//                        massage.put("Order",3);
//                        jsonString = massage.toString();
//                        sendMessageToClient(3,jsonString);
//
//                        massage.put("Order",4);
//                        jsonString = massage.toString();
//                        sendMessageToClient(4,jsonString);
//                        PrintWriter writer = clientMap_everyone.get(2);
//                        System.out.println("Client ID: " + 2 + ", PrintWriter: " + writer);
//
//                        massage.put("Order",0);
//                       jsonString = massage.toString();
//                        sendMessageToClient(2,jsonString);
//                        massage.clear();
//
//                        massage.put("Order",1);
//                        jsonString = massage.toString();
//                        sendMessageToClient(3,jsonString);
//                        massage.clear();

//                        //随机发 0_ip的是0
//                        //1_ip的是1
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
