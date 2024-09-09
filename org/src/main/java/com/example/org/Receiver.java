package com.example.org;

import java.util.ArrayList;
import java.util.List;

public class Receiver {
    public Room room;

    public Receiver(Room room) {
        this.room = room;
    }

    //确认玩家手中是否有某张牌
    public boolean CheckContains(Player player,int typeId){
        for(int i=0;i<player.handCardList.size();i++){
            if(player.handCardList.get(i).getTypeId()==typeId) return true;
        }
        return false;
    }

    //指定目标出牌
    public void PlayCardWithTarget(Player player,int seatId,int id){
        if(!CheckContains(player,player.handCardList.get(id).getTypeId())) return;
        Player targetPlayer = room.getPlayerBySeatId(seatId);
        if(targetPlayer==null) return;
        Card card = player.handCardList.get(id);
        if(card.RequireTarget()==false){
            PlayCardWithoutTarget(player,id);
            return;
        }
        if(card.CanInitiative()==false) return;
        player.handCardList.remove(id);
        room.RespWithTarget(player,targetPlayer,card.getTypeId());
    }

    //确定是否能出牌（包括响应出牌和当前回合出牌和救援）
    public boolean CheckPlayCard(Player player,int id) {
        if (player.handCardList.contains(id)) return false;
        if (room.respPlayers.contains(player) && room.respPlayers.get(0) == player && room.status== Room.roomStatus.RespStatus) return true;
        if (room.helpPlayers.contains(player) && room.helpPlayers.get(0) == player && room.status== Room.roomStatus.RescueStatus) return true;
        if (room.turn == player.seatId && room.status== Room.roomStatus.PlayStatus){
            if(!player.IsAbleToPlay(0)) return true;
            else return false;
        }
        return false;
    }

    //无指定目标出牌
    public void PlayCardWithoutTarget(Player player,int id){
        if(!CheckPlayCard(player,id)) return ;
        if(room.status== Room.roomStatus.RescueStatus){
            boolean resp = CardManager.cardsMap.get(3).Use(player,id);
            if(!resp){
                //发送求援信息
            }
        }

        if(room.status== Room.roomStatus.PlayStatus){
            Card card = player.handCardList.get(id);
            if(card.CanInitiative()){
                boolean user = card.Use(player,id);
                if(!user){}
                else{
                    card.setResp(player);
                }
            }
        }
        else if(room.status== Room.roomStatus.RespStatus){
            Card card = player.handCardList.get(id);
            boolean resp = room.currentCard.Resp(player,id);
            if(resp) {

            }
        }
    }
    //
    public void Abandon(Player player){
        if(room.wxkjPlayers.size()>0){
            for (Player wxkjPlayer : room.wxkjPlayers) {
                if(wxkjPlayer.seatId == player.seatId){
                    room.wxkjPlayers.remove(wxkjPlayer);
                    if(room.wxkjPlayers.size()>0){
                        return;
                    }
                    else{
                        Card card = player.room.currentCard;
                        card.wxkjResp(player,false);
                    }
                }
            }
        }
        else if(room.turn==player.getSeatId()&&room.status == Room.roomStatus.PlayStatus){
            room.status= Room.roomStatus.DiscardStatus;
        }
        else if(room.status== Room.roomStatus.RescueStatus){
            room.helpPlayers.remove(0);
            if(room.helpPlayers.size()>0){
                // 向下一个玩家发送请求
            }
            else room.GameOver(room);
        }
        else if(room.respPlayers.get(0)==player){
            room.respPlayers.remove(0);
            if(room.currentCard.AbandonResp(player)){
                if(player.hp<=0){
                    room.status= Room.roomStatus.RescueStatus;
                    room.dyingPlayer=player;
                    //通知房间内成员是否救援
                }
                else room.setStatus(Room.roomStatus.PlayStatus);
            }
        }
    }
}
