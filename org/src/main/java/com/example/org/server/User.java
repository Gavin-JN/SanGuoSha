package com.example.org.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private String name;
    private String host;//ip地址
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int status; //0表示登录 1表示游戏中 2表示离线


    private String IOString;//输入流


    public User(String name, String host,Socket socket,BufferedReader in,PrintWriter out) {
        this.name = name;
        this.host = host;
        this.socket = socket;
        this.in = in;
        this.out = out;
    }
    //GET SET

}
