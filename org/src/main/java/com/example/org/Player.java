package com.example.org;

import java.net.HttpRetryException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
    public int seatId;
    private Heroes hero;
    private List<Card> handCardList;
    public List<Card> equipCardList;
    public List<Card> judgeCardList;

    public final static int ToDistance=1;//自己对敌方距离
    public final static int FromDistance=1;//敌方对自己距离
    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
    }

    public int hp;
    //    final static int hp= ;设置一个静态变量代表当前血量
    public int hpLimit;//血量上限
    public boolean isUseJiu=false;
    public boolean ifUseGuoHeChaiQiao=false;

    public boolean isIfUseGuoHeChaiQiao() {
        return ifUseGuoHeChaiQiao;
    }

    public void setIfUseGuoHeChaiQiao(boolean ifUseGuoHeChaiQiao) {
        this.ifUseGuoHeChaiQiao = ifUseGuoHeChaiQiao;
    }

    //血量,实时更新
    public void setHp(int CurrentHp) {
        this.hp = CurrentHp;
    }

    public void setHpLimit(Heroes hero) {
        this.hpLimit=hero.getHpLimit();
    }//血量上限为所选武将的血量上限

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

    public void JudgeCardList(Player player) {
        if (player.judgeCardList.size() == 0)
            return;
        else {
            switch (judgeCardList.get(0).getTypeId()) {
                case 11:
                    ;//执行乐不思蜀判定
                case 12:
                    ;//执行兵粮寸断判定
            }
            player.judgeCardList.remove(0);
            JudgeCardList(player);
        }
    }

    public boolean IsAbleToDraw() {
        //若兵粮寸断判定成功则返回false,弹出一张非梅花牌图片及“兵粮寸断生效”字样
        if (Math.random() > 0.75)
            return false;

        else  //弹出一张梅花牌图片及“兵粮寸断失效”字样
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


    public boolean IsAbleToPlay() {
        //若乐不思蜀判定成功则返回false ，弹出一张非红桃牌及“乐不思蜀生效”字样
        if (Math.random() > 0.75)
            return false;

        else  //弹出一张红桃牌及“乐不思蜀失效”字样
            return true;
    }

    public void PlayCard() {
        if (!IsAbleToPlay()) return;
        if (!CheckHandCardList()) return;
            /*do {

            } while (AbandonPlayCard() == true || !CheckHandCardList());*/
    }

    public void UseCard(int typeId) {         //选取手牌区牌并将其typeId作为参数
        for (int i = 0; i < handCardList.size(); i++) {
            if (handCardList.get(i).getTypeId() == typeId) {
                //
            }
        }
    }



    public boolean IsRequireDiscard() {
        return true;
    }

    public void Discard() {
        if (this.getCardsNum() > this.getHp())
            for (int i = 0; i < handCardList.size(); i++)
                handCardList.remove(this.getHp() + i);//暂时设定弃掉超过血量范围的牌，后续设置可选取
        this.setHandCardList(handCardList);//更新当前手牌

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