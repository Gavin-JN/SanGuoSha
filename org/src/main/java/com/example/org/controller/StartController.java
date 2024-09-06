package com.example.org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.org.fireWindow;

public class StartController {

    @FXML
    private Button StartGame;
    @FXML
    private Button ExitGame;

    @FXML
    public void ExitGame(ActionEvent event){
        System.exit(0);

    }

    @FXML
    public void StartGame(ActionEvent event){
        System.out.println("play");
        fireWindow player=new fireWindow();  //传入两个玩家

    }
}
