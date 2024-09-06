package com.example.org;

import java.net.HttpRetryException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
    public int seatId;
    public List<Card> handCardList;
    public List<Card> equipCardList;
    public List<Card> judgeCardList;
    public Heroes hero;
    public int hp;
    //    final static int hp= ;设置一个静态变量代表当前血量
    public int hpLimit;//血量上限
    public boolean isUseJiu=false;
    public boolean isNextShaAddDamage=false;
    public boolean ifUseGuoHeChaiQiao=false;

    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
    }

    public boolean isIfUseGuoHeChaiQiao() {
        return ifUseGuoHeChaiQiao;
    }

    public void setIfUseGuoHeChaiQiao(boolean ifUseGuoHeChaiQiao) {
        this.ifUseGuoHeChaiQiao = ifUseGuoHeChaiQiao;
    }

    //血量
    public void setHp(int CurrentHp) {
        this.hp = CurrentHp;
    }

    public void setHpLimit() {

    }

    public Room room;

    public int getHp() {
        return hp;
    }

    public int getHpLimit() {
        return hpLimit;
    }

    private int attackDistance;


    public int getCardsNum() {
        return handCardList.size();
    }//当前玩家手牌数量

    public int getShaNum() {
        int number = 0;
        for (int i = 0; i < handCardList.size(); i++) {
            if (handCardList.get(i).getTypeId() == 1) {
                number++;
            }
        }
        return number;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
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

    public void JudgeCardList(Player player) {
        if (player.judgeCardList.size() == 0)
            return;
        else {
            int judge = (int)(Math.random()*4);
            switch (judgeCardList.get(0).getTypeId()) {
                case 11:
                    player.IsAbleToPlay(judge);
                case 12:
                    player.IsAbleToDraw(judge);
            }
            player.judgeCardList.remove(0);
            JudgeCardList(player);
        }
    }

    public boolean IsAbleToDraw(int judge) {
        if(judge==0) return false;
        return true;
    }

    //抽牌
    public int DrawCard(List<Card> cardList) {   //cardList为当前这局游戏的剩余的所有的待摸牌
        Collections.shuffle(cardList);  //打乱剩余牌的次序
        int typeOfCard = 0;
        if (!cardList.isEmpty()) {
            Card GetCard = cardList.remove(0);
            typeOfCard = GetCard.getTypeId();
        } else {
            System.out.println("卡牌数量不足");
        }
        return typeOfCard;
    }


    public boolean IsAbleToPlay(int judge) {
        if(judge==0) return false;
        return true;
    }

    public boolean IsPlayFinish() {
        return true;
    }

    public boolean IsRequireDiscard() {
        return true;
    }

    public boolean IsSkillInitiate(Heroes hero) {

        return true;
    }

    public boolean CheckHandCardList() {
        for (int i = 0; i < handCardList.size(); i++) {
            if (handCardList.get(i).CanInitiative()) return true;
        }
        return false;
    }
}