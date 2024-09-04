package com.example.org.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameServerUI extends Application {

    @Override
    public void start(Stage Stage) throws Exception {
        //服务器登录界面
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stage = new Stage();
        stage.setTitle("XXX游戏服务器");
        stage.setScene(scene);
        stage.show();
    }
}
