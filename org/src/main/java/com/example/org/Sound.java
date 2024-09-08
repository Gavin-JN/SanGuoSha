package com.example.org;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import static javafx.application.Application.launch;

public class Sound{
    public Sound() {
        StackPane root = new StackPane();

        // 创建媒体对象，指定音乐文件路径
        Media media = new Media(this.getClass().getResource("music/BGM.mp3").toString());

        // 创建媒体播放器
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }
}