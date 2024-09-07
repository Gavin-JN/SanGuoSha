package com.example.org;

import javafx.scene.layout.BackgroundImage;

import java.util.ArrayList;
import java.util.List;

public class Room {
    static int index=1; //初始房间数
    public int sid;
    public int roomId;
    public List<Card> cardList;  //牌堆
    public List<Card> discardList;  //弃牌堆
    public Card currentCard;  //当前处理的牌
    public List<Player> players; //房间内所有玩家
    public List<Player> respPlayers;  //房间内待响应的玩家
    public List<Player> helpPlayers;  //房间内考虑救援的玩家
    public Player dyingPlayer;  //濒死求援的玩家
    public List<Room> roomList;
    public int turn;
    enum roomStatus {InitStatus,SelectStatus,JudgeStatus,DrawStatus,PlayStatus,DiscardStatus,
                    RespStatus,RescueStatus,Closed}; //房间各阶段
    public roomStatus status;

    public Room(int roomId) {
        this.roomId = roomId;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setStatus(roomStatus status) {
        this.status = status;
    }

    public roomStatus getStatus() {
        return status;
    }

    //新建房间
    public void createRoom(){
        Room room = getRoom();
        room.Init(players);
    }

    //若有房间空闲则使用该房间，否则新建房间
    public Room getRoom(){
        for(Room room:roomList){
            if(room.getStatus()== roomStatus.Closed) return room;
        }
        Room room = new Room(index++);
        roomList.add(room);
        return room;
    }
    //房间初始化,玩家武将及牌
    public void Init(List<Player> playerList){
        this.players = playerList;
        setStatus(roomStatus.InitStatus);
        CardManager.CreateCardList();
        cardList=CardManager.cardsPile;

//        for (Player player : players)
//            for(int i=0;i<1;i++)    player.handCardList.add(new Card(player.DrawCard(cardList)));

        Card card=new Sha(1);
        for (Player player : players)
        {
            player.handCardList.add(card);
        }
     players.get(0).setHero( new caoCao());
        players.get(1).setHero( new caoCao());

    }

    public Player getPlayerBySeatId(int seatId){
        return players.get(seatId);
    }
    public void RespWithTarget(Player player,Player targetPlayer,int typeId){
        player.room.respPlayers.add(targetPlayer);
        player.room.setStatus(roomStatus.RespStatus);
        player.room.currentCard= new Card(typeId);
    }
    public void RespWithoutTarget(Player player,int typeId){

    }
    public void GameOver(Room room){
        room.status=roomStatus.Closed;
    }
    public boolean IsPlayFinish(Player player1,Player player2) {
        if(player1.hp==0||player2.hp==0)//判断条件应该为 “某一玩家血量为0且在响应阶段没有使用桃或酒”
        return true;
        else
            return false;
    }
    public  void selectHero(Player player){
        int id=(int)(1+Math.random()*8);
        switch (id)
        {
            case 1:
                player.setHero(new sunQuan());
                break;
            case 2:
                player.setHero(new caoCao());
                break;
            case 3:
                player.setHero(new zhaoYun());
                break;
            case 4:
                player.setHero(new zhangFei());
                break;
            case 5:
                player.setHero(new zhuGeLiang());
                break;
            case 6:
                player.setHero(new zhangLiao());
                break;
            case 7:
                player.setHero(new daQiao());
                break;
            case 8:
                player.setHero(new guoJia());
                break;
        }
    }
}
