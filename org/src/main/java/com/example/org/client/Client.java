package com.example.org.client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("120.26.3.185",2777);//公网的ip地址，不在同一个局域网100.67.78.195
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
