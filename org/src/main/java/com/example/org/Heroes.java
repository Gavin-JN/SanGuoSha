package com.example.org;

import java.util.List;

public class Heroes {

    //血量上限
    public int hpLimit;
    //将武将与玩家绑定
    public Player player;
    //武将图片资源路径
    private String heroPhotoPath;
    //判断当前玩家技能能否主动使用
    boolean IsSkillInitiate;
    //设置武将Id
    private  int heroId;
    //构造器（与析构器）
    public Heroes() {}

    public Heroes(int hpLimit, String heroPhotoPath) {
        this.hpLimit = hpLimit;
        this.heroPhotoPath = heroPhotoPath;
    }

    public int getHeroId() {return heroId;}

    public void setHeroId(int heroId) {this.heroId = heroId;}

    public void setHpLimit(int num) {
        this.hpLimit = num;
    }

    public int getHpLimit() {
        return hpLimit;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setHeroPhotoPath(String heroPhotoPath) {
        this.heroPhotoPath = heroPhotoPath;
    }

    public String getHeroPhotoPath() {
        return heroPhotoPath;
    }

    public void setSkillInitiate(boolean isSkillInitiate) {
        this.IsSkillInitiate = isSkillInitiate;

    }

    public boolean getSkillInitiate() {
        return IsSkillInitiate;
    }
}
class sunQuan extends Heroes {
        private boolean balanceUsed = false;  // 布尔类型用于记录记录制衡是否已使用,初始化为未使用过制衡
        public sunQuan() {
            this.setSkillInitiate(true);
            this.setHpLimit(4);
            this.setHeroPhotoPath("controller/img/heroPic/sunQuan.jpg");
            this.setHeroId(1);
        }

        //1、制衡。弃任意牌，摸等量牌，每回合一次
        public void zhiheng( List <Card> cardList,List<Integer> checkedCard) {//制衡（使用制衡之前要先判断玩家是否还有牌）
            int discardCards=checkedCard.size();
            if (balanceUsed) {
                System.out.println("本回合制衡已经使用过了！");
                return;
            }
            if (discardCards > player.handCardList.size()) {
                System.out.println("手牌不足以弃牌！");
                return;
            }
            for(int i=0;i<checkedCard.size();i++) {
                player.handCardList.remove(checkedCard.get(i));
            }
          for(int i=0;i<discardCards;i++) {   //抽取与弃牌数量相同的新牌
              int typeOfCard=player.DrawCard(cardList);  //cardList为待抽取的剩余的所有卡牌
              Card cardIn=player.getCardByType(typeOfCard);
              player.handCardList.add(cardIn);
          }
            balanceUsed = true;
            System.out.println("孙权使用了制衡，弃了" + discardCards + "张牌，摸了" + discardCards + "张牌。");
        }
}

class caoCao extends Heroes {
        //1、奸雄。获得对自己造成伤害的牌
        // (当角色收到伤害的时候调用该函数）
        public caoCao() {
            this.setSkillInitiate(false);
            this.setHpLimit(4);
            this.setHeroPhotoPath("controller/img/heroPic/caocao.jpg");
            this.setHeroId(2);
        }

        public void jianXiong(Card card, Player player) {  //card为对对该角色造成伤害的卡牌
            // 获得对自己造成伤害的牌
            player.handCardList.add(card);  // 模拟获得一张牌
            System.out.println("曹操发动奸雄，获得了" + card + "牌。");
        }
    }
class zhaoYun extends Heroes {

    public zhaoYun() {
        this.setSkillInitiate(true);
        this.setHpLimit(4);
        this.setHeroPhotoPath("controller/img/heroPic/zhaoYun.jpg");
        this.setHeroId(3);
    }


    //1、龙胆——你可以将你手牌的【杀】当【闪】、【闪】当【杀】使用或打出。
    //(该技能需要角色自己主动选择是否使用）
    public void longDan(int cardType) {
        if (cardType == 1) {
            System.out.println("赵云将杀当作闪使用。");
            //需传递某种信号
        } else if (cardType == 2) {
            System.out.println("赵云将闪当作杀使用。");
        } else {
            System.out.println("卡牌类型错误。");
        }
    }
}

    class zhangFei extends Heroes {
        public zhangFei() {
            this.setSkillInitiate(true);
            this.setHpLimit(4);
            this.setHeroPhotoPath("controller/img/heroPic/zhangFei.jpg");
            this.setHeroId(4);
        }

        //咆哮——出牌阶段，你可以使用任意数量的【杀】。
        //该技能角色需主动选择使用
        public void paoXiao(Player player) {
            //解除对该玩家出杀的数量的限制

        }
    }

    class zhuGeLiang extends Heroes {
        public zhuGeLiang() {
            this.setSkillInitiate(false);
            this.setHpLimit(3);
            this.setHeroPhotoPath("controller/img/heroPic/zhuGeLiang.jpg");
            this.setHeroId(5);
        }

        //空城——锁定技，当你没有手牌时，你不能成为【杀】或【决斗】的目标。◆当你在“决斗”过程中没有手牌无法打出杀时，你仍然会受到【决斗】的伤害。
        //返回的布尔类型决定角色是否可以被杀
        public boolean kongCheng(Player player) {
            if (player.getCardsNum() == 0) {

                //  player.
                System.out.println("诸葛亮发动了空城，不能成为杀或决斗的目标。");
                return true;
            }
            return false;
        }
    }

    class zhangLiao extends Heroes {
        public zhangLiao() {
            this.setSkillInitiate(true);
            this.setHpLimit(4);
            this.setHeroPhotoPath("controller/img/heroPic/zhangliao.jpg");
            this.setHeroId(6);
        }

        //突袭——摸牌阶段，你可以放弃摸牌，然后从至多两名（至少一名）角色的手牌里各抽取一张牌。◆摸牌阶段，你一旦发动突袭，就不能从牌堆获得牌；只剩一名其他角色时，你就只能选择这一名角色；若此时其他任何人都没有手牌，你就不能发动突袭。
        public void yuXi(Player targetPlayer)
        {
            if(player.room.getStatus()== Room.roomStatus.DrawStatus) {
                //放弃摸牌
               int typeofCard=player.DrawCard(player.handCardList);
               player.handCardList.add(player.getCardByType(typeofCard));
            }
        }
    }

    class daQiao extends Heroes {
        public daQiao() {
            this.setSkillInitiate(true);
            this.setHpLimit(3);
            this.setHeroPhotoPath("controller/img/heroPic/daQiao.jpg");
            this.setHeroId(7);
        }

        //流离——当你成为【杀】的目标时，你可以弃一张牌，并将此【杀】转移给你攻击范围内的另一名角色。（该角色不得是【杀】的使用者）
        //当玩家被杀时，可自主选择是否使用该技能
        public void liuLi(Player targetPlayer) {
//            if()    //条件为被杀的时候
//            {
//                //弃一张牌
//                //把杀指向对方（对敌方杀）
//            }

        }
    }

    class guoJia extends Heroes {
        public guoJia() {
            this.setSkillInitiate(false);
            this.setHpLimit(3);
            this.setHeroPhotoPath("controller/img/heroPic/guoJia.jpg");
            this.setHeroId(8);
        }

        //遗计——你每受到1点伤害，可摸两张牌
        //当受到伤害时该技能直接发动
        public void yiJi(Player player,List <Card> cardList) {
            //if里面应为条件及受到一点伤害
//            if()
//            {
//                for(int i=0;i<2;i++) {   //抽取与弃牌数量相同的新牌
//                    int typeOfCard=player.DrawCard(cardList);  //cardList为待抽取的剩余的所有卡牌
//                    Card cardIn=player.getCardByType(typeOfCard);
//                    player.handCardList.add(cardIn);
//                }
            }
        }

