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

    //游戏玩家数
    public static int count = 0;
    //传输数据的JSON文件
    public static JSONObject message = new JSONObject();
    //信号
    public static int netCode;
    //信号处理者
    public static GameEventHandlingImpl gameEventHandling = new GameEventHandlingImpl();

    //是否进行初始化
    public static Boolean isInitGame = true;
    //初始化获得的msg0，msg1信息
    public static JSONObject Game_msg[];
    //暴力使用参数,一次性参数
    public static int violent = 0;

    //线程集存储所有的客户端 通过Socket进行连接
    private static final ConcurrentHashMap<Socket, PrintWriter> clientMapBySocket = new ConcurrentHashMap<>();
    //用id进行标志
    private static final ConcurrentHashMap<String, PrintWriter> clientMapById = new ConcurrentHashMap<>();
    //收集所有socket的pw
    private static List<PrintWriter> clientWriters = new ArrayList<>();

    private static int clientIdCounter = 0;  // 用于分配唯一的客户端ID
    private static String[] clientIpMap = new String[100];


    public static void main(String[] args) throws IOException {
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
            int clientId = clientIdCounter++;
            clientIpMap[clientId] = clientSocket.getInetAddress().getHostAddress();
            System.out.println("用id来表示socket"+clientIpMap[clientId]+"    "+clientId);

//            clientMapById.put(clientId, new PrintWriter(clientSocket.getOutputStream(), true));
            clientMapById.put(clientIpMap[clientId], new PrintWriter(clientSocket.getOutputStream(), true));
            clientMapBySocket.put(clientSocket, new PrintWriter(clientSocket.getOutputStream(), true));
            clientWriters.add(new PrintWriter(clientSocket.getOutputStream(), true));
            new Thread(new ClientHandler(clientSocket,clientId)).start();
        }
    }//

    //统一发信息
    public static void broadcastMessage(String message) {
        for (PrintWriter writer : clientMapBySocket.values()) {
            writer.println(message);
        }
    }

    public static void sendMessageToClient(Socket clientSocket, String message) {
        PrintWriter writer = clientMapBySocket.get(clientSocket);
        if (writer != null) {
            writer.println(message);
            writer.flush(); // 确保消息立即发送
        }
    }

    //给特定客户端发信息
    public static void sendMessageToClientById(int clientId, String message) {
        PrintWriter writer = clientMapById.get(clientIpMap[clientId]);
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
                clientMapBySocket.put(clientSocket, out);
                String jsonString;
                while ((jsonString = in.readLine()) !=null)
                {
                    message = new JSONObject(jsonString);
                    netCode = message.getInt("NetCode");
                    break;
                }

                switch(netCode){
                    //匹配
                    case 1002:
                    {
                        //收信息
                        //看人数够了没有
                        int msg = message.getInt("count");
//                      Boolean is = gameEventHandling.Matching(msg);
                        count = count+msg;
                        System.out.println("游戏人数"+count);

                        while(true){
                            if(count<2){
                                out.println("0");
                            }else {
                                count = 0;
                                System.out.println("游戏开始");

                                // 1表示开始游戏
                                broadcastMessage("1");
                                break;
                            }
                        }
                        break;
                    }
                    //初始化界面
                    case 1010:    //第二个信息
                    {
                        //一定是这样的
                        JSONObject tmp_massage;
                        //暴力算法
                        if ((violent % 2) == 0) {
                            tmp_massage = Game_msg[violent];
                            violent++;
                        } else {
                            tmp_massage = Game_msg[violent];
                        }

                        //算法得到的初始化信息
                        message.put("MessageIdentified", "YES");
                        message.put("Order", tmp_massage.getInt("Order"));
                        message.put("HeroId", tmp_massage.getInt("HeroId"));
                        message.put("enemyHeroId", tmp_massage.getInt("enemyHeroId"));
                        message.put("HandCardList", tmp_massage.getJSONArray("HandCardList"));
                        jsonString = message.toString();
                        //打印对应信息
                        System.out.println(jsonString);
                        message.clear();
                        sendMessageToClient(clientSocket, jsonString);
                        break;
                    }
                    //出杀
                    case 1011:
                    {
                        //收到
                        System.out.println("收到1011");
                        //向另一台主机发信息
                        message.put("ResponseCode",1000);
                        //再带一个信息("display","1")

                        //
                        jsonString = message.toString();
                        message.clear();
                        System.out.println("客户端A: "+clientId+" 发送");
                        System.out.println("客户端B: "+(clientId+1)%2+" 发送");
                        System.out.println(jsonString);

                        //发送信息

                        broadcastMessage(jsonString);
                        sendMessageToClient(clientSocket, jsonString);
                        sendMessageToClientById(clientId,jsonString);
                        sendMessageToClientById((clientId+1)%2,jsonString);

                        break;
                    }

                    case 1111:
                    {
                        System.out.println("收到1111");
                        message.put("Msg","1" );
                        jsonString = message.toString();
                        System.out.println(jsonString);
                        message.clear();
                        break;
                    }
                    //出闪
                    case 1012:
                    {
                        System.out.println("收到1012");
                        //向另一台主机发送是否出闪
                        message.put("Display",2);
                        break;
                    }

                    //出桃
                    case 1013:
                    {
                        System.out.println("收到");
                        message.put("Display",3);
                        jsonString = message.toString();
                        System.out.println(jsonString);
                        message.clear();
                        //发信息给另一个客户端
                        break;
                    }

                    //出酒
                    case 1014:
                    {
                        System.out.println("收到");
                        message.put("Display",4);
                        jsonString = message.toString();
                        System.out.println(jsonString);
                        message.clear();
                        //发信息给另一个客户端
                        break;
                    }

                    //出顺手牵羊
                    case 1015:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出过河拆桥
                    case 1016:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出无中生有
                    case 1017:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出借刀杀人
                    case 1018:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出决斗
                    case 1019:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出无懈可击
                    case 1020:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出乐不思蜀
                    case 1021:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出兵粮寸断
                    case 1022:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出南蛮入侵
                    case 1023:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出万箭齐发
                    case 1024:
                    {
                        System.out.println("收到");
                        break;
                    }

                    //出诸葛连弩
                    case 1025:
                    {
                        System.out.println("收到");
                        break;
                    }

                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientMapBySocket.remove(clientSocket);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
