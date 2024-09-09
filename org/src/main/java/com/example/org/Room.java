package com.example.org;

import javafx.scene.layout.BackgroundImage;

import java.util.ArrayList;
import java.util.List;

public class Room {
    static int index=1; //初始房间数
    public int sid;
    public int roomId;
    public List<Card> cardList;  //牌堆

    public Card currentCard;  //当前处理的牌

    public List<Player> players; //房间内所有玩家
    public List<Player> respPlayers;  //房间内待响应的玩家
    public List<Player> helpPlayers;  //房间内考虑救援的玩家
    public Player dyingPlayer;  //濒死求援的玩家
    public List<Player> wxkjPlayers;  //无懈可击响应玩家
    public Player jdsrPlayer;  //借刀杀人被指定杀的玩家
    public List<Room> roomList;
    public int turn;
    enum roomStatus {InitStatus,SelectStatus,JudgeStatus,DrawStatus,PlayStatus,DiscardStatus,
                    RespStatus,RescueStatus,Closed}; //房间各阶段
    public roomStatus status;
    public Receiver receiver;

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
        receiver = new Receiver(this);
        this.players = playerList;
        respPlayers = new ArrayList<>();
        helpPlayers = new ArrayList<>();
        wxkjPlayers = new ArrayList<>();
        jdsrPlayer = new Player();
        setStatus(roomStatus.InitStatus);
        CardManager.CreateCardList();
        cardList=CardManager.cardsPile;
        turn=0;
        for(int i=0;i< players.size();i++){
            players.get(i).handCardList.clear();
            players.get(i).equipCardList=null;
            players.get(i).judgeCardList[0]=false;
            players.get(i).judgeCardList[1]=false;
        }
       for(int m=0;m<4;m++) {
          for (Player player : players)
          for (int i = 0; i < 1; i++) player.handCardList.add(player.getCardByType(player.DrawCard(cardList)));
       }
        //随机分配英雄操作
        selectHero(players.get(0));
        players.get(0).setHpLimit();
        //初始化血量为武将血量
        players.get(0).setHp(players.get(0).getHpLimit());
        selectHero(players.get(1), players.get(0).getHero()); // 确保第二个玩家的武将不同
        players.get(1).setHpLimit();
        //初始化血量为武将血量
        players.get(1).setHp(players.get(1).getHpLimit());
        //给玩家随机分配座位号，确保座位号不能一样
        players.get(0).setSeatId(players.get(0).randomSeatId());
        if(players.get(0).seatId==0)
        {
            players.get(1).setSeatId(1);
        }
        else {
            players.get(1).setSeatId(0);
        }

        assignRoomToPlayers();  // 为每个玩家分配房间
        status=roomStatus.JudgeStatus;

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
    public void update(){
        switch(status){
            case JudgeStatus:{
                if(getPlayerBySeatId(turn).judgeCardList[0]){
                    //多种情况：1.第一次进入判断，通知他人出无懈可击
                    //2.是否处于他人无懈可击状态
                    //3.已经不是无懈可击了，判定是否生效
                    int status = getPlayerBySeatId(turn).buffStatus;
                    if(status == 0){
                        currentCard=new LeBuSiShu(11);
                        respPlayers.add(getPlayerBySeatId(turn));
                        currentCard.setwxkjResp(getPlayerBySeatId(turn));
                        getPlayerBySeatId(turn).buffStatus=1;
                        return;
                    }
                    else if(status==1){
                        return;
                    }
                    else if(status==2){
                        getPlayerBySeatId(turn).buffStatus=0;
                    }
                }
                if(getPlayerBySeatId(turn).judgeCardList[1]){
                    //多种情况：1.第一次进入判断，通知他人出无懈可击
                    //2.是否处于他人无懈可击状态
                    //3.已经不是无懈可击了，判定是否生效
                    int status = getPlayerBySeatId(turn).buffStatus;
                    if(status == 0){
                        currentCard=new BingLiangCunDuan(12);
                        respPlayers.add(getPlayerBySeatId(turn));
                        currentCard.setwxkjResp(getPlayerBySeatId(turn));
                        getPlayerBySeatId(turn).buffStatus=1;
                        return;
                    }
                    else if(status==1){
                        return;
                    }
                    else if(status==2){
                        getPlayerBySeatId(turn).buffStatus=0;
                    }
                }
                status=roomStatus.DrawStatus;
                return;
            }
            case DrawStatus:{
                if(!getPlayerBySeatId(turn).isAbleToDraw){
                    getPlayerBySeatId(turn).isAbleToDraw=true;
                    status=roomStatus.PlayStatus;
                }
                else{
                    for(int i=0;i<2;i++) {
                        getPlayerBySeatId(turn).DrawCard(cardList);
                    }
                    status=roomStatus.PlayStatus;
                }
            }
            case PlayStatus:{
                if(!getPlayerBySeatId(turn).isAbleToPlay){
                    getPlayerBySeatId(turn).isAbleToDraw=true;
                    status=roomStatus.DiscardStatus;
                }
                else{
                    //出牌直到Abandon
                }
            }
            case DiscardStatus:{
                //弃牌至当前血量
                int nextTurn = (++turn)%players.size();
                turn = nextTurn;
                status= Room.roomStatus.JudgeStatus;
            }
        }
    }
    //初始化时分配英雄
    public void selectHero(Player player) {
        int id = (int) (1 + Math.random() * 7);
        assignHero(player, id);
    }

    public void selectHero(Player player, Heroes otherHero) {
        int id;
        do {
            id = (int) (1 + Math.random() * 7);
        } while (isSameHero(id, otherHero));
        assignHero(player, id);
    }

    private boolean isSameHero(int id, Heroes otherHero) {
        switch (id) {
            case 1: return otherHero instanceof sunQuan;
            case 2: return otherHero instanceof caoCao;
            case 3: return otherHero instanceof zhaoYun;
            case 4: return otherHero instanceof zhangFei;
            case 5: return otherHero instanceof zhuGeLiang;
            case 6: return otherHero instanceof zhangLiao;
            case 7: return otherHero instanceof guoJia;
            default: return false;
        }
    }

    private void assignHero(Player player, int id) {
        switch (id) {
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
                player.setHero(new guoJia());
                break;
        }
    }

    public void assignRoomToPlayers() {
        for (Player player : players) {
            player.room = this;
        }
    }
}
