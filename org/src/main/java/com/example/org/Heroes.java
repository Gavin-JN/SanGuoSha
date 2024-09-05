package com.example.org;

import java.util.List;

public class Heroes {
    public int hp;
//    final static int hp= ;设置一个静态变量代表当前血量
    public int hpLimit;//血量上限
    //血量
    public void setHp(){
    }//设置当前血量
    public void setHpLimit(int num){
        this.hpLimit=num;
    }//设置血量上限
    boolean IsSkillInitiate;//判断当前玩家技能能否主动使用

    public void setSkillInitiate(boolean isSkillInitiate){
        this.IsSkillInitiate=isSkillInitiate;

    }
    public boolean getSkillInitiate(){
        return IsSkillInitiate;
    }
    public int getHp() {
        return hp;
    }//返回当前血量

    public int getHpLimit() {
        return hpLimit;
    }//返回血量上限


    Player player;    //将武将与玩家绑定

<<<<<<< HEAD
class sunQuan extends Heroes {
    private boolean balanceUsed=false;  // 布尔类型用于记录记录制衡是否已使用,初始化为未使用过制衡
    public sunQuan(){
        this.setSkillInitiate(true);
        this.setHpLimit(4);
    }

    //1、制衡。弃任意牌，摸等量牌，每回合一次
    public void zhiheng(Player player,int discardCards){  //制衡（使用制衡之前要先判断玩家是否还有牌）{
        if (balanceUsed) {
            System.out.println("本回合制衡已经使用过了！");
            return;
        }
        if (discardCards > player.getHandCardList().size()) {
            System.out.println("手牌不足以弃牌！");
            return;
        }

        // 弃牌并摸等量牌
        player.setHandCardList();
        // 摸等量牌  【摸排：从剩余卡堆中获得等量的卡牌数量加入到玩家目前已有的卡牌列表中】
        player.DrawCard();
        balanceUsed = true;
        System.out.println("孙权使用了制衡，弃了" + discardCards + "张牌，摸了" + discardCards + "张牌。");
=======
    public Heroes(Player player) {
        this.player = player;
>>>>>>> 40086650255f8818e99091ae0fcf878691ba904d
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

<<<<<<< HEAD
class  caoCao extends Heroes {
    //1、奸雄。获得对自己造成伤害的牌
    // (当角色收到伤害的时候调用该函数）
    public caoCao(){
        this.setSkillInitiate(false);
        this.setHpLimit(4);
    }
    public void jianXiong(Card card,Player player) {  //card为对对该角色造成伤害的卡牌
        // 获得对自己造成伤害的牌
        player.getHandCardList().add(card);  // 模拟获得一张牌
        System.out.println("曹操发动奸雄，获得了" + card + "牌。");
    }
}

class  zhaoYun extends Heroes {

    public zhaoYun(){
        this.setSkillInitiate(true);
        this.setHpLimit(4);
    }
    //1、龙胆——你可以将你手牌的【杀】当【闪】、【闪】当【杀】使用或打出。
    //(该技能需要角色自己主动选择是否使用）
    public void longDan(String cardType) {
        if (cardType.equals("杀")) {
            System.out.println("赵云将杀当作闪使用。");
        } else if (cardType.equals("闪")) {
            System.out.println("赵云将闪当作杀使用。");
        } else {
            System.out.println("卡牌类型错误。");
        }
    }
}

class zhangFei extends Heroes {
    public zhangFei(){
        this.setSkillInitiate(true);
        this.setHpLimit(4);
    }
    //咆哮——出牌阶段，你可以使用任意数量的【杀】。
    //该技能角色需主动选择使用
    //killCount 魏角色要出杀的数量
    public void paoXiao(int killCount) {
        if (getCards() >= killCount) {   //此处的getCards函数应改为牌中杀的数量
            System.out.println("张飞使用了" + killCount + "张杀。");
            setCards(getCards() - killCount);  // 扣除使用的杀
        } else {
            System.out.println("张飞没有足够的杀牌。");
        }
    }
}

class  zhuGeLiang extends Heroes {
    public zhuGeLiang(){
        this.setSkillInitiate(false);
        this.setHpLimit(3);
    }
  //空城——锁定技，当你没有手牌时，你不能成为【杀】或【决斗】的目标。◆当你在“决斗”过程中没有手牌无法打出杀时，你仍然会受到【决斗】的伤害。
  //返回的布尔类型决定角色是否可以被杀
  public boolean kongCheng(Player player) {
      if (player.getCardsNum() == 0) {
          System.out.println("诸葛亮发动了空城，不能成为杀或决斗的目标。");
          return true;
      }
      return false;
  }
}

class  zhangLiao extends Heroes {
    public zhangLiao(){
        this.setSkillInitiate(true);
        this.setHpLimit(4);
    }
    //突袭——摸牌阶段，你可以放弃摸牌，然后从至多两名（至少一名）角色的手牌里各抽取一张牌。◆摸牌阶段，你一旦发动突袭，就不能从牌堆获得牌；只剩一名其他角色时，你就只能选择这一名角色；若此时其他任何人都没有手牌，你就不能发动突袭。
    public void tuXi(List<Heroes> players) {
        int number=0;
        for (Heroes player : players) {
            if (player.getCards() > 0) {
                player.setCards(player.getCards() - 1);  // 模拟从玩家手中抽一张牌
                setCards(getCards() + 1);
                System.out.println("张辽发动突袭，从对方手中抽了一张牌。");
                number++;
            } else {
                System.out.println("目标玩家没有手牌。");
            }
            if(number==2) {  //至多抽取两名玩家的手牌
                break;
            }
        }
    }
}

class daQiao extends Heroes {
    public daQiao(){
        this.setSkillInitiate(true);
        this.setHpLimit(3);
    }
  //流离——当你成为【杀】的目标时，你可以弃一张牌，并将此【杀】转移给你攻击范围内的另一名角色。（该角色不得是【杀】的使用者）
  //当玩家被杀时，可自主选择是否使用该技能
    public void liuLi(){
        setCards(getCards() -1);
        //要判断是否处于攻击范围内
        //对另一名玩家实施杀

    }
}

class  guoJia  extends Heroes {
    public guoJia(){
        this.setSkillInitiate(false);
        this.setHpLimit(3);
    }
  //遗计——你每受到1点伤害，可摸两张牌
  //当受到伤害时该技能直接发动
  public void yiJi()  //参数为指定给予手牌的两名玩家
  {
   //获得两张牌
  }
}

//
//class sunQuan extends Heroes {
//
//    private boolean balanceUsed=false;  // 布尔类型用于记录记录制衡是否已使用,初始化为未使用过制衡
//
//    public sunQuan(Player player) {
//        super(player);
//    }
//
//    //1、制衡。弃任意牌，摸等量牌，每回合一次
//    public void zhiheng(int discardCards){  //制衡（使用制衡之前要先判断玩家是否还有牌）{
//        if (balanceUsed) {
//            System.out.println("本回合制衡已经使用过了！");
//            return;
//        }
//        if (discardCards > player.getHandCardList().size()) {
//            System.out.println("手牌不足以弃牌！");
//            return;
//        }
//
//        // 弃牌并摸等量牌
//        player.getHandCardList();
//        // 摸等量牌  【摸排：从剩余卡堆中获得等量的卡牌数量加入到玩家目前已有的卡牌列表中】
//        player.DrawCard(,discardCards); //剩余牌的List
//        balanceUsed = true;
//        System.out.println("孙权使用了制衡，弃了" + discardCards + "张牌，摸了" + discardCards + "张牌。");
//    }
//    //回合结束时还将制衡初始
//    public void resetBalance() {
//        balanceUsed = false;
//    }
//}
//
//class  caoCao extends Heroes {
//    public caoCao(Player player) {
//        super(player);
//    }
//
//    //1、奸雄。获得对自己造成伤害的牌
//    // (当角色收到伤害的时候调用该函数）
//    public void jianXiong(Card card,Player player) {  //card为对对该角色造成伤害的卡牌
//        // 获得对自己造成伤害的牌
//        player.getHandCardList().add(card);  // 模拟获得一张牌
//        System.out.println("曹操发动奸雄，获得了" + card + "牌。");
//    }
//}
//
//class  zhaoYun extends Heroes {
//    public zhaoYun(Player player) {
//        super(player);
//    }
//
//    //1、龙胆——你可以将你手牌的【杀】当【闪】、【闪】当【杀】使用或打出。
//    //(该技能需要角色自己主动选择是否使用）
//    public void longDan(String cardType) {
//        if (cardType.equals("杀")) {
//            System.out.println("赵云将杀当作闪使用。");
//        } else if (cardType.equals("闪")) {
//            System.out.println("赵云将闪当作杀使用。");
//        } else {
//            System.out.println("卡牌类型错误。");
//        }
//    }
//}
//
//class zhangFei extends Heroes {
//    public zhangFei(Player player) {
//        super(player);
//    }
//
//    //咆哮——出牌阶段，你可以使用任意数量的【杀】。
//    //该技能角色需主动选择使用
//    //killCount 魏角色要出杀的数量
//    public void paoXiao(int killCount) {
//        if ()
//        {
//
//        } else {
//            System.out.println("张飞没有足够的杀牌。");
//        }
//    }
//}
//
//class  zhuGeLiang extends Heroes {
//    public zhuGeLiang(Player player) {
//        super(player);
//    }
//
//    //空城——锁定技，当你没有手牌时，你不能成为【杀】或【决斗】的目标。◆当你在“决斗”过程中没有手牌无法打出杀时，你仍然会受到【决斗】的伤害。
//  //返回的布尔类型决定角色是否可以被杀
//  public boolean kongCheng() {
//      if (getCards() == 0) {
//          System.out.println("诸葛亮发动了空城，不能成为杀或决斗的目标。");
//          return true;
//      }
//      return false;
//  }
//}
//
//class  zhangLiao extends Heroes {
//    public zhangLiao(Player player) {
//        super(player);
//    }
//
//    //突袭——摸牌阶段，你可以放弃摸牌，然后从至多两名（至少一名）角色的手牌里各抽取一张牌。◆摸牌阶段，你一旦发动突袭，就不能从牌堆获得牌；只剩一名其他角色时，你就只能选择这一名角色；若此时其他任何人都没有手牌，你就不能发动突袭。
//    public void tuXi(List<Heroes> players) {
//        int number=0;
//        for (Heroes player : players) {
//            if (player.getCards() > 0) {
//                player.setCards(player.getCards() - 1);  // 模拟从玩家手中抽一张牌
//                setCards(getCards() + 1);
//                System.out.println("张辽发动突袭，从对方手中抽了一张牌。");
//                number++;
//            } else {
//                System.out.println("目标玩家没有手牌。");
//            }
//            if(number==2) {  //至多抽取两名玩家的手牌
//                break;
//            }
//        }
//    }
//}
//
//class daQiao extends Heroes {
//    public daQiao(Player player) {
//        super(player);
//    }
//
//    //流离——当你成为【杀】的目标时，你可以弃一张牌，并将此【杀】转移给你攻击范围内的另一名角色。（该角色不得是【杀】的使用者）
//  //当玩家被杀时，可自主选择是否使用该技能
//    public void liuLi(){
//        setCards(getCards() -1);
//        //要判断是否处于攻击范围内
//        if(true){
//            //对另一名玩家实施杀
//        }
//    }
//}
//
//class  guoJia  extends Heroes {
//    public guoJia(Player player) {
//        super(player);
//    }
//
//    //遗计——你每受到1点伤害，可摸两张牌，将其中的一张交给任意一名角色，然后将另一张交给任意一名角色。
//  //当受到伤害时该技能主动发动
//  public void yiJi()  //参数为指定给予手牌的两名玩家
//  {
//   //指定两名玩家的手牌+1
//  }
//}
>>>>>>> 40086650255f8818e99091ae0fcf878691ba904d
