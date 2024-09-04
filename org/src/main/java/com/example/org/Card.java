package com.example.org;

public class Card {
   private int id;
   private int typeId;

   public Card(int id, int typeId) {
      this.id = id;
      this.typeId = typeId;
   }

   public Card(int typeId) {
      this.typeId = typeId;
   }

   public Card() {
   }
}

/*所有牌共100张，
基本牌类：杀30张，闪15张，桃8张，酒5张；
锦囊牌类：顺手牵羊5张，过河拆桥6张，无中生有4张，借刀杀人2张，决斗3张，无懈可击3张，
        乐不思蜀3张，兵粮寸断2张，南蛮入侵3张，万箭齐发1张；
装备牌类：诸葛连弩2张，寒冰剑1张，古锭刀1张，青龙偃月刀1张，丈八蛇矛1张，+1马2张，-1马2张。*/

/*杀：出牌阶段对敌方使用，可造成一点伤害，可在对方决斗，南蛮入侵，借刀杀人时打出，
    一般情况下每回合仅可使用1张，id 0~29 ，typeId 1 */
class Sha extends Card{
   private int times;
   public Sha(int typeId) {
      super(typeId);
   }
   public boolean IsShaInitiate(){
      if(CurrentRound==seatId){
         if(CurrentStatus==PlayStatus){
            if(IsAbleToPlay&&times>0){
               return true;
            }
         }
      }
      else return false;
   }

   public boolean IsAttackValid(int seatId){
      int seatDistance = seatId-cur.seatId;

         boolean isValid = false;
         if(seatDistance>0){
            if(player equiped HorseIncrease1){
               if(me equiped HorseDecrease1){
                  if(toolsAttackDistance>=seatDistance)
                     isValid = true;
               }
               if(me not equiped HorseDecrease1){
                  if(toolsAttackDistance>=seatDistance+1)
                     isValid = true;
               }
            }
            if(seatDistance<0){
               if (player not equiped HorseIncrease1){
                  if (me equiped HorseDecrease1){
                     if (toolsAttackDistance >=seatDistance*(-1)-1)
                           isValid = true;
                  }
                  if (me not equiped HorseDecrease1){
                     if (toolsAttackDistance >=seatDistance*(-1) + 1)
                        isValid = true;
                  }
               }
            }
         }
         return isValid
   }
   public void UseSha(int seatId){
      if(IsAttackValid(seatId)); //choose whether to use;
      if()
   }

   public boolean IsRespondByShan(){
      return true;
   }
   public boolean IsHit(){
      return true;
   }
}

//闪：可在对方杀，万箭齐发时打出，防止受到伤害，id 30~44 ， typeId 2
class Shan extends Card{

}

//桃：可在自己回合已受伤时或濒死时使用回复一点体力，id 45~52 ，typeId 3
class Tao extends Card{

}

//酒：可在濒死时使用回复一点体力，或出牌阶段使用使下一张杀伤害加1（以此法每回合仅可使用一次），id 53~57 ， typeId 4
class Jiu extends Card{

}

//顺手牵羊：可从距离为1的对手处拿走一张牌，id 58~62 ， typeId 5
class ShunShouQianYang extends Card{

}

//过河拆桥：可弃置对手区域内一张牌， id 63~68 ， typeId 6
class GuoHeChaiQiao extends Card{

}

//无中生有：摸两张牌，id 69~72 ， typeId 7
class WuZhongShengYou extends Card{

}

//借刀杀人：若对方装备武器，可指定其向另一人出杀，若不出杀，其将武器交给你，id 73~74 ， typeId 8
class JieDaoShaRen extends Card{

}

//决斗：双方轮流出杀直到一人不出杀受到一点伤害，id 75~77 ， typeId 9
class JueDou extends Card{

}

//无懈可击：可使敌方打出的锦囊牌无效，id 78~80 ， typeId 10
class WuXieKeJi extends Card{

}

//乐不思蜀：置于敌方判定区，敌方摸牌前进行判定，有75%几率不能出牌，id 81~83 ， typeId 11
class LeBuSiShu extends Card{

}

//兵粮寸断：置于敌方判定区，敌方摸牌前进行判定，有75%几率不能摸牌，id 84~85 ， typeId 12
class BingLiangCunDuan extends Card{

}

//南蛮入侵：使用后其余所有角色需打出一张杀相应，否则受到一点伤害，id 86~88 ， typeId 13
class NanManRuQin extends Card{

}

//万箭齐发：使用后其余所有角色需打出一张闪相应，否则受到一点伤害，id 89 ， typeId 14
class WanJianQiFa extends Card{

}

//诸葛连弩；攻击距离为1，装备后使用杀无次数限制，id 90~91 ， typeId 15
class ZhuGeLianNu extends Card{

}

//寒冰剑：攻击距离为2，装备后使用杀对敌方造成伤害时，可防止此伤害，改为弃置敌方2张牌，id 92 ， typeId 16
class HanBingJian extends Card{

}

//古锭刀：攻击距离为2，装备后使用杀对敌方造成伤害时，若敌方无手牌，此伤害+1，id 93 ， typeId 17
class GuDingDao extends Card{

}

//青龙偃月刀：攻击距离为3，装备后使用杀被闪响应后，可继续出杀，id 94 ， typeId 18
class QingLongYanYueDao extends Card{

}

//丈八蛇矛：攻击距离为3，装备后可将任意两张牌当作杀使用或打出，id 95 ， typeId 19
class ZhangBaSheMao extends Card{

}

//加1马：装备后别人计算与你的距离+1，id 96~97 ， typeId 20
class HorseIncrease1 extends Card{

}

//减1马：装备后别人计算与你的距离-1，id 98~99 ， typeId 21
class HorseDecrease1 extends Card{

}
