package com.example.org.controller;

import com.example.org.Player;
import com.example.org.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.example.org.fireWindow;

import java.util.ArrayList;
import java.util.List;

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

        List<Player> players = new ArrayList<Player>();

        Player player1 = new Player();
        players.add(player1);

        //玩家2
        Player targetPlayer = new Player();
        players.add(targetPlayer);

        Room room=new Room(1);
        room.Init(players);
        fireWindow player=new fireWindow(players.get(0),players.get(1));  //传入两个玩家

    }
}
