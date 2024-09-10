package com.example.org;

import com.example.org.controller.FireWindowController;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class fireWindow extends Parent {

    public  Pane player1Pane;
    public Pane player2Pane;
    public Pane gameAreaPane;
    //己方玩家英雄区
    public Pane heroCardPane;
    //己方玩家装备区
    public  Pane equipmentPane;
    //敌方玩家装备区
    public  Pane equipmentPane2;
    //敌方玩家英雄区
    public   Pane heroCardPane2;
    //选择的敌方玩家手牌索引
    public List <Integer> checkedCardFromTarget;
    //选择的己方玩家的手牌索引
    public List<Integer> checkedCards;
    //根
    public BorderPane root;
    //己方玩家血条
    public StackPane bloodPone1;
    //敌方玩家血条
    public StackPane bloodPone2;
    public ProgressBar healthBar1;
    public ProgressBar healthBar2;
    public Label healthLabel1;
    public Label healthLabel2;
    //己方玩家 仅装备容器
    public Pane equipmentContainer;
    //敌方玩家 仅装备容器
    public Pane equipmentContainer2;
    //敌方玩家座位号
    public int checkedSeatId;
    //仅敌方玩家手牌容器
    public Pane targetContainer;
    //己方玩家手牌容器
    public Pane cardContainer;
    //对战区域卡牌展示容器
    public Pane cardContainer2;

    public boolean IsPlay = false;

    public  AtomicBoolean ifPlayCard;

    public boolean balanceUsed=false;

    public int outCard=-1;
    public FireWindowController fireWindowController = new FireWindowController();


//    private List

    public fireWindow(Player player1, Player targetPlayer) {
        //己方
        player1Pane = new Pane();
        player1Pane.setPrefSize(1000, 150);
        //敌方
        player2Pane = new Pane();
        //对战区域
        gameAreaPane = new Pane();
        heroCardPane = new Pane();
        checkedCardFromTarget =new ArrayList<>(); //选中的对方玩家的牌的列表
        //记录选中卡片的编号
        checkedCards = new ArrayList<Integer>();
        //敌方武将
        heroCardPane2 = new Pane();
        //己方血条区域
        bloodPone1 = new StackPane();
        //敌方血条区域
        bloodPone2 = new StackPane();


        //设置根区域的背景图片
        root = new BorderPane();
        Image image = new Image(getClass().getResourceAsStream("img/background.jpg"));
        BackgroundSize backgroundsize=new BackgroundSize(1300,800,false,true,true,true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundsize);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
        //将分区域添加到根里
        root.setTop(player2Pane);
        root.setBottom(player1Pane);
        root.setCenter(gameAreaPane);
        //设置己方区域的背景图片
        Image image2 = new Image(getClass().getResourceAsStream("img/campImage.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(40, 40, false, false, true, true); // 缩小图片的尺寸
        BackgroundImage backgroundImage2 = new BackgroundImage(
                image2,
                BackgroundRepeat.REPEAT,  // 水平方向重复
                BackgroundRepeat.REPEAT,  // 垂直方向重复
                BackgroundPosition.CENTER,
                backgroundSize);
        Background background2 = new Background(backgroundImage2);
        player1Pane.setBackground(background2);


        //编辑己方区域的内容
        heroCardPane.setPrefSize(100, 150);
        Image imageHero = new Image(getClass().getResourceAsStream(player1.getHero().getHeroPhotoPath()));
        BackgroundSize backgroundSizeHero = new BackgroundSize(100, 150, false, false, false, false);
        BackgroundImage heroImage = new BackgroundImage(imageHero, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeHero);
        Background heroBackground = new Background(heroImage);
        heroCardPane.setBackground(heroBackground);
        heroCardPane.setLayoutX(20);
        heroCardPane.setLayoutY(0);
        player1Pane.getChildren().add(heroCardPane);
        //己方装备

        equipmentContainer = new Pane();
        equipmentContainer.setPrefSize(300,150);
        equipmentContainer.setLayoutX(150);
        player1Pane.getChildren().add(equipmentContainer);
        for(int i=0;i<player1.equipCardList.length;i++) {
            equipmentPane = new Pane();
            equipmentPane.setPrefSize(100, 150);
            Image imageEquipment = new Image(getClass().getResourceAsStream(player1.equipCardList[i].getCardPhotoPath()));
            BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
            Background equipmentBackground = new Background(equipmentImage);
            equipmentPane.setBackground(equipmentBackground);
            equipmentPane.setLayoutX(0+i*35);
            equipmentPane.setLayoutY(0);
            equipmentContainer.getChildren().add(equipmentPane);
        }

        //限制每次出牌只可以出一张
        ifPlayCard=new AtomicBoolean(false);//己方卡牌区

        cardContainer = new Pane();  //卡牌区域
        cardContainer.setPrefSize(900, 150);
        cardContainer.setLayoutX(340);
        cardContainer.setLayoutY(0);
        player1Pane.getChildren().add(cardContainer);

        List <Pane>cardpane=new ArrayList<>();

        for (int i = 0; i < player1.handCardList.size(); i++) {
            Pane cardPane = new Pane();
            cardPane.setPickOnBounds(true); // 确保整个Pane的边界都可以接受点击事件
            cardPane.setPrefSize(100, 150);
            cardPane.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            Image imageCard = new Image(getClass().getResourceAsStream(player1.handCardList.get(i).getCardPhotoPath()));
            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage = new BackgroundImage(imageCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCard);
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(0 + i * 110);
            cardPane.setLayoutY(0);
            //用于判断该pane是否已经被点击过
            AtomicBoolean isClicked = new AtomicBoolean(false);
            int finalI = i;
            Integer finaLi=i;
            //鼠标点击事件
            cardPane.setOnMouseClicked(event -> {
                if(!ifPlayCard.get()) {
                    if (!isClicked.get()) {
                        cardPane.setTranslateY(-38);
                        isClicked.set(true);
                        checkedCards.add(finalI);
                        //将玩家出的牌的索引赋给玩家  putId
                        player1.setPutId(finalI);

                        player1.chooseCard=finalI;

                        ifPlayCard.set(true);
                        outCard=player1.handCardList.get(player1.putId).getTypeId();

                        if(outCard==1)
                        {
                            if(player1.canUseSha)
                            {
                            }
                            else {
                                cardPane.setTranslateY(0);
                                isClicked.set(false);
                                checkedCards.remove(finaLi);
                                //还将玩家的 putId初始化
                                player1.setPutId(-1);
                                ifPlayCard.set(false);
                                outCard=-1;
                            }
                        }

                    } else {
                        cardPane.setTranslateY(0);
                        isClicked.set(false);
                        checkedCards.remove(finaLi);
                        //还将玩家的 putId初始化
                        player1.setPutId(-1);
                        ifPlayCard.set(false);
                        outCard=-1;


                    }
                }
                //被点击后标记事件，即该张牌可能会出
            });
            cardContainer.getChildren().add(cardPane);
        }

        //敌方武将照片
        heroCardPane2.setPrefSize(100, 150);
        Image imageHero2 = new Image(getClass().getResourceAsStream(targetPlayer.getHero().getHeroPhotoPath()));
        BackgroundSize backgroundSizeHero2 = new BackgroundSize(100, 150, false, false, false, false);//根据玩家抽中的武将，上传对应的图片
        BackgroundImage heroImage2 = new BackgroundImage(imageHero2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeHero2);
        Background heroBackground2 = new Background(heroImage2);
        heroCardPane2.setBackground(heroBackground2);
        heroCardPane2.setLayoutX(300);
        heroCardPane2.setLayoutY(0);
        player2Pane.getChildren().add(heroCardPane2);
        heroCardPane2.setOnMouseClicked(event -> {
                    System.out.println("所选座位号：" + targetPlayer.seatId);
                    checkedSeatId = targetPlayer.seatId;
                });

        //敌方
        equipmentContainer2 = new Pane();
        equipmentContainer2.setPrefSize(100,150);
        equipmentContainer2.setLayoutX(450);
        player2Pane.getChildren().add(equipmentContainer2);

        for(int i=0;i<targetPlayer.equipCardList.length;i++) {
            equipmentPane2 = new Pane();
            equipmentPane2.setPrefSize(100, 150);
            Image imageEquipment2 = new Image(getClass().getResourceAsStream(targetPlayer.equipCardList[i].getCardPhotoPath()));
            BackgroundSize backgroundSizeEquipment2 = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage equipmentImage2 = new BackgroundImage(imageEquipment2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment2);
            Background equipmentBackground2 = new Background(equipmentImage2);
            equipmentPane2.setBackground(equipmentBackground2);
            equipmentPane2.setLayoutX(0+i*35);
            equipmentPane2.setLayoutY(0);
            equipmentContainer2.getChildren().add(equipmentPane2);
        }


        //敌方卡牌信息
        targetContainer=new Pane();
        targetContainer.setPrefSize(400, 150);
        targetContainer.setLayoutX(700);
        targetContainer.setLayoutY(0);
        player2Pane.getChildren().add(targetContainer);
        for (int i = 0; i < targetPlayer.handCardList.size(); i++) {    //根据对方玩家的卡牌的数量循环对应的次数
            Pane cardPane = new Pane();
            cardPane.setPrefSize(100, 150);
            Image imageCard = new Image(getClass().getResourceAsStream("img/cardBack.png"));
            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage = new BackgroundImage(imageCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCard);
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(0 + i * 45);
            cardPane.setLayoutY(0);
            targetContainer.getChildren().add(cardPane);

            //用于判断该pane是否已经被点击过
            AtomicBoolean isClicked = new AtomicBoolean(false);
            int finalI = i;
            Integer FinaLi=i;
            cardPane.setOnMouseClicked(event -> {
                if (!isClicked.get()) {
                    cardPane.setTranslateY(30);
                    isClicked.set(true);
                    checkedCardFromTarget.add(finalI);
                } else {
                    cardPane.setTranslateY(0);
                    isClicked.set(false);
                    checkedCardFromTarget.remove(FinaLi);
                }
            });
        }

       //在gameArea区域展示牌
        cardContainer2 = new Pane();
        cardContainer2.setPrefSize(400,400);
        cardContainer2.setLayoutX(400);
        cardContainer2.setLayoutY(100);
        gameAreaPane.getChildren().add(cardContainer2);

        //出牌按钮
        Button up = new Button();
        up.setPrefSize(80, 40);
        up.setText("出牌");
        Image buttonBackgroundImage = new Image(getClass().getResourceAsStream("img/buttonBackground.jpg"));
        BackgroundSize buttonBackgroundSize = new BackgroundSize(80, 40, false, false, false, false);
        BackgroundImage backgroundImg = new BackgroundImage(buttonBackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, buttonBackgroundSize);
        up.setBackground(new Background(backgroundImg));
        up.setLayoutX(300);
        up.setLayoutY(420);
        up.backgroundProperty();
        up.setStyle("-fx-background-color: #000fff");
        up.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        up.setOnAction(event -> {
            System.out.println("决定出牌");
            //将checkedCard 中编号的卡牌在玩家目前已有的卡牌列表中 先展示在对战区域，之后再从玩家的卡牌列表中remove
            //player属性中putId为玩家出的牌在自己手牌列表中的索引
            //执行所处的牌的作用
            if((player1.room.status== Room.roomStatus.PlayStatus&&player1.seatId==player1.room.turn)||
                    (player1.room.status== Room.roomStatus.RespStatus&&player1.seatId==player1.room.respPlayers.get(0).seatId)) {

                for (int i = 0; i < checkedCards.size(); i++) {
                    Card cardOut = player1.handCardList.get(checkedCards.get(i));
                    boolean ab;

                    switch (cardOut.getTypeId()) {
                        //所出的牌为杀
                        case 1:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            else {
                                player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));
                                cardOut.AbandonResp(targetPlayer);
                            }
                            break;
                        case 3:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithoutTarget(player1, checkedCards.get(0));
                            break;
                        //
                        case 4:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithoutTarget(player1, checkedCards.get(0));
                            break;
                        //
                        case 5:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));
//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab= cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 6:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            player1.ifUseGuoHeChaiQiao=true;
                            break;
                        //
                        case 7:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithoutTarget(player1, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(player1);
                            break;
                        //
                        case 8:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 9:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 11:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 12:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithTarget(player1, targetPlayer.seatId, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 13:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithoutTarget(player1,  checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        //
                        case 14:
                            if (!cardOut.Use(player1, checkedCards.get(0))) break;
                            player1.room.receiver.PlayCardWithoutTarget(player1, checkedCards.get(0));

//                            cardOut.setwxkjResp(targetPlayer);
//                            targetPlayer.room.receiver.PlayCardWithoutTarget(targetPlayer, targetPlayer.chooseCard);
                            ab = cardOut.Abandon(targetPlayer);
                            break;
                        case 15:
                            cardOut.Use(player1,checkedCards.get(0));
                            break;
                        case 16:
                            cardOut.Use(player1,checkedCards.get(0));
                            break;
                        case 17:
                            cardOut.Use(player1,checkedCards.get(0));
                            break;
                        case 18:
                            cardOut.Use(player1,checkedCards.get(0));
                            break;
                        case 19:
                            cardOut.Use(player1,checkedCards.get(0));
                            break;
                        //加装备
                        default:
                            break;
                    }
                }
                //在gameArea区域展示
                if(player1.handCardList.get(checkedCards.get(0)).getTypeId()<15) {
                    showCardInArea(cardContainer2, player1, checkedCards);
                }
                else {
                    showEquipment(equipmentContainer,player1);
                }



                for(int i=0;i<checkedCards.size();i++) {
                    player1.handCardList.remove((int)(checkedCards.get(i)));
                }
                //玩家手牌列表更新之后再展示手牌
                renderPlayerCards(cardContainer, player1);
                checkedCards.clear();
                //更新地方卡牌区域
                updateTarget(targetContainer,targetPlayer);

                if(targetPlayer.getHp()<0)
                {
                    Alert outOfUp=new Alert(Alert.AlertType.WARNING);
                    outOfUp.setContentText("游戏结束！");
                    outOfUp.showAndWait();
                    return;
                }


                //更新己方血条
                upDateAllBlood(bloodPone1, player1, healthBar1, healthLabel1);
                //更新敌方血条
                upDateAllBlood(bloodPone2, targetPlayer, healthBar2, healthLabel2);
                //更新ifPlayCard
                ifPlayCard.set(false);
            }

            else {
                Alert cardoutAlert = new Alert(Alert.AlertType.WARNING);
                cardoutAlert.setTitle("警告");
                cardoutAlert.setHeaderText(null);
                cardoutAlert.setContentText("当前阶段无法出牌！");
                cardoutAlert.showAndWait();
                ifPlayCard.set(false);
                checkedCards.clear();
            }

            IsPlay = true;
            fireWindowController.SendMessage(IsPlay,outCard);
        });

        //结束回合按钮
        Button down = new Button();
        down.setPrefSize(80, 40);
        down.setText("取消");
        down.setBackground(new Background(backgroundImg));
        down.setLayoutX(900);
        down.setLayoutY(420);
        down.backgroundProperty();
        down.setStyle("-fx-background-color: #000fff");
        down.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        down.setOnAction(event -> {
            if(player1.room.status== Room.roomStatus.PlayStatus&&player1.room.turn==player1.seatId)
                player1.room.update();
            if(player1.room.status== Room.roomStatus.RespStatus&&player1.seatId==player1.room.respPlayers.get(0).getSeatId())
                player1.room.currentCard.AbandonResp(player1);


            System.out.println("status:::"+player1.room.status);
        });

        //弃牌按钮
        Button fold = new Button();
        fold.setPrefSize(80, 40);
        fold.setText("决定弃牌");
        fold.setBackground(new Background(backgroundImg));
        fold.setLayoutX(600);
        fold.setLayoutY(420);
        fold.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        fold.setOnAction(event -> {

            //需要判断是弃自己的牌还是其对方的牌，看是否使用了过河拆桥
            if(player1.isIfUseGuoHeChaiQiao())   //使用的是过河拆桥，故此时弃对方玩家的牌
            {
                //此时show出去的牌不分装备牌还是...
                showCardInArea(cardContainer2,targetPlayer,checkedCardFromTarget); //在gameArea区域展示

                for(int i=0;i<checkedCardFromTarget.size();i++) {
                    targetPlayer.handCardList.remove((int)checkedCardFromTarget.get(i));//删除对方玩家的被选中的手牌
                }
                updateTarget(targetContainer,targetPlayer);
                player1.setIfUseGuoHeChaiQiao(false);
            }
            else {  //不是过河 拆桥，则删除己方玩家的被选中的卡牌

                //此时show出去的牌不分装备牌还是...
                showCardInArea(cardContainer2,player1,checkedCards);
                for(int i=0;i<checkedCards.size();i++) {
                    player1.handCardList.remove((int)(checkedCards.get(i)));
                }

                renderPlayerCards(cardContainer,player1);

            }

            System.out.println("决定弃牌");
            //将checkedCard列表中的编号的卡牌从玩家目前已有的卡牌中remove
            checkedCardFromTarget.clear();
            checkedCards.clear();

        });

        gameAreaPane.getChildren().add(up);
        gameAreaPane.getChildren().add(down);
        gameAreaPane.getChildren().add(fold);

        bloodPone1.setPrefSize(100,20);
        heroCardPane.getChildren().add(bloodPone1);
        //设置血条(己方)
        healthBar1 = new ProgressBar();
        healthBar1.setProgress(1.0);  // 初始为满血
        healthBar1.setStyle("-fx-accent: red;");  // 设置血条颜色为红色
        healthBar1.setPrefWidth(100);  // 设置血条的宽度
        healthBar1.setLayoutX(0);
        //文字标签
        healthLabel1 = new Label();
        healthLabel1.setTextFill(Color.BLACK);  // 设置标签文字颜色
        healthLabel1.setStyle("-fx-font-weight: bold;");  // 设置字体粗体
        String Limithp1=String.valueOf(player1.getHpLimit());
        healthLabel1.setText(Limithp1);
        bloodPone1.getChildren().addAll(healthBar1,healthLabel1);


        bloodPone2.setPrefSize(100,20);
        heroCardPane2.getChildren().add(bloodPone2);
        //设置血条（敌方）
        healthBar2 = new ProgressBar();
        healthBar2.setProgress(1.0);  // 初始为满血
        healthBar2.setStyle("-fx-accent: red;");  // 设置血条颜色为红色
        healthBar2.setPrefWidth(100);  // 设置血条的宽度
        healthBar2.setLayoutX(0);

        healthLabel2 = new Label();
        healthLabel2.setTextFill(Color.BLACK);  // 设置标签文字颜色
        healthLabel2.setStyle("-fx-font-weight: bold;");  // 设置字体粗体
        String Limithp2=String.valueOf(targetPlayer.getHpLimit());
        healthLabel2.setText(Limithp2);
        bloodPone2.getChildren().addAll(healthBar2,healthLabel2);

        //创建武将技能的容器
        Pane skillContainer = new Pane();
        skillContainer.setPrefSize(80,30);
        skillContainer.setLayoutX(30);
        skillContainer.setLayoutY(420);
        gameAreaPane.getChildren().add(skillContainer);
        Button skillButton = new Button();
        skillButton.setPrefSize(80,30);
        skillContainer.getChildren().add(skillButton);
        int heroId=player1.getHero().getHeroId();
        switch (heroId)
        {
            case 1:skillButton.setText("制衡");
            break;
            case 2:skillButton.setText("奸雄");
            break;
            case 3:skillButton.setText("龙胆");
            break;
            case 4:skillButton.setText("咆哮");
            break;
            case 5:skillButton.setText("空城");
            break;
            case 6:skillButton.setText("突袭");
            break;
            case 7:skillButton.setText("遗技");
            break;
        }
        //点击技能按钮事件
        skillButton.setOnAction(event -> {
            switch (heroId)
            {
                case 1:
                    if(checkedCards.size()!=0) {
//                        sunQuan sun = new sunQuan();
                        //此时该按钮充当出牌
                        //展示要弃的牌
                        showCardInArea(cardContainer2, player1, checkedCards);
                        //弃牌并摸等量牌
                        int discardCards=checkedCards.size();
                        if (balanceUsed) {
                            System.out.println("本回合制衡已经使用过了！");
                            return;
                        }
                        if (discardCards > player1.handCardList.size()) {
                            System.out.println("手牌不足以弃牌！");
                            return;
                        }

                        for(int i=0;i<checkedCards.size();i++) {
                            player1.handCardList.remove((int)checkedCards.get(i));
                        }

                        for(int i=0;i<discardCards;i++) {   //抽取与弃牌数量相同的新牌
                            int typeOfCard=player1.DrawCard(player1.room.cardList);  //cardList为待抽取的剩余的所有卡牌
                            Card cardIn=player1.getCardByType(typeOfCard);
                            player1.handCardList.add(cardIn);
                        }
                        balanceUsed = true;
                        checkedCards.clear();

                        System.out.println("孙权使用了制衡，弃了" + discardCards + "张牌，摸了" + discardCards + "张牌。");

                        //更新玩家手牌区域
                        renderPlayerCards(cardContainer, player1);
                    }
                    else {
                        Alert sunquanAlert = new Alert(Alert.AlertType.WARNING);
                        sunquanAlert.setTitle("警告");
                        sunquanAlert.setHeaderText(null);
                        sunquanAlert.setContentText("（孙权）请选择要弃的卡牌，之后获得等量的新牌");
                        sunquanAlert.showAndWait();

                    }
                    break;
                case 2:
                    Alert skillAlert = new Alert(Alert.AlertType.WARNING);
                    skillAlert.setTitle("警告");
                    skillAlert.setHeaderText(null);
                    skillAlert.setContentText("（曹操）此技能为被动技能，无需点击！");
                    skillAlert.showAndWait();
                    //被动，当收到伤害的时候自动调用
                    break;
                case 3:
                    //此时此按钮依然充当出牌
                    if(checkedCards.size()!=0) {
                        zhaoYun zhaoyun = new zhaoYun();
                        int chengedTypeId = zhaoyun.longDan(player1.getPutId());
                        for (int i = 0; i < checkedCards.size(); i++)  //删除本地
                        {
                            player1.handCardList.remove((int) (checkedCards.get(i)));
                        }

                        //将出的牌给为对应牌
                        player1.setPutId(chengedTypeId);
                        checkedCards.clear();
                        checkedCards.add(chengedTypeId);
                        showCardInArea(cardContainer2, player1, checkedCards);

                        checkedCards.clear();
                    }
                    else
                    {
                        Alert zhaoyunAlert = new Alert(Alert.AlertType.WARNING);
                        zhaoyunAlert.setTitle("警告");
                        zhaoyunAlert.setHeaderText(null);
                        zhaoyunAlert.setContentText("（赵云）请选择“杀”或者“闪” ！");
                        zhaoyunAlert.showAndWait();
                    }
                    break;
                case 4:
                    Alert skillAlert2 = new Alert(Alert.AlertType.WARNING);
                    skillAlert2.setTitle("警告");
                    skillAlert2.setHeaderText(null);
                    skillAlert2.setContentText("（张飞）此技能为被动技能，无需点击！");
                    skillAlert2.showAndWait();
                    //张飞被动，解除玩家的出杀的限制
                    break;
                case 5:
                    Alert skillAlert3 = new Alert(Alert.AlertType.WARNING);
                    skillAlert3.setTitle("警告");
                    skillAlert3.setHeaderText(null);
                    skillAlert3.setContentText("（诸葛亮）此技能为被动技能，无需点击！");
                    skillAlert3.showAndWait();
                    //诸葛亮被动，当玩家手牌数量为0的时候不可以成为杀或者决斗的对象，直接在杀和决斗的卡牌属性里面写
                    break;
                case 6:
//                    //可以放在摸排的程序，
//                    zhangLiao zhangliao=new zhangLiao();
//                    zhangliao.tuXi(player1,targetPlayer);
//                    //更新玩家自己的手牌列表
//                    renderPlayerCards(cardContainer,player1);
//                    break;
                case 7:
                    Alert skillAlert4 = new Alert(Alert.AlertType.WARNING);
                    skillAlert4.setTitle("警告");
                    skillAlert4.setHeaderText(null);
                    skillAlert4.setContentText("（郭嘉）此技能为被动技能，无需点击！");
                    skillAlert4.showAndWait();
                    //被动技能，当玩家血量减1时，给玩家分配两张牌
                    break;
            }
        });

        //设置Scane和Stage的大小
        Scene scene = new Scene(root, 1300, 800);
        Stage stage = new Stage();
        stage.setMaxWidth(1300);
        stage.setMaxHeight(800);
        stage.setResizable(false); //固定窗口的大小
        stage.setScene(scene);
        InputStream in = this.getClass().getResourceAsStream("img/title.png");
        Image icon = new Image(in);
        stage.getIcons().add(icon);
        stage.setTitle("FireWindow");

        // 添加背景音乐
        Sound p=new Sound();
        stage.show();
    }


    //更新己方玩家的卡牌区域
    public void renderPlayerCards(Pane cardContainer, Player player) {
        // 清空卡牌容器
        cardContainer.getChildren().clear();

        // 遍历玩家的手牌列表
        for (int i = 0; i < player.handCardList.size(); i++) {
            Pane cardPane = new Pane();
            cardPane.setPrefSize(100, 150);

            // 从手牌列表中获取卡牌图片路径
            String cardPhotoPath = player.handCardList.get(i).getCardPhotoPath();
            Image imageCard = new Image(getClass().getResourceAsStream(cardPhotoPath));

            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            // 创建卡牌背景图
            BackgroundImage cardImage = new BackgroundImage(
                    imageCard,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSizeCard
            );
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);

            // 设置卡牌的布局位置（水平排列）
            cardPane.setLayoutX(0 + i * 110);
            cardPane.setLayoutY(0);


            AtomicBoolean isClicked = new AtomicBoolean(false);
            int finalI = i;
            cardPane.setOnMouseClicked(event -> {
                if (!isClicked.get()) {
                    cardPane.setTranslateY(-38);
                    isClicked.set(true);
                    // 添加选中的卡牌编号
                    checkedCards.add(finalI);
                    //检查outCard是否成功获得当前打出手牌
                    outCard=player.handCardList.get(finalI).getTypeId();
                    System.out.println("outCard为："+outCard);

                    if(outCard==1)
                    {
                        if(player.canUseSha)
                        {
                        }
                        else {
                            cardPane.setTranslateY(0);
                            isClicked.set(false);
                            checkedCards.remove(finalI);
                            //还将玩家的 putId初始化
                            player.setPutId(-1);
                            ifPlayCard.set(false);
                            outCard=-1;

                            Alert shaAlert = new Alert(Alert.AlertType.WARNING);
                            shaAlert.setContentText("您已无法再次出“杀！");
                            shaAlert.showAndWait();

                        }
                    }
                } else {
                    cardPane.setTranslateY(0);
                    isClicked.set(false);
                    // 移除选中的卡牌编号
                    checkedCards.remove(Integer.valueOf(finalI));
                    System.out.println("outCars有问题");
                }
            });
            // 将卡牌添加到卡牌容器中
            cardContainer.getChildren().add(cardPane);
        }
    }

    //将出的牌展示在对战区域,非装备牌（出牌按钮）/所有选中（弃牌区）
    public void showCardInArea(Pane cardContainer2,Player player,List<Integer> checked)  //仅展示出牌，不删除
    {
            cardContainer2.getChildren().clear();
            for (int i = 0; i < checked.size(); i++) {
                    Pane showCardPane = new Pane();
                    showCardPane.setPrefSize(100, 150);
                    Image imageShowCard = new Image(getClass().getResourceAsStream(player.handCardList.get(checked.get(i)).getCardPhotoPath()));
                    BackgroundSize backgroundSizeCardBack = new BackgroundSize(100, 150, false, false, false, false);
                    BackgroundImage showCardImage = new BackgroundImage(imageShowCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCardBack);
                    Background showCardBackground = new Background(showCardImage);
                    showCardPane.setBackground(showCardBackground);
                    showCardPane.setLayoutX(0 + i * 30);
                    showCardPane.setLayoutY(0);
                    cardContainer2.getChildren().add(showCardPane);

            }

    }

    //当玩家的血量发生变化的时候调用此函数更新界面上的血条
    public void updataBlood(StackPane heroCardPhone00,Player player,ProgressBar healthBar,Label healthLabel)   //更新血条长度
    {
        heroCardPhone00.getChildren().clear();
        double MaxBlood=player.getHpLimit();
        double nowBlood=player.getHp();
        healthBar.setProgress(nowBlood/MaxBlood);
        heroCardPhone00.getChildren().addAll(healthBar,healthLabel);
    }

    //刷新血量
    public void upDateAllBlood(StackPane pane,Player player,ProgressBar healthBar,Label healthLabel)
    {
        double limit=player.getHpLimit();
        int now=(int)player.getHp();
        String nowHp=String.valueOf(now);
        //更新血条内的文字提示
        healthLabel.setText(nowHp);
        //更新血条图像
        updataBlood(pane,player,healthBar,healthLabel);
    }

    //刷新敌方玩家手牌区域
    public  void updateTarget(Pane targetContainer,Player targetPlayer)
    {
        targetContainer.getChildren().clear();
        for (int i = 0; i < targetPlayer.handCardList.size(); i++) {    //根据对方玩家的卡牌的数量循环对应的次数
            Pane cardPane = new Pane();
            cardPane.setPrefSize(100, 150);
            Image imageCard = new Image(getClass().getResourceAsStream("img/cardBack.png"));
            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage = new BackgroundImage(imageCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCard);
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(0 + i * 45);
            cardPane.setLayoutY(0);
            targetContainer.getChildren().add(cardPane);

            //用于判断该pane是否已经被点击过
            AtomicBoolean isClicked = new AtomicBoolean(false);
            int finalI = i;
            Integer FinaLi=i;
            cardPane.setOnMouseClicked(event -> {
                if (!isClicked.get()) {
                    cardPane.setTranslateY(30);
                    isClicked.set(true);
                    checkedCardFromTarget.add(finalI);
                } else {
                    cardPane.setTranslateY(0);
                    isClicked.set(false);
                    checkedCardFromTarget.remove(FinaLi);
                }
            });
        }
    }

    //在gameArea区域展示牌，参数为一张Card
    public void showByCard(Pane cardContainer2,Player player,Card card)  //对战区展示一张牌
    {
        cardContainer2.getChildren().clear();
            if(card.getTypeId()<15) {
                Pane showCardPane = new Pane();
                showCardPane.setPrefSize(100, 150);
                Image imageShowCard = new Image(getClass().getResourceAsStream(card.getCardPhotoPath()));
                BackgroundSize backgroundSizeCardBack = new BackgroundSize(100, 150, false, false, false, false);
                BackgroundImage showCardImage = new BackgroundImage(imageShowCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCardBack);
                Background showCardBackground = new Background(showCardImage);
                showCardPane.setBackground(showCardBackground);
                showCardPane.setLayoutY(0);
                cardContainer2.getChildren().add(showCardPane);
            }
            //当出的牌为装备牌时，将装备牌展示在装备牌处
            else {
                equipmentContainer.getChildren().clear();
                Image imageEquipment = new Image(getClass().getResourceAsStream(card.getCardPhotoPath()));
                BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
                BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
                Background equipmentBackground = new Background(equipmentImage);
                equipmentPane.setBackground(equipmentBackground);
                equipmentPane.setLayoutX(0);
                equipmentPane.setLayoutY(0);
                equipmentContainer.getChildren().add(equipmentPane);
            }
        }

    //更新装备区
    public void showEquipment(Pane equipmentContainer,Player player)
    {
        equipmentContainer.getChildren().clear();
        for(int i=0;i<player.equipCardList.length;i++) {
            equipmentPane = new Pane();
            equipmentPane.setPrefSize(100, 150);
            Image imageEquipment = new Image(getClass().getResourceAsStream(player.equipCardList[i].getCardPhotoPath()));
            BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
            Background equipmentBackground = new Background(equipmentImage);
            equipmentPane.setBackground(equipmentBackground);
            equipmentPane.setLayoutX(0+i*35);
            equipmentPane.setLayoutY(0);
            equipmentContainer.getChildren().add(equipmentPane);


            System.out.println("hsadgjhgcjhs"+player.equipCardList.length);
        }
    }

}