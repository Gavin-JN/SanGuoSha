package com.example.org.server.impl;

import com.example.org.Player;
import com.example.org.Room;
import com.example.org.server.GameEventHandling;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//方法清单
public class GameEventHandlingImpl implements GameEventHandling {
    //玩家人数
    public static int Number_of_Player = 0;

    //游戏玩家
    public Player player0 =new Player();
    public Player player1 =new Player();
    public List<Player> players = new ArrayList<Player>();
    public Room room = new Room(1);
    //传输信息
    private JSONObject msg0 = new JSONObject();
    private JSONObject msg1 = new JSONObject();
    private JSONArray jsonArray0 = new JSONArray();
    private JSONArray jsonArray1 = new JSONArray();


    //匹配
    @Override
    public boolean Matching(int count) {
        Number_of_Player = Number_of_Player + count;
        if(Number_of_Player < 2){
            System.out.println(Number_of_Player);
            return false;
        }
        else{
            System.out.println(Number_of_Player);
            Number_of_Player = 0;
            return true;
        }
    }

    public void InitGame(){
        //这里初始化游戏的玩家出牌顺序，我方武将，敌方武将，手牌
        players.add(player0);
        players.add(player1);
        if(players.get(0).seatId==0)
        {
            players.get(1).setSeatId(1);
        }
        else {
            players.get(1).setSeatId(0);
        }
        room.Init(players);
        //这里可以操控武将选择便于测试
        msg0.put("Order",player0.seatId);
        msg0.put("HeroId",player0.getHero().getHeroId());
        msg0.put("enemyHeroId",player1.getHero().getHeroId());
        for(int i=0;i<4;i++){
            jsonArray0.put(player0.handCardList.get(i).getTypeId());
        }
        msg0.put("HandCardList",jsonArray0);

        //加入卡牌

        msg1.put("Order",player1.seatId);
        msg1.put("HeroId",player1.getHero().getHeroId());
        msg1.put("enemyHeroId",player0.getHero().getHeroId());
        for(int i=0;i<4;i++){
            jsonArray1.put(i,player1.handCardList.get(i).getTypeId());
        }
        msg1.put("HandCardList",jsonArray1);
//        jsonArray1.clear();
    }

    public JSONObject Getmsg0(){
        return msg0;
    }
    public JSONObject Getmsg1(){
        return msg1;
    }

    public static void main(String[] args) {
        int Number_of_Player = 0;

        //游戏玩家
        Player player0 =new Player();
        Player player1 =new Player();
         List<Player> players = new ArrayList<Player>();
         Room room = new Room(1);
        //传输信息
        JSONObject msg0 = new JSONObject();
        JSONObject msg1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //这里初始化游戏的玩家出牌顺序，我方武将，敌方武将，手牌
        players.add(player0);
        players.add(player1);
        if(players.get(0).seatId==0)
        {
            players.get(1).setSeatId(1);
        }
        else {
            players.get(1).setSeatId(0);
        }
        room.Init(players);
        //这里可以操控武将选择便于测试
        msg0.put("Order",player0.seatId);
        msg0.put("HeroId",player0.getHero().getHeroId());
        msg0.put("enemyHeroId",player1.getHero().getHeroId());
        for(int i=0;i<4;i++){
            jsonArray.put(player0.handCardList.get(i).getTypeId());
        }
        msg0.put("HandCardList",jsonArray);
        System.out.println(jsonArray.toString());
        jsonArray.clear();

        //加入卡牌

        msg1.put("Order",player1.seatId);
        msg1.put("HeroId",player1.getHero().getHeroId());
        msg1.put("enemyHeroId",player0.getHero().getHeroId());
        for(int i=0;i<4;i++){
            jsonArray.put(i,player1.handCardList.get(i).getTypeId());
        }
        msg1.put("HandCardList",jsonArray);
        System.out.println(jsonArray.toString());
        jsonArray.clear();
    }
}

