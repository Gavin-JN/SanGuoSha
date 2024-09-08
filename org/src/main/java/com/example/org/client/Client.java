package com.example.org.client;

import com.example.org.GameApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {
    //阿三大苏打实打实
    public static void main(String[] args) throws IOException {
        //获取本机ip地址
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();

        //连接服务端，去找到指定服务器
        Socket socket = new Socket("192.168.185.82", 1688);

        //创建socket的输出流
        OutputStream os = socket.getOutputStream();
        //把低级的输出流包装成高级的数据输出流
        DataOutputStream dos = new DataOutputStream(os);

        GameApplication app = new GameApplication();
        app.launch(args);

        //反复发消息
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请说");
            String msg = sc.nextLine();

            if (msg.equals("exit")) {
                System.out.println("欢迎下次使用");
                dos.close();
                socket.close();
                break;
            }
            dos.writeUTF(ipAddress + ":" + msg);
            dos.flush();

        }

    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("reg.fxml"));
        Node root = fxmlLoader.load();

        // 创建一个StackPane作为新的根布局
        StackPane stackPane = new StackPane();

        // 设置背景图
        Image backgroundImage = new Image(this.getClass().getResourceAsStream("img/bg.png")); // 确保图片路径正确
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        // 应用背景图到StackPane
        stackPane.setBackground(new Background(background));

        // 将FXML加载的内容添加到StackPane中
        stackPane.getChildren().add(root);

        // 使用StackPane的默认大小来创建场景
        Scene scene = new Scene(stackPane);

        // 设置舞台图标和标题
        InputStream in = this.getClass().getResourceAsStream("img/title.png");
        Image image = new Image(in);
        stage.getIcons().add(image);
        stage.setTitle("注册页");
        stage.setScene(scene);
        stage.show();
    }
}

