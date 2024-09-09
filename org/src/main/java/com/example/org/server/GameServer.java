package com.example.org.server;

import com.example.org.server.impl.GameEventHandlingImpl;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {


    //传输数据的JSON文件
    public static JSONObject massage = new JSONObject();

    //信号
    public static int netCode;

    //信号处理者
    public static GameEventHandlingImpl gameEventHandling = new GameEventHandlingImpl();

    //游戏玩家
    public static int count = 0;




    //ServetSocket比Socket多的功能
    //ServetSocket可以在服务器端创建端口号以对应链接，还可以处理Client发送的用户请求，发出等待请求
    public static void main(String[] args) throws IOException {
        System.out.println("---服务端启动成功---");
        //创建服务端Socket对象，同时注册端口
        ServerSocket serverSocket = new ServerSocket(1688);
        //线程池 用于统一发信息
        ExecutorService threadPool = Executors.newFixedThreadPool(10);


        //把这个客户端对应的通信管道交给一个独立的线程进行处理
        while (true) {
            //等待连接 这里accept返回值是一个socket对象
            Socket socket = serverSocket.accept();
            //提示有人上线了
            System.out.println("玩家上线:" + socket.getRemoteSocketAddress());
            //启动一个新线程
//            GameServetReaderThread readerThread = new GameServetReaderThread(socket);
            threadPool.submit(new ClientHandler(socket));
//            Thread thread = new Thread(readerThread);
//            thread.start();
        }
    }


    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {

                String jsonString = in.readLine();
                massage = new JSONObject(jsonString);
                netCode = massage.getInt("NetCode");

                switch(netCode){
                    case 1002:
                        //匹配
                        int msg = massage.getInt("count");
                        Boolean is = gameEventHandling.Matching(msg);
                        count = count+msg;
                        System.out.println(count);

                        while(true){
                            if(count<2){
                                out.println("0");
                                System.out.println(count);
                            }else {
                                System.out.println(count);
                                count = 0;
                                System.out.println("游戏开始");
                                // 发送回消息或处理其他逻辑
                                out.println("1");
                                out.close();
                                in.close();
                                clientSocket.close();
                                break;
                            }
                        }
                        break;
                    case 1010:    //第二个信息

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
