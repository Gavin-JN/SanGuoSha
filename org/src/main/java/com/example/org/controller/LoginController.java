package com.example.org.controller;

import com.example.org.UserDao.impl.SQLExecImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField NameOfLogin;
    @FXML
    private PasswordField PasswordOfLogin;
    @FXML
    private Label prompt;

    @FXML
    public void Exit(ActionEvent event){
        System.exit(0);

    }

    @FXML
    public void Play(ActionEvent event) throws ClassNotFoundException, SQLException {
        //将信息传入服务器当中
        //比较数据
        //成功之后进入游戏
        //不成功就提示错误
//        SQLExecImpl sqlExec=new SQLExecImpl();
//        boolean isValid=sqlExec.queryAccount(NameOfLogin.getText(),PasswordOfLogin.getText());

        if(NameOfLogin.getText().equals("") || PasswordOfLogin.getText().equals("")){
            prompt.setText("用户名或者密码不能为空！！");
//        } else if (!true) {//不匹配
//            prompt.setText("用户名或者密码错误！！");
//        }else if(isValid){ //匹配
        }else if(true){ //匹配
            System.out.println("play");
            FXMLLoader fxmlLoader2=new FXMLLoader(getClass().getResource("start.fxml"));
            //获取当前窗口
            Stage stage2=(Stage) ((Node)event.getSource()).getScene().getWindow();
            try {

                Node root = fxmlLoader2.load();
                // 创建一个StackPane作为新的根布局
                StackPane stackPane = new StackPane();
                // 设置背景图
                Image backgroundImage = new Image(this.getClass().getResourceAsStream("img/bg2.png")); // 确保图片路径正确

                BackgroundImage background = new BackgroundImage(
                        backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT
                );

                // 应用背景图到StackPane
                stackPane.setBackground(new Background(background));

                // 将FXML加载的内容添加到StackPane中
                stackPane.getChildren().add(root);

                // 使用StackPane的默认大小来创建场景
                Scene scene = new Scene(stackPane);


                //创建窗体
                Stage StartStage=new Stage();
                //弹出模式
                StartStage.initModality(Modality.WINDOW_MODAL);
                InputStream in = this.getClass().getResourceAsStream("img/title.png");
                Image image = new Image(in);
                StartStage.getIcons().add(image);
                StartStage.setTitle("三国杀·全明星");
                //设置场景scene
                StartStage.setScene(scene);
                StartStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //关闭当前窗口
            stage2.close();

//        Pane pane = new Pane();
////            Image backgroundImage = new Image(this.getClass().getResourceAsStream("bg2.png")); // 确保图片路径正确
//        pane.setStyle("-fx-background-image: url('profile1.png');");
//        BackgroundImage picture = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT
//        );
        }
    }
}
