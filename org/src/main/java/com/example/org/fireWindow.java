package com.example.org;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        equipmentPane.setPrefSize(100, 150);
        Image imageEquipment = new Image(getClass().getResourceAsStream("img/equipment.png"));
        BackgroundSize backgroundSizeEquipment = new BackgroundSize(100, 150, false, false, false, false);
        BackgroundImage equipmentImage = new BackgroundImage(imageEquipment, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSizeEquipment);
        Background equipmentBackground = new Background(equipmentImage);
        equipmentPane.setBackground(equipmentBackground);
        equipmentPane.setLayoutX(180);
        equipmentPane.setLayoutY(0);
        player1Pane.getChildren().add(equipmentPane);

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

            AtomicBoolean isClicked = new AtomicBoolean(false); //用于判断该pane是否已经被点击过
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
                if(!isClicked.get()) {
                    cardPane.setTranslateY(-38);
                    isClicked.set(true);
                    canHover.set(false);

                    System.out.println(finalI);

                    checkedCards.add(finalI);
                }
                else {
                    cardPane.setTranslateY(0);
                    isClicked.set(false);
                    checkedCards.remove(finaLi);
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
            showCardInArea(cardContainer2,player1,checkedCards); //展示
            //执行所处的牌的作用
            for(int i =0;i<checkedCards.size();i++){
                Card cardOut=player1.handCardList.get(checkedCards.get(i));
                switch (cardOut.getTypeId())
                {
                    //所出的牌为杀
                    case 1:
                        
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


            for(int i=0;i<checkedCards.size();i++)  //删除本地
            {
                player1.handCardList.remove((int)(checkedCards.get(i)));
            }
            //玩家手牌列表更新之后再展示手牌
            renderPlayerCards(cardContainer,player1);
            checkedCards.clear();
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
//                for(int i=0;i<checkedCardFromTarget.size();i++) {   //将弃的卡牌展示在对战区域
//                    Pane showCardPane = new Pane();
//                    showCardPane.setPrefSize(100, 150);
//                    Image imageShowCard = new Image(getClass().getResourceAsStream(targetPlayer.handCardList.get(checkedCardFromTarget.get(i)).getCardPhotoPath()));
//                    BackgroundSize backgroundSizeCardBack = new BackgroundSize(100, 150, false, false, false, false);
//                    BackgroundImage showCardImage=new BackgroundImage(imageShowCard,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSizeCardBack);
//                    Background showCardBackground=new Background(showCardImage);
//                    showCardPane.setBackground(showCardBackground);
//                    showCardPane.setLayoutX(400+i*40);
//                    showCardPane.setLayoutY(100);
//                    gameAreaPane.getChildren().add(showCardPane);
//                }
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

        //返回按钮
        Button back = new Button();
        back.setPrefSize(100, 60);
        back.setText("返回");
        back.setLayoutX(0);
        back.setLayoutY(0);
        back.backgroundProperty();
        back.setStyle("-fx-background-color: #000fff");
        back.setStyle("-fx-border-radius: 8px; -fx-background-radius: 8px;");
        back.setOnAction(event -> {

            System.out.println("返回开始页面");
            checkedCards.clear();

        });

        player2Pane.getChildren().add(back);
        gameAreaPane.getChildren().add(up);
        gameAreaPane.getChildren().add(down);
        gameAreaPane.getChildren().add(fold);

        //设置血条(己方)
        ProgressBar healthBar1 = new ProgressBar();
        healthBar1.setProgress(1.0);  // 初始为满血
        healthBar1.setStyle("-fx-accent: red;");  // 设置血条颜色为红色
        healthBar1.setPrefWidth(100);  // 设置血条的宽度
        healthBar1.setLayoutX(0);
        heroCardPane.getChildren().add(healthBar1);

        //设置血条（敌方）
        ProgressBar healthBar2 = new ProgressBar();
        healthBar2.setProgress(1.0);  // 初始为满血
        healthBar2.setStyle("-fx-accent: red;");  // 设置血条颜色为红色
        healthBar2.setPrefWidth(100);  // 设置血条的宽度
        healthBar2.setLayoutX(0);
        healthBar2.setProgress(80.0/100);
        heroCardPane2.getChildren().addAll(healthBar2);

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
      for(int i=0;i<checked.size();i++)
      {
          Pane showCardPane = new Pane();
          showCardPane.setPrefSize(100, 150);
          Image imageShowCard = new Image(getClass().getResourceAsStream(player.handCardList.get(checked.get(i)).getCardPhotoPath()));
          BackgroundSize backgroundSizeCardBack = new BackgroundSize(100, 150, false, false, false, false);
          BackgroundImage showCardImage=new BackgroundImage(imageShowCard,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSizeCardBack);
          Background showCardBackground=new Background(showCardImage);
          showCardPane.setBackground(showCardBackground);
          showCardPane.setLayoutX(0+i*30);
          showCardPane.setLayoutY(0);
          cardContainer2.getChildren().add(showCardPane);
      }

    }

    //当玩家的血量发生变化的时候调用此函数更新界面上的血条
    private void updataBlood(Pane heroCardPhone00,Player player,ProgressBar healthBar)   //更新血条长度
    {
        heroCardPhone00.getChildren().clear();
        double MaxBlood=player.getHpLimit();
        double nowBlood=player.getHp();
        healthBar.setProgress(nowBlood/MaxBlood);
        heroCardPhone00.getChildren().add(healthBar);
    }
}