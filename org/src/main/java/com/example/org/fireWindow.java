package com.example.org;

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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class fireWindow extends Parent {

    private  Pane player1Pane;
    private Pane player2Pane;
    private Pane gameAreaPane;
    private Pane heroCardPane;
    private  Pane equipmentPane;
    private   Pane heroCardPane2;
    private List <Integer> checkedCardFromTarget;
    private List<Integer> checkedCards;
    private BorderPane root;
    private StackPane bloodPone1;
    private StackPane bloodPone2;
    private ProgressBar healthBar1;
    private ProgressBar healthBar2;
    private Label healthLabel1;
    private Label healthLabel2;
    private Pane equipmentContainer;
    private int checkedSeatId;

    public fireWindow(Player player1, Player targetPlayer) {
        //己方
        player1Pane = new Pane();
        player1Pane.setPrefSize(1000, 150);
        //敌方
        player2Pane = new Pane();
        //对战区域
        gameAreaPane = new Pane();
        heroCardPane = new Pane();
        equipmentPane = new Pane();
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
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
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
        equipmentContainer.setPrefSize(100,150);
        equipmentContainer.setLayoutX(180);
        equipmentPane.setPrefSize(100, 150);
        player1Pane.getChildren().add(equipmentContainer);
        Image imageEquipment = new Image(getClass().getResourceAsStream("img/zhuangBei.jpg"));
        BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
        BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
        Background equipmentBackground = new Background(equipmentImage);
        equipmentPane.setBackground(equipmentBackground);
        equipmentPane.setLayoutX(0);
        equipmentPane.setLayoutY(0);
        equipmentContainer.getChildren().add(equipmentPane);

        //己方卡牌区
        Pane cardContainer = new Pane();  //卡牌区域
        cardContainer.setPrefSize(900, 150);
        cardContainer.setLayoutX(340);
        cardContainer.setLayoutY(0);
        player1Pane.getChildren().add(cardContainer);
        for (int i = 0; i < player1.handCardList.size(); i++) {
            Pane cardPane = new Pane();
            cardPane.setPickOnBounds(true); // 确保整个Pane的边界都可以接受点击事件
            cardPane.setPrefSize(100, 150);
            Image imageCard = new Image(getClass().getResourceAsStream(player1.handCardList.get(i).getCardPhotoPath()));
            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage = new BackgroundImage(imageCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCard);
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(0 + i * 110);
            cardPane.setLayoutY(0);

            //限制每次出牌只可以出一张
            AtomicBoolean ifPlayCard=new AtomicBoolean(false);
            //用于判断该pane是否已经被点击过
            AtomicBoolean isClicked = new AtomicBoolean(false);
            AtomicBoolean canHover = new AtomicBoolean(true);
            AtomicBoolean ifUp= new AtomicBoolean(false);
            cardPane.setOnMouseEntered(event -> {
                if(canHover.get()) {
                    cardPane.setLayoutY(-20);
                    ifUp.set(true);
                }
            });
            cardPane.setOnMouseExited(event2 -> {
                if(ifUp.get()) {
                    cardPane.setLayoutY(0);
                    ifUp.set(false);
                }
            });
            int finalI = i;
            Integer finaLi=i;
            //鼠标悬浮以及点击事件
            cardPane.setOnMouseClicked(event -> {
                if(!ifPlayCard.get()) {
                    if (!isClicked.get()) {
                        cardPane.setTranslateY(-38);
                        isClicked.set(true);
                        canHover.set(false);
                        checkedCards.add(finalI);
                        //将玩家出的牌的索引赋给玩家  putId
                        player1.setPutId(finalI);

                    } else {
                        cardPane.setTranslateY(0);
                        isClicked.set(false);
                        checkedCards.remove(finaLi);
                        canHover.set(true);
                        //还将玩家的 putId初始化
                        player1.setPutId(-1);
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
        heroCardPane2.setLayoutX(450);
        heroCardPane2.setLayoutY(0);
        player2Pane.getChildren().add(heroCardPane2);
        player2Pane.setOnMouseClicked(event -> {
                    System.out.println("所选座位号：" + targetPlayer.seatId);
                    checkedSeatId = targetPlayer.seatId;

                    player2Pane.setStyle("-fx-background-color: red;");

                    // 设置延迟，延迟结束后恢复原始背景颜色
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.3)); // 0.3秒延迟//
                    pause.setOnFinished(e -> {
                        player2Pane.setStyle(""); // 恢复原始样式
                    });
                });

        //敌方卡牌信息
        for (int i = 0; i < targetPlayer.handCardList.size(); i++) {    //根据对方玩家的卡牌的数量循环对应的次数
            Pane cardPane = new Pane();
            cardPane.setPrefSize(100, 150);
            Image imageCard = new Image(getClass().getResourceAsStream("img/cardBack.png"));
            BackgroundSize backgroundSizeCard = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage = new BackgroundImage(imageCard, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeCard);
            Background cardBackground = new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(650 + i * 45);
            cardPane.setLayoutY(0);
            player2Pane.getChildren().add(cardPane);

            //要判断玩家是否可以弃对方的牌
            AtomicBoolean isClicked = new AtomicBoolean(false); //用于判断该pane是否已经被点击过
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
        Pane cardContainer2 = new Pane();
        cardContainer2.setPrefSize(400,400);
        cardContainer2.setLayoutX(400);
        cardContainer2.setLayoutY(100);
        gameAreaPane.getChildren().add(cardContainer2);

        //出牌按钮
        Button up = new Button();
        up.setPrefSize(80, 40);
        up.setText("出牌");
        up.setLayoutX(250);
        up.setLayoutY(320);
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
                    switch (cardOut.getTypeId()) {
                        //所出的牌为杀
                        case 1:
                            Sha sha = new Sha(1);
                            //当杀未被闪响应时
                            if (!sha.Resp(targetPlayer, targetPlayer.getPutId())) {

                            }

                            break;
                        //所出的牌为闪
                        case 2:
                            break;
                        //所出的牌为桃
                        case 3:
                            break;
                        //
                        case 4:
                            break;
                        //
                        case 5:
                            break;
                        //
                        case 6:
                            break;
                        //
                        case 7:
                            break;
                        //
                        case 8:
                            break;
                        //
                        case 9:
                            break;
                        //
                        case 10:
                            break;
                        //
                        case 11:
                            break;
                        //
                        case 12:
                            break;
                        //
                        case 13:
                            break;
                        //
                        case 14:
                            break;
                        //
                        case 15:
                            break;
                        //
                        case 16:
                            break;
                        //
                        case 17:
                            break;
                        //
                        case 18:
                            break;
                        //
                        case 19:
                            break;
                        //
                        case 20:
                            break;
                    }

                }
                showCardInArea(cardContainer2, player1, checkedCards); //展示

                for (int i = 0; i < checkedCards.size(); i++)  //删除本地
                {
                    player1.handCardList.remove((int) (checkedCards.get(i)));
                }
                //玩家手牌列表更新之后再展示手牌
                renderPlayerCards(cardContainer, player1);
                checkedCards.clear();

                //更新己方血条
                upDateAllBlood(bloodPone1, player1, healthBar1, healthLabel1);
                //更新敌方血条
                upDateAllBlood(bloodPone2, targetPlayer, healthBar2, healthLabel2);
            }
            else {
                Alert cardoutAlert = new Alert(Alert.AlertType.WARNING);
                cardoutAlert.setTitle("警告");
                cardoutAlert.setHeaderText(null);
                cardoutAlert.setContentText("当前阶段无法出牌！");
                cardoutAlert.showAndWait();
            }

        });

        //结束回合按钮
        Button down = new Button();
        down.setPrefSize(80, 40);
        down.setText("结束回合");
        down.setLayoutX(850);
        down.setLayoutY(320);
        down.backgroundProperty();
        down.setStyle("-fx-background-color: #000fff");
        down.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        down.setOnAction(event -> {

            if(player1.handCardList.size()>player1.getHp())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("警告");
                alert.setHeaderText(null);
                alert.setContentText("您目前的手牌数量仍大于血量无法进入下一回合，请弃牌或出牌！");

                alert.showAndWait();
              System.out.println("手牌数量超过血量无法进入下一回合");
            }

            else {
               player1.room.turn= (player1.room.turn+1)%player1.room.players.size();
            }
            System.out.println("回合结束进入下一回合");
            checkedCards.clear();
        });

        //弃牌按钮
        Button fold = new Button();
        fold.setPrefSize(80, 40);
        fold.setText("决定弃牌");
        fold.setLayoutX(550);
        fold.setLayoutY(320);
        fold.backgroundProperty();
        fold.setStyle("-fx-background-color: #000fff");
        fold.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        fold.setOnAction(event -> {

            //需要判断是弃自己的牌还是其对方的牌，看是否使用了过河拆桥
            if(player1.isIfUseGuoHeChaiQiao())   //使用的是过河拆桥，故此时弃对方玩家的牌
            {
                showCardInArea(cardContainer2,targetPlayer,checkedCardFromTarget); //在gameArea区域展示

                for(int i=0;i<checkedCardFromTarget.size();i++) {
                    targetPlayer.handCardList.remove((int)checkedCardFromTarget.get(i));//删除对方玩家的被选中的手牌
                }
            }
            else {  //不是过河 拆桥，则删除己方玩家的被选中的卡牌

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
        healthLabel1.setTextFill(Color.WHITE);  // 设置标签文字颜色
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
        healthLabel2.setTextFill(Color.WHITE);  // 设置标签文字颜色
        healthLabel2.setStyle("-fx-font-weight: bold;");  // 设置字体粗体
        String Limithp2=String.valueOf(targetPlayer.getHpLimit());
        healthLabel2.setText(Limithp2);
        bloodPone2.getChildren().addAll(healthBar2,healthLabel2);

        //创建武将技能的容器
        Pane skillContainer = new Pane();
        skillContainer.setPrefSize(80,30);
        skillContainer.setLayoutX(30);
        skillContainer.setLayoutY(355);
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
                        sunQuan sun = new sunQuan();
                        //此时该按钮充当出牌
                        //展示要弃的牌
                        showCardInArea(cardContainer2, player1, checkedCards);
                        //弃牌并摸等量牌
                        sun.zhiheng(player1, player1.room.cardList, checkedCards);
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
        Scene scene = new Scene(root, 1250, 700);
        Stage stage = new Stage();
        stage.setMaxWidth(1000);
        stage.setMaxHeight(700);
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
    private void renderPlayerCards(Pane cardContainer, Player player) {
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

            // 添加卡牌的交互事件，如悬浮效果、点击效果等
            cardPane.setOnMouseEntered(event -> cardPane.setLayoutY(-20));
            cardPane.setOnMouseExited(event -> cardPane.setLayoutY(0));

            AtomicBoolean isClicked = new AtomicBoolean(false);
            int finalI = i;
            cardPane.setOnMouseClicked(event -> {
                if (!isClicked.get()) {
                    cardPane.setTranslateY(-38);
                    isClicked.set(true);
                    // 添加选中的卡牌编号
                    checkedCards.add(finalI);
                } else {
                    cardPane.setTranslateY(0);
                    isClicked.set(false);
                    // 移除选中的卡牌编号
                    checkedCards.remove(Integer.valueOf(finalI));
                }
            });
            // 将卡牌添加到卡牌容器中
            cardContainer.getChildren().add(cardPane);
        }
    }

    //将出的牌展示在对战区域
    private void showCardInArea(Pane cardContainer2,Player player,List<Integer> checked)  //仅展示出牌，不删除
    {
            cardContainer2.getChildren().clear();
            for (int i = 0; i < checked.size(); i++) {
                if(player.handCardList.get(checked.get(i)).getTypeId()<15) {
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
                //当出的牌为装备牌时，将装备牌展示在装备牌处
                else {
                    equipmentContainer.getChildren().clear();
                    Image imageEquipment = new Image(getClass().getResourceAsStream(player.handCardList.get(checked.get(i)).getCardPhotoPath()));
                    BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
                    BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
                    Background equipmentBackground = new Background(equipmentImage);
                    equipmentPane.setBackground(equipmentBackground);
                    equipmentPane.setLayoutX(0);
                    equipmentPane.setLayoutY(0);
                    equipmentContainer.getChildren().add(equipmentPane);
                }
            }

    }

    //当玩家的血量发生变化的时候调用此函数更新界面上的血条
    private void updataBlood(StackPane heroCardPhone00,Player player,ProgressBar healthBar,Label healthLabel)   //更新血条长度
    {
        heroCardPhone00.getChildren().clear();
        double MaxBlood=player.getHpLimit();
        double nowBlood=player.getHp();
        healthBar.setProgress(nowBlood/MaxBlood);
        heroCardPhone00.getChildren().addAll(healthBar,healthLabel);
    }

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
}