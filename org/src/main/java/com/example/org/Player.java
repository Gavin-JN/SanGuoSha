package com.example.org;

import java.util.List;

public class Player {
    private int heroId;
    private int seatId;
    private List<Card> handCardList;
    private List<Card> equipCardList;
    private List<Card> judgeCardList;
    private int volume;
    private int attackDistance;

    public int getSeatId() {
        return seatId;
    }

    public int getVolume() {
        return volume;
    }

    public boolean IsCurrentRound(){
        return true;
    }
    public boolean IsJudgeCardListEmpty(){
        //^^^^^^^^^^^^^^^^^^^^^^^^^
        return true;
    }
    public boolean IsJudgeMade(){
        //^^^^^^^^^^^^^^^^^^^^
        return true;
    }
    public boolean IsAbleToDraw(){
        return true；
    }
    public void DrawCard(int num){

    }
    public boolean IsAbleToPlay(){
        return true;
    }
    public void PlayCard(){

    }
    public boolean IsPlayFinish(){
        return true;
    }
    public boolean IsRequireDisCard(){
        return true;
    }
    public void DisCard(){

    }
    public boolean IsSkillInitiate(){
        return true;
    }


}
