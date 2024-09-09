package com.example.org.server.impl;

import com.example.org.server.GameEventHandling;

//方法清单
public class GameEventHandlingImpl implements GameEventHandling {
    //玩家人数
    public static int Number_of_Player = 0;

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

    //

}
