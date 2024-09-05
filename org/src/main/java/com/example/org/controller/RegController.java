package com.example.org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class RegController {

    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtConfirm;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;

    @FXML
    public void login(ActionEvent event){
        System.out.println("login");
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("login.fxml"));
        //获取当前窗口
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();


        try {
            Node root = fxmlLoader.load();
            // 创建一个StackPane作为新的根布局
            StackPane stackPane = new StackPane();

            // 设置背景图
            Image backgroundImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("images/bg.png"))); // 确保图片路径正确

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
            Stage loginStage=new Stage();
            //弹出模式
            loginStage.initModality(Modality.WINDOW_MODAL);
            //设置场景scene
            InputStream in = this.getClass().getResourceAsStream("images/title.png");
            Image image = new Image(in);
            loginStage.getIcons().add(image);
            loginStage.setTitle("登录页");
            loginStage.setScene(scene);
            loginStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //关闭当前窗口
        stage.close();

    }

    @FXML
    public void register(ActionEvent event){
        System.out.println("register");

    }

}
