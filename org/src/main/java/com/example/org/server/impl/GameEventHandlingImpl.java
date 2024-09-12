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
    public Player player0 = new Player();
    public Player player1 = new Player();
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
        if (Number_of_Player < 2) {
            System.out.println(Number_of_Player);
            return false;
        } else {
            System.out.println(Number_of_Player);
            Number_of_Player = 0;
            return true;
        }
    }

    public void InitGame() {
        //这里初始化游戏的玩家出牌顺序，我方武将，敌方武将，手牌
        jsonArray0.clear();
        jsonArray1.clear();

        players.add(player0);
        players.add(player1);
        if (players.get(0).seatId == 0) {
            players.get(1).setSeatId(1);
        } else {
            players.get(1).setSeatId(0);
        }
        room.Init(players);
        //这里可以操控武将选择便于测试
        msg0.put("Order",0);
        msg0.put("HeroId",1);
        msg0.put("enemyHeroId",2);
        jsonArray0.put(0,15);
        jsonArray0.put(1,16);
        jsonArray0.put(2,18);
        jsonArray0.put(3,19);
        jsonArray0.put(4,6);
        jsonArray0.put(5,7);
        jsonArray0.put(6,13);
        jsonArray0.put(7,14);
        jsonArray0.put(8,1);
        jsonArray0.put(9,1);
        jsonArray0.put(10,1);
        jsonArray0.put(11,4);
//        for (int i = 0; i < 4; i++) {
//            jsonArray0.put(i,player0.DrawCard(room.cardList));
//        }
        msg0.put("HandCardList", jsonArray0);

//        随机生成
//        msg0.put("HandCardList", jsonArray0);
//
//        msg0.put("Order",player0.getSeatId());
//        msg0.put("HeroId",player0.getHero());
//        msg0.put("enemyHeroId",player1.getHero());
//        for (int i = 0; i < 4; i++) {
//            jsonArray0.put(i,player0.DrawCard(room.cardList));
//        }
//        msg0.put("HandCardList", jsonArray0);

//        msg1.put("HandCardList", jsonArray0);
//        msg1.put("Order",player1.getSeatId());
//        msg1.put("HeroId",player1.getHero());
//        msg1.put("enemyHeroId",player0.getHero());
//        for (int i = 0; i < 4; i++) {
//            jsonArray0.put(i,player1.DrawCard(room.cardList));
//        }
//        msg1.put("HandCardList", jsonArray1);



//        //测试1 演示无限杀 和 郭嘉扣血拿牌
//        jsonArray0.put(0,1);
//        jsonArray0.put(1,1);
//        jsonArray0.put(2,1);
//        jsonArray0.put(3,4);
//        //测试2 演示过河拆桥 无中生有 南蛮入侵 万箭齐发
//        jsonArray0.put(0,6);
//        jsonArray0.put(1,7);
//        jsonArray0.put(2,13);
//        jsonArray0.put(3,14);
//        //测试3 演示装备牌
//        jsonArray0.put(0,15);
//        jsonArray0.put(1,16);
//        jsonArray0.put(2,18);
//        jsonArray0.put(3,19);
//        //测试4 孙权 曹操 和 一张杀
//        jsonArray0.put(0,1);
//        jsonArray0.put(1,1);
//        jsonArray0.put(2,1);
//        jsonArray0.put(3,19);

//        msg0.put("Order",0);
//        msg0.put("HeroId",1);
//        msg0.put("enemyHeroId",2);
//        msg1.put("Order",1);
//        msg1.put("HeroId",2);
//        msg1.put("enemyHeroId",1);


        //加入卡牌

        msg1.put("Order",1);
        msg1.put("HeroId",2);
        msg1.put("enemyHeroId",1);
        jsonArray1.put(0,15);
        jsonArray1.put(1,16);
        jsonArray1.put(2,18);
        jsonArray1.put(3,19);
//        for (int i = 0; i < 4; i++) {
//            jsonArray1.put(i, 16);
//        }
        msg1.put("HandCardList", jsonArray1);
//        jsonArray1.clear();
    }

    public JSONObject Getmsg0() {
        return msg0;
    }

    public JSONObject Getmsg1() {
        return msg1;
    }
}
//    public static void main(String[] args) {
//        int Number_of_Player = 0;
//
//        //游戏玩家
//        Player player0 =new Player();
//        Player player1 =new Player();
//         List<Player> players = new ArrayList<Player>();
//         Room room = new Room(1);
//        //传输信息
//        JSONObject msg0 = new JSONObject();
//        JSONObject msg1 = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        //这里初始化游戏的玩家出牌顺序，我方武将，敌方武将，手牌
//        players.add(player0);
//        players.add(player1);
//        if(players.get(0).seatId==0)
//        {
//            players.get(1).setSeatId(1);
//        }
//        else {
//            players.get(1).setSeatId(0);
//        }
//        room.Init(players);
//        //这里可以操控武将选择便于测试
//
//        msg0.put("Order",player0.seatId);
//        msg0.put("HeroId",player0.getHero().getHeroId());
//        msg0.put("enemyHeroId",player1.getHero().getHeroId());
//        for(int i=0;i<4;i++){
//            jsonArray.put(i,1);
//            //player0.handCardList.get(i).getTypeId()
//        }
//        //
//        msg0.put("HandCardList",jsonArray);
//        System.out.println(jsonArray.toString());
//        jsonArray.clear();
//
//        //加入卡牌
//
//        msg1.put("Order",player1.seatId);
//        msg1.put("HeroId",player1.getHero().getHeroId());
//        msg1.put("enemyHeroId",player0.getHero().getHeroId());
//        for(int i=0;i<4;i++){
//            jsonArray.put(i,1);
//            //player1.handCardList.get(i).getTypeId()
//        }
//        msg1.put("HandCardList",jsonArray);
//        System.out.println(jsonArray.toString());
//        jsonArray.clear();
//    }
//}

