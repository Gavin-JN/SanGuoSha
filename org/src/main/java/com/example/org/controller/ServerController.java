package com.example.org.controller;

import com.example.org.server.GameServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerController {
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnStatus;


    public void startServer(ActionEvent event) {
        System.out.println("Server started");
        Thread thread = new Thread(new Runnable(){
            public void run() {
                GameServer gameServer = new GameServer();
                gameServer.start();
            }
        });
        thread.start();
    }
}
