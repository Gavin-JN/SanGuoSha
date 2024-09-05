package com.example.org;

import java.util.List;

public class Room {
    static int index=1;
    public int roomId;
    public List<Card> cardList;
    public Card curretCard;
    public List<Player> players;
    public List<Player> respPlayers;
    public List<Player> helpPlayers;
    public Player dyingPlayer;
    public List<Room> roomList;
    public int turn;
    enum roomStatus {InitStatus,JudgeStatus,DrawStatus,PlayStatus,DiscardStatus,
                    ResponseStatus,RescueStatus,Closed,};
    public roomStatus status;

    public Room(int roomId) {
        this.roomId = roomId;
    }

    public int getTurn() {
        return turn;
    }

    public roomStatus getStatus() {
        return status;
    }


    public void createRoom(){
        Room room = getRoom();
        room.Init(players);
    }

    public Room getRoom(){
        for(Room room:roomList){
            if(room.getStatus()== roomStatus.Closed) return room;
        }
        Room room = new Room(index++);
        roomList.add(room);
        return room;
    }
    public void Init(List<Player> playerList){
        this.players = playerList;
    }
}
