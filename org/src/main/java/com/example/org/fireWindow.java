package com.example.org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class fireWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //己方
        Pane player1Pane=new Pane();
        player1Pane.setPrefSize(1000,150);

        //敌方
        Pane player2Pane=new Pane();

        //对战区域
        Pane gameAreaPane=new Pane();
        //设置根区域的背景图片
        BorderPane root = new BorderPane();
        Image image=new Image(getClass().getResourceAsStream("images/background.jpg"));
        BackgroundImage backgroundImage=new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        Background background=new Background(backgroundImage);
        root.setBackground(background);
        //将分区域添加到根里
        root.setTop(player2Pane);
        root.setBottom(player1Pane);
        root.setCenter(gameAreaPane);
        //设置己方区域的背景图片
        Image image2=new Image(getClass().getResourceAsStream("images/campImage.jpg"));
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
        Pane heroCardPane=new Pane();
        heroCardPane.setPrefSize(100,150);
        Image imageHero=new Image(getClass().getResourceAsStream("images/hero.png"));
        BackgroundImage heroImage=new BackgroundImage(imageHero, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        Background heroBackground=new Background(heroImage);
        heroCardPane.setBackground(heroBackground);
        heroCardPane.setLayoutX(20);
        heroCardPane.setLayoutY(0);
        player1Pane.getChildren().add(heroCardPane);

        Pane equipmentPane=new Pane();
        equipmentPane.setPrefSize(100,150);
        Image imageEquipment=new Image(getClass().getResourceAsStream("images/equipment.png"));
        BackgroundImage equipmentImage=new BackgroundImage(imageEquipment,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        Background equipmentBackground=new Background(equipmentImage);
        equipmentPane.setBackground(equipmentBackground);
        equipmentPane.setLayoutX(180);
        equipmentPane.setLayoutY(0);
        player1Pane.getChildren().add(equipmentPane);

        //for 循环的次数我我方玩家手牌的数量
        //牌的图片应为一个存放URL的String类型的玩家目前手牌的种类的handCardList
        for(int i=0;i<6;i++)
        {
            Pane cardPane=new Pane();
            cardPane.setPrefSize(100,150);
            Image imageCard=new Image(getClass().getResourceAsStream("images/card.png"));
            BackgroundImage cardImage=new BackgroundImage(imageCard,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            Background cardBackground=new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(340+i*110);
            cardPane.setLayoutY(0);
            cardPane.setOnMouseClicked(event -> {
                cardPane.setLayoutY(-20);
            });;
            player1Pane.getChildren().add(cardPane);
        }

        //敌方武将
        Pane heroCardPane2=new Pane();
        heroCardPane2.setPrefSize(100,150);
        Image imageHero2=new Image(getClass().getResourceAsStream("images/hero.png"));
        BackgroundImage heroImage2=new BackgroundImage(imageHero2, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        Background heroBackground2=new Background(heroImage2);
        heroCardPane2.setBackground(heroBackground2);
        heroCardPane2.setLayoutX(300);
        heroCardPane2.setLayoutY(0);
        player2Pane.getChildren().add(heroCardPane2);

        //for循环的次数应是敌方玩家的目前手牌的数量

        for(int i=0;i<6;i++)
        {
            Pane cardPane=new Pane();
            cardPane.setPrefSize(100,150);
            Image imageCard=new Image(getClass().getResourceAsStream("images/cardBack.png"));
            BackgroundSize backgroundSizeCardBack = new BackgroundSize(100, 150, false, false, false, false);
            BackgroundImage cardImage=new BackgroundImage(imageCard,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,backgroundSizeCardBack);
            Background cardBackground=new Background(cardImage);
            cardPane.setBackground(cardBackground);
            cardPane.setLayoutX(450+i*40);
            cardPane.setLayoutY(0);
            player2Pane.getChildren().add(cardPane);
        }

        //设置Scane和Stage的大小
        Scene scene=new Scene(root,1000,700);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(700);
        stage.setResizable(false); //固定窗口的大小
        stage.setScene(scene);
        stage.setTitle("FireWindow");
        stage.show();

    }
}
