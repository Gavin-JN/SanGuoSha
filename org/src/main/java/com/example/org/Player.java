package com.example.org;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player {
    public int seatId;

    public List<Card> handCardList;
    public List<Card> equipCardList;
    public boolean judgeCardList[] = new boolean[2];
    public Heroes hero;
    public int hp;
    //    final static int hp= ;设置一个静态变量代表当前血量
    public int hpLimit;//血量上限
    public boolean isUseJiu=false;
    public boolean isNextShaAddDamage=false;
    public boolean ifUseShunShouQianYang =false;
    public boolean ifUseGuoHeChaiQiao=false;

    public Room room;
    public int attackDistance;
    public boolean Wxkj = false;
    public int buffStatus=0;
    public boolean isAbleToPlay=true;
    public boolean isAbleToDraw=true;

    //构造器（与析构器）
    public Player() {
        this.handCardList = new ArrayList<>();
    }
    public Player(Heroes hero) {
        this.hero = hero;
        this.handCardList=new ArrayList<Card>();
    }


    //set和get方法
    public void setHero(Heroes hero) {
        this.hero = hero;
    }
    public Heroes getHero() {
        return hero;
    }
    public void setIfUseGuoHeChaiQiao(boolean ifUseGuoHeChaiQiao) {this.ifUseGuoHeChaiQiao = ifUseGuoHeChaiQiao;}
    public boolean isIfUseGuoHeChaiQiao() {
        return ifUseGuoHeChaiQiao;
    }
    public void setHp(int CurrentHp) {
        this.hp = CurrentHp;
    }
    public int getHp() {
        return hp;
    }
    public void setHpLimit() {this.hpLimit = this.getHero().getHpLimit();}
    public int getHpLimit() {
        return hpLimit;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {return seatId;}
    public void setAttackDistance(int attackDistance) {this.attackDistance = attackDistance;}
    public int getAttackDistance() {return attackDistance;}


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
        public void Discard() {
        }

        public  Card getCardByType(int typeId) {
        switch (typeId)
        {
            case 1:
                return new Sha(1);
            case 2:
                return new Shan(2);
            case 3:
                return new Tao(3);
            case 4:
                return new Jiu(4);
            case 5:
                return new ShunShouQianYang(5);
            case 6:
                return new GuoHeChaiQiao(6);
            case 7:
                return new WuZhongShengYou(7);
            case 8:
                return new JieDaoShaRen(8);
            case 9:
                return new JueDou(9);
            case 10:
                return new WuXieKeJi(10);
            case 11:
                return new LeBuSiShu(11);
            case 12:
                return new BingLiangCunDuan(12);
            case 13:
                return new NanManRuQin(13);
            case 14:
                return new WanJianQiFa(14);
            case 15:
                return new ZhuGeLianNu(15);
            case 16:
                return new HanBingJian(16);
            case 17:
                return new GuDingDao(17);
            case 18:
                return new QingLongYanYueDao(18);
            case 19:
                return new HorseIncrease1(19);
            case 20:
                return new HorseDecrease1(20);
        }
        return new Card();
        }
    }