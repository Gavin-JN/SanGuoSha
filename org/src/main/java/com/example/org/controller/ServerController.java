package com.example.org.controller;

import com.example.org.server.GameServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnStatus;


    private Socket socket;
    public ServerController(Socket socket) {
        this.socket = socket;
    }

    public void startServer(ActionEvent event) throws IOException {
        GameServer gameServer = new GameServer();
    }
}
