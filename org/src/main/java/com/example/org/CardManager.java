package com.example.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardManager {
    static List<Card> cardsPile = new ArrayList<>();
    static Map<Integer,Card> cardsMap = new HashMap<>();
    public static void init(){
        Card card = new Sha(1);
        cardsMap.put(card.getTypeId(),card);

        card = new Shan(2);
        cardsMap.put(card.getTypeId(),card);

        card = new Tao(3);
        cardsMap.put(card.getTypeId(),card);

        card = new Jiu(4);
        cardsMap.put(card.getTypeId(),card);

        card = new ShunShouQianYang(5);
        cardsMap.put(card.getTypeId(),card);

        card = new GuoHeChaiQiao(6);
        cardsMap.put(card.getTypeId(),card);

        card = new WuZhongShengYou(7);
        cardsMap.put(card.getTypeId(),card);

        card = new JieDaoShaRen(8);
        cardsMap.put(card.getTypeId(),card);

        card = new JueDou(9);
        cardsMap.put(card.getTypeId(),card);

        card = new WuXieKeJi(10);
        cardsMap.put(card.getTypeId(),card);

        card = new LeBuSiShu(11);
        cardsMap.put(card.getTypeId(),card);

        card = new BingLiangCunDuan(12);
        cardsMap.put(card.getTypeId(),card);

        card = new NanManRuQin(13);
        cardsMap.put(card.getTypeId(),card);

        card = new WanJianQiFa(14);
        cardsMap.put(card.getTypeId(),card);

        card = new ZhuGeLianNu(15);
        cardsMap.put(card.getTypeId(),card);

        card = new HanBingJian(16);
        cardsMap.put(card.getTypeId(),card);

        card = new GuDingDao(17);
        cardsMap.put(card.getTypeId(),card);

        card = new QingLongYanYueDao(18);
        cardsMap.put(card.getTypeId(),card);

        card = new ZhangBaSheMao(19);
        cardsMap.put(card.getTypeId(),card);

        card = new HorseIncrease1(20);
        cardsMap.put(card.getTypeId(),card);

        card = new HorseDecrease1(21);
        cardsMap.put(card.getTypeId(),card);

        CreateCardList();
    }
    public static void CreateCardList(){
        for(int i=0;i<30;i++){
            cardsPile.add(new Card(1));
        }
        for(int i=0;i<15;i++){
            cardsPile.add(new Card(2));
        }
        for(int i=0;i<8;i++){
            cardsPile.add(new Card(3));
        }
        for(int i=0;i<5;i++){
            cardsPile.add(new Card(4));
        }
        for(int i=0;i<5;i++){
            cardsPile.add(new Card(5));
        }
        for(int i=0;i<6;i++){
            cardsPile.add(new Card(6));
        }
        for(int i=0;i<4;i++){
            cardsPile.add(new Card(7));
        }
        for(int i=0;i<2;i++){
            cardsPile.add(new Card(8));
        }
        for(int i=0;i<3;i++){
            cardsPile.add(new Card(9));
        }
        for(int i=0;i<3;i++){
            cardsPile.add(new Card(10));
        }
        for(int i=0;i<3;i++){
            cardsPile.add(new Card(11));
        }
        for(int i=0;i<2;i++){
            cardsPile.add(new Card(12));
        }
        for(int i=0;i<3;i++){
            cardsPile.add(new Card(13));
        }
        for(int i=0;i<1;i++){
            cardsPile.add(new Card(14));
        }
        for(int i=0;i<2;i++){
            cardsPile.add(new Card(15));
        }
        cardsPile.add(new Card(16));
        cardsPile.add(new Card(17));
        cardsPile.add(new Card(18));
        cardsPile.add(new Card(19));
        for(int i=0;i<2;i++){
            cardsPile.add(new Card(20));
        }
        for(int i=0;i<2;i++){
            cardsPile.add(new Card(21));
        }

    }

}
