package com.example.org;

import java.util.ArrayList;
import java.util.List;

//用来管理玩家手牌，实时更新
public class PlayerCardManager {
    Player owner;
    List<Card> item=new ArrayList<>();

    public PlayerCardManager(Player player){
        owner=player;
    }
    //出牌时，检验手上是否有对应的牌，即能不能出牌
    public boolean container(int...ids) {//传入多个id
     boolean IsHave=true;
     //需要出的牌和手中的牌一一对照
        return IsHave;
    }
}
