package com.example.org;

import java.util.Currency;
import java.util.List;

public class Card {
   private int typeId;
   private String cardPhotoPath;

   public String getCardPhotoPath() {
      return cardPhotoPath;
   }

   public void setCardPhotoPath(String cardPhotoPath) {
      this.cardPhotoPath = cardPhotoPath;
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
   public boolean Use(Player player,int id){
      return true;
   }

   public boolean Resp(Player targetPlayer,int id){
      return true;
   }
   public boolean AbandonResp(Player targetPlayer){
      return false;
   }
   public void setResp(Player player){

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
      super.setCardPhotoPath("controller/img/ShouPai/Sha.webp");
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
   public void Use(Player player,Player targetPlayer){
      //根据攻击距离，限制出杀次数，装备武器效果执行杀的效果
   }
   public boolean Resp(Player targetPlayer,int id){
      if(AbandonResp(targetPlayer)) return false;
      if(targetPlayer.handCardList.get(id).getTypeId()==2) return true;
      return false;
   }
   public boolean AbandonResp(Player targetPlayer){
      int damage;
      if(targetPlayer.room.getPlayerBySeatId(targetPlayer.room.turn).isUseJiu
              &&targetPlayer.room.getPlayerBySeatId(targetPlayer.room.turn).isNextShaAddDamage) {
         damage = 2;
         targetPlayer.room.getPlayerBySeatId(targetPlayer.room.turn).isNextShaAddDamage=false;
      }
      else damage = 1;
      targetPlayer.hp-=damage;
      if(targetPlayer.hp<=0){
         List<Player> playerList = targetPlayer.room.players;
         boolean add = false;
         targetPlayer.room.helpPlayers.clear();
         for(int i=0; targetPlayer.room.helpPlayers.size()<playerList.size();i++){
               i%=targetPlayer.room.players.size();
               if(playerList.get(i)==targetPlayer) add=true;
               if(add) targetPlayer.room.helpPlayers.add(playerList.get(i));
         }
      }
      return targetPlayer.hp<=0;
   }
}

//闪：可在对方杀，万箭齐发时打出，防止受到伤害， typeId 2
class Shan extends Card{
   public Shan(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/Shan.webp");
   }

   public boolean CanPassive(){
      return true;
   }
}

//桃：可在自己回合已受伤时或濒死时使用回复一点体力，typeId 3
class Tao extends Card{
   public Tao(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/Tao.jpg");
   }
   public boolean Use(Player player,int id) {
      Card card = player.handCardList.get(id);
      if (card == null || card.getTypeId() != 3) return false;
      if (player.room.dyingPlayer == null || player.hp == player.hpLimit) return false;
      if (player.room.dyingPlayer != null) {
         player.room.dyingPlayer.hp++;
         player.handCardList.remove(id);
         if(player.room.dyingPlayer.hp<=0) player.room.GameOver(player.room);
         else{
            player.room.helpPlayers.clear();
            player.room.dyingPlayer=null;
         }
      }
      else if(player.hp<player.hpLimit){
         player.hp++;
      }
      return true;
   }

   public boolean CanInitiative(){
      return true;
   }
}

//酒：可在濒死时使用回复一点体力，或出牌阶段使用使下一张杀伤害加1（以此法每回合仅可使用一次）， typeId 4
class Jiu extends Card{
   public Jiu(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/Jiu.png");
   }
   public boolean Use(Player player,int id) {
      if (player.hp <= 0) {//濒死回血
         player.hp++;
         player.handCardList.remove(id);
      }
      //杀的伤害+1
      if (player.room.status == Room.roomStatus.PlayStatus && player.seatId==player.room.turn && player.isUseJiu==false) {
         player.isUseJiu = true;
         player.isNextShaAddDamage = true;
         player.handCardList.remove(id);
      }
      return true;
   }
   public boolean CanInitiative(){
      return true;
   }
}

//顺手牵羊：可从距离为1的对手处拿走一张牌， typeId 5
class ShunShouQianYang extends Card {
   public ShunShouQianYang(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/ShunShouQianYang.webp");
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
      super.setCardPhotoPath("controller/img/ShouPai/GuoHeChaiQiao.png");
   }

   public boolean CanInitiative() {
      return true;
   }

   public boolean RequireTarget() {
      return true;
   }

   public void discardFromTargetPlayer(Player targetPlayer,int locationCard) {      //location为玩家选中的对方牌在对方牌组里的索引
     // targetPlayer
   }
}

//无中生有：摸两张牌， typeId 7
class WuZhongShengYou extends Card{
   public WuZhongShengYou(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/WuZhongShengYou.webp");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//借刀杀人：若对方装备武器，可指定其向另一人出杀，若不出杀，其将武器交给你， typeId 8
class JieDaoShaRen extends Card{
   public JieDaoShaRen(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/JieDaoShaRen.png");
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
      super.setCardPhotoPath("controller/img/ShouPai/JueDou.png");
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
      super.setCardPhotoPath("controller/img/ShouPai/WuXieKeJi.webp");
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
      super.setCardPhotoPath("controller/img/ShouPai/LeBuSIShu.jpg");
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
      super.setCardPhotoPath("controller/img/ShouPai/bingLiangCunDuan.png");
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
      super.setCardPhotoPath("controller/img/ShouPai/NanManRuQin.webp");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//万箭齐发：使用后其余所有角色需打出一张闪相应，否则受到一点伤害， typeId 14
class WanJianQiFa extends Card{
   public WanJianQiFa(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/WanJianQiFa.webp");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//诸葛连弩；攻击距离为1，装备后使用杀无次数限制， typeId 15
class ZhuGeLianNu extends Card{
   public ZhuGeLianNu(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/ZhuGeLianNu.webp");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//寒冰剑：攻击距离为2，装备后使用杀对敌方造成伤害时，可防止此伤害，改为弃置敌方2张牌， typeId 16
class HanBingJian extends Card{
   public HanBingJian(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/HanBingJian.png");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//古锭刀：攻击距离为2，装备后使用杀对敌方造成伤害时，若敌方无手牌，此伤害+1， typeId 17
class GuDingDao extends Card{
   public GuDingDao(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/GuDingDao.png");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//青龙偃月刀：攻击距离为3，装备后使用杀被闪响应后，可继续出杀， typeId 18
class QingLongYanYueDao extends Card{
   public QingLongYanYueDao(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/QingLongYanYueDao.jpg");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//加1马：装备后别人计算与你的距离+1，typeId 19
class HorseIncrease1 extends Card{
   public HorseIncrease1(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/HorseOfIncrease.png");
   }

   public boolean CanInitiative() {
      return true;
   }
}

//减1马：装备后别人计算与你的距离-1， typeId 20
class HorseDecrease1 extends Card{
   public HorseDecrease1(int typeId) {
      super(typeId);
      super.setCardPhotoPath("controller/img/ShouPai/HorseOfReduce.png");
   }

   public boolean CanInitiative() {
      return true;
   }
}
