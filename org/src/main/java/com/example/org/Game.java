package com.example.org;
///
public class Game {
    private int round;
    enum gameStatus {judgeStatus,drawStatus,playStatus,discardStatus,responseStatus,rescueStatus};
    private gameStatus status;

    public int getRound() {
        return round;
    }

    public gameStatus getStatus() {
        return status;
    }
    public void CardHandle(int source,int target, int typeId){
<<<<<<< HEAD
=======

>>>>>>> 6880b873136ab6792d23c32f2be526361f2b8415
    }
}
