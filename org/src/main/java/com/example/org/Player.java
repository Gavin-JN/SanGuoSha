package com.example.org;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
    private int heroId;
    private int seatId;
    private List<Card> handCardList;
    private List<Card> equipCardList;
    private List<Card> judgeCardList;
    private int volume;
    private int attackDistance;

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public List<Card> getHandCardList() {
        return handCardList;
    }

    public void setHandCardList(List<Card> handCardList) {
        this.handCardList = handCardList;
    }

    public List<Card> getEquipCardList() {
        return equipCardList;
    }

    public void setEquipCardList(List<Card> equipCardList) {
        this.equipCardList = equipCardList;
    }

    public List<Card> getJudgeCardList() {
        return judgeCardList;
    }

    public void setJudgeCardList(List<Card> judgeCardList) {
        this.judgeCardList = judgeCardList;
    }

    public int getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(int attackDistance) {
        this.attackDistance = attackDistance;
    }

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
        return true;
    }

    //抽牌
    public int  DrawCard(List<Card> cards){   //cards为当前这局游戏的剩余的所有的待摸牌
        Collections.shuffle(cards);  //打乱剩余牌的次序
        if(!cards.isEmpty())
        {
            Card GetCard=cards.remove(0);
            int typeOfCard=GetCard.getTypeId();
            return typeOfCard;  //返回所抽中卡片的类型编号
        }
        return 0;
    }


    public boolean IsAbleToPlay(){
        return true;
    }

    public void PlayCard(){
        if(!IsAbleToPlay()) return;
        if(!CheckHandCardList()) return;
        if(!AbandonPlayCard()) return;

    }
    public boolean IsPlayFinish(){
        return true;
    }
    public boolean IsRequireDiscard(){
        return true;
    }
    public void Discard(){

    }
    public boolean IsSkillInitiate(){
        return true;
    }
    public boolean CheckHandCardList(){
        for(int i=0;i<handCardList.size();i++){
            if(handCardList.get(i).CanInitiative()) return true;
        }
        return false;
    }
    public boolean AbandonPlayCard(){
        return false;
    }
}
