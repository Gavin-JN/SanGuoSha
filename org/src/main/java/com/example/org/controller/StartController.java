package com.example.org.controller;

import com.example.org.Heroes;
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
    public void StartGame(ActionEvent event){
        System.out.println("play");

        Heroes.sunQuan sunQuan = new Heroes.sunQuan();
        Heroes.caoCao caoCao = new Heroes.caoCao();

        //玩家1  认为玩家1为己方
        Player player1 = new Player(sunQuan);
        //玩家2
        Player targetPlayer = new Player(caoCao);

        fireWindow firewindow=new fireWindow(player1,targetPlayer);  //传入两个玩家
    }



    @FXML
    public void ExitGame(ActionEvent event){System.exit(0);}

}
