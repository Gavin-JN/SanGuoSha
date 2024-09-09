package com.example.org.client;

import com.example.org.GameApplication;
import com.example.org.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Application {

    public static void main(String[] args) throws IOException {

        //获取本机ip地址
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        //开启app
        GameApplication app = new GameApplication();
        app.launch(args);
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

