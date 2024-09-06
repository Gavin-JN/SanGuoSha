package com.example.org.controller;

import com.example.org.Player;
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

        //玩家1  认为玩家1为己方
        Player player1 = new Player();
        //玩家2
        Player targetPlayer = new Player();


        fireWindow player=new fireWindow(player1,targetPlayer);  //传入两个玩家

    }
}
