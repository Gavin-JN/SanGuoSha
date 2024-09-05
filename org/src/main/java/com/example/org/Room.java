package com.example.org;

import java.util.ArrayList;
import java.util.List;

public class Room {
    static int index=1; //初始房间数
    public int sid;
    public int roomId;
    public List<Card> cardList;  //牌堆
    public Card curretCard;  //当前处理的牌
    public List<Player> players; //房间内所有玩家
    public List<Player> respPlayers;  //房间内待响应的玩家
    public List<Player> helpPlayers;  //房间内考虑救援的玩家
    public Player dyingPlayer;  //濒死求援的玩家
    public List<Room> roomList;
    public int turn;
    enum roomStatus {InitStatus,JudgeStatus,DrawStatus,PlayStatus,DiscardStatus,
                    ResponseStatus,RescueStatus,Closed,}; //房间各阶段
    public roomStatus status;

    public Room(int roomId) {
        this.roomId = roomId;
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
    //房间初始化
    public void Init(List<Player> playerList){
        this.players = playerList;
        setStatus(roomStatus.InitStatus);
        cardList=new ArrayList<>();
        sid=1;
    }
    public void InitHandCard(){
        for (Player player : players) {
            List<Card> cardList1 = new ArrayList<>();
            for(int i=0;i<4;i++){
                cardList1.add(i,new Card(player.DrawCard(CardManager.cardsPile)));
            }
        }
    }
}
