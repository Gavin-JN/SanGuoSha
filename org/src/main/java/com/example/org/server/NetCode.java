package com.example.org.server;

//用代码来表示不同的客户端和服务端的行为
public enum NetCode {
    Non(0),
    UserRegister(1000),         // 注册
    UserLogin(1001),            // 登录
    Matching(1002),             // 开始匹配
    MatchResp(1003),            // 匹配成功后的响应


    AllPlayerInfo(1100),        // 通知 所有客户端,在场玩家的信息,以及自己的身份
    MainSel(1101),              // 通知主公 选择武将
    OtherSel(1102),             // 通知其他人 选择武将,并且通知所有人,主公的武将
    PlayerHeroInfo(1103),       // 通知客户端 所有的玩家选择的武将


    Deal(1104),                 // 发牌
    CardNumInfo(1105),          // 通知客户的,牌堆数量 以及 其他玩家手牌
    AwaitShowCard(1106),        // 通知所有人 等待某个玩家出牌
    ShowCard(1107),             // 玩家出牌协议


    ShowCardNonPlayer(1500),    // 玩家出一张没有目标的牌
    ShowCardHavePlayer(1501),   // 出一张有目标的牌

    ShowCardSha(1600),          // 通知客户端,某个玩家出了一张杀
    RespShowCardInfo(1601),     // 服务器通知客户端,某个玩家的响应信息
    Abandon(1602),              // 玩家放弃一次 出牌/响应 的操作
    HpUpdate(1603),             // 当某个玩家的hp改变
    Help(1604),                 // 有人死亡,通知某个玩家 出药  救人
    NoticeResp(1605),           // 服务器 通知客户端,某个玩家响应(就是收到 杀 等需要响应的牌了)
    Discard(1606),              // 通知客户端,某个玩家将牌扔到 出牌区,不造成特殊效果的牌都可以用这个;
    ;

    int code;

    NetCode(int code){
        this.code = code;
    }
    public int getCode(){
        return code;
    }

    //通过code获取对应的枚举值
    public static NetCode getType(int code){
        //返回包含所有NetCode常量的数组
        NetCode[] values = NetCode.values();
        for (int i = 0; i < values.length; i++) {
            if(code == values[i].code){
                return values[i];
            }
        }
        return Non;
    }
}
