package com.example.org;

import java.util.Currency;

public class Card {
   private int typeId;
   private int fireDistance;//卡牌的攻击距离
   private String cardPhotoPath;

   public String getCardPhotoPath() {
      return cardPhotoPath;
   }

   public void setCardPhotoPath(String cardPhotoPath) {
      this.cardPhotoPath = cardPhotoPath;
   }

   public int getFireDistance() {
      return fireDistance;
   }

   public void setFireDistance(int fireDistance) {
      this.fireDistance = fireDistance;
   }

   public Card(int typeId) {
      this.typeId = typeId;
   }
   public int getTypeId() {
      return typeId;
   }

   public Card() {
   }
   public boolean CanInitiative(){
      return false;
   }
   public boolean CanPassive(){
      return false;
   }
   public boolean RequireTarget(){
      return false;
   }
   public boolean Resp(Player targetPlayer,int typeId){
      return true;
   }
   public boolean AbandonResp(Player targetPlayer){
      return false;
   }
}

/*所有牌共99张，
基本牌类：杀30张，闪15张，桃8张，酒5张；
锦囊牌类：顺手牵羊5张，过河拆桥6张，无中生有4张，借刀杀人2张，决斗3张，无懈可击3张，
        乐不思蜀3张，兵粮寸断2张，南蛮入侵3张，万箭齐发1张；
装备牌类：诸葛连弩2张，寒冰剑1张，古锭刀1张，青龙偃月刀1张，+1马2张，-1马2张。*/

/*杀：出牌阶段对敌方使用，可造成一点伤害，可在对方决斗，南蛮入侵，借刀杀人时打出，
    一般情况下每回合仅可使用1张，typeId 1 */
class Sha extends Card{
   public Sha(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative(){
      return true;
   }
   public boolean CanPassive(){
      return true;
   }
   public boolean RequireTarget() {
      return true;
   }
   public void Use(Player player,Player targetPlayer){   //9.6 目前仅考虑了是否使用酒对杀的影响
      if(player.isIfUseJiu()) {    //使用酒的时候，杀的伤害是2
         targetPlayer.setHp(targetPlayer.getHp()-2);
      }
      else {  //未使用酒的时候的杀的伤害是1
         targetPlayer.setHp(targetPlayer.getHp()-1);
      }
      //根据攻击距离，限制出杀次数，装备武器效果，是否喝酒，及对手是否响应执行对手血量变化
   }
   public boolean Resp(Player targetPlayer,int typeId){
      if(AbandonResp(targetPlayer)) return false;
      for(int i=0;i<targetPlayer.getHandCardList().size();i++){
         if(targetPlayer.getHandCardList().get(i).getTypeId()==2) return true;
      }
      return false;
   }
   public boolean AbandonResp(Player targetPlayer){
      return true;
   }
}

//闪：可在对方杀，万箭齐发时打出，防止受到伤害， typeId 2
class Shan extends Card{
   public Shan(int typeId) {
      super(typeId);
   }

   public boolean CanPassive(){
      return true;
   }
}

//桃：可在自己回合已受伤时或濒死时使用回复一点体力，typeId 3
class Tao extends Card{
   public Tao(int typeId) {
      super(typeId);
   }
   public void Use(Player player){
      if(player.getHp()<player.getHpLimit()) {//血量+1
         int CurrentHp=player.getHp();
         player.setHp(CurrentHp);
      }
   }

   public boolean CanInitiative(){
      return true;
   }
}

//酒：可在濒死时使用回复一点体力，或出牌阶段使用使下一张杀伤害加1（以此法每回合仅可使用一次）， typeId 4
class Jiu extends Card{
   public Jiu(int typeId) {
      super(typeId);
   }
   public void Use(Player player){
      if(player.getHp()==0) {//濒死回血
         player.setHp(1);
      }
      //杀的伤害+1
      else
      {
         player.setIfUseJiu(true);
      }
   }

   public boolean CanInitiative(){
      return true;
   }
}

//顺手牵羊：可从距离为1的对手处拿走一张牌， typeId 5
class ShunShouQianYang extends Card {
   public ShunShouQianYang(int typeId) {
      super(typeId);
      super.setFireDistance(1);  //攻击距离为1
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }
}

//过河拆桥：可弃置对手区域内一张牌，  typeId 6
class GuoHeChaiQiao extends Card{
   public GuoHeChaiQiao(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }

   public void dicardFromTargetPlayer(Player targetPlayer,int locationCard) {      //location为玩家选中的对方牌在对方牌组里的索引
     // targetPlayer
   }
}

//无中生有：摸两张牌， typeId 7
class WuZhongShengYou extends Card{
   public WuZhongShengYou(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//借刀杀人：若对方装备武器，可指定其向另一人出杀，若不出杀，其将武器交给你， typeId 8
class JieDaoShaRen extends Card{
   public JieDaoShaRen(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }


   public boolean RequireTarget() {
      return true;
   }
}

//决斗：双方轮流出杀直到一人不出杀受到一点伤害， typeId 9
class JueDou extends Card{
   public JueDou(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }
}

//无懈可击：可使敌方打出的锦囊牌无效， typeId 10
class WuXieKeJi extends Card{
   public WuXieKeJi(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean CanPassive() {
      return true;
   }
}

//乐不思蜀：置于敌方判定区，敌方摸牌前进行判定，有75%几率不能出牌， typeId 11
class LeBuSiShu extends Card{
   public LeBuSiShu(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }
}

//兵粮寸断：置于敌方判定区，敌方摸牌前进行判定，有75%几率不能摸牌， typeId 12
class BingLiangCunDuan extends Card{
   public BingLiangCunDuan(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }
}

//南蛮入侵：使用后其余所有角色需打出一张杀相应，否则受到一点伤害， typeId 13
class NanManRuQin extends Card{
   public NanManRuQin(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//万箭齐发：使用后其余所有角色需打出一张闪相应，否则受到一点伤害， typeId 14
class WanJianQiFa extends Card{
   public WanJianQiFa(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//诸葛连弩；攻击距离为1，装备后使用杀无次数限制， typeId 15
class ZhuGeLianNu extends Card{
   public ZhuGeLianNu(int typeId) {
      super(typeId);
      super.setFireDistance(1);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//寒冰剑：攻击距离为2，装备后使用杀对敌方造成伤害时，可防止此伤害，改为弃置敌方2张牌， typeId 16
class HanBingJian extends Card{
   public HanBingJian(int typeId) {
      super(typeId);
      super.setFireDistance(2);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//古锭刀：攻击距离为2，装备后使用杀对敌方造成伤害时，若敌方无手牌，此伤害+1， typeId 17
class GuDingDao extends Card{
   public GuDingDao(int typeId) {
      super(typeId);
      super.setFireDistance(2);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//青龙偃月刀：攻击距离为3，装备后使用杀被闪响应后，可继续出杀， typeId 18
class QingLongYanYueDao extends Card{
   public QingLongYanYueDao(int typeId) {
      super(typeId);
      super.setFireDistance(3);
   }

   public boolean CanInitiative() {
      return true;
   }
}



//加1马：装备后别人计算与你的距离+1，typeId 19
class HorseIncrease1 extends Card{
   public HorseIncrease1(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }
}

//减1马：装备后别人计算与你的距离-1， typeId 20
class HorseDecrease1 extends Card{
   public HorseDecrease1(int typeId) {
      super(typeId);
   }

   public boolean CanInitiative() {
      return true;
   }
}
