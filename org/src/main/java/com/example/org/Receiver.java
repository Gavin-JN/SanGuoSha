package com.example.org;

import java.util.ArrayList;
import java.util.List;

public class Receiver {
    public Room room;

    //确认玩家手中是否有某张牌
    public boolean CheckContains(Player player,int typeId){
        for(int i=0;i<player.getHandCardList().size();i++){
            if(player.getHandCardList().get(i).getTypeId()==typeId) return true;
        }
        return false;
    }

    //指定目标出牌
    public void PlayCardWithTarget(Player player,int seatId,int typeId){
        if(!CheckContains(player,typeId)) return;
        Player targetPlayer = room.getPlayerBySeatId(seatId);
        if(targetPlayer==null) return;
        Card card = new Card();
        for(int i=0;i<player.getHandCardList().size();i++){
            if(player.getHandCardList().get(i).getTypeId()==typeId){
                card = player.getHandCardList().get(i);
                break;
            }
        }
        if(card.RequireTarget()==false){
            PlayCardWithoutTarget(player,typeId);
            return;
        }
        if(card.CanInitiative()==false) return;
        List<Card> newHandCardList=player.getHandCardList();
        for(int i=0;i<newHandCardList.size();i++){
            if(newHandCardList.get(i).getTypeId()==typeId){
                newHandCardList.remove(i);
                break;
            }
        }
        player.setHandCardList(newHandCardList);
        room.RespWithTarget(player,targetPlayer,typeId);
    }

    //确定是否能出牌（包括响应出牌和当前回合出牌和救援）
    public boolean CheckPlayCard(Player player,int typeId) {
        ArrayList<Integer> handCardTypeId = new ArrayList<>();
        for(int i=0;i<player.getHandCardList().size();i++){
            handCardTypeId.add(player.getHandCardList().get(i).getTypeId());
            if(!handCardTypeId.contains(typeId)) return false;
        }
        if (room.respPlayers.contains(player) && room.respPlayers.get(0) == player && room.status== Room.roomStatus.RespStatus) return true;
        if (room.helpPlayers.contains(player) && room.helpPlayers.get(0) == player && room.status== Room.roomStatus.RescueStatus) return true;
        if (room.turn == player.seatId && room.status== Room.roomStatus.PlayStatus){
            if(player.IsAbleToPlay()) return true;
            else return false;
        }
        return false;
    }

    //无指定目标出牌
    public void PlayCardWithoutTarget(Player player,int typeId){
        if(!CheckPlayCard(player,typeId)) return ;
        for (int i=0;i<player.getHandCardList().size();i++) {
            if(player.getHandCardList().get(i).getTypeId()==typeId) {
                Card card = player.getHandCardList().get(i);

            }
        }
        if(room.currentCard.Resp(player,typeId)){
            // 进行全局消息提示
            room.respPlayers.remove(player);
            if(room.respPlayers.size()>0){
                //通知该玩家响应
            }
            else{
                room.setStatus(Room.roomStatus.PlayStatus);
            }
        }

    }

    //
    public void Abandon(Player player){
        if(room.turn==player.getSeatId()&&room.status == Room.roomStatus.PlayStatus){
            room.status= Room.roomStatus.DiscardStatus;
            player.Discard();
            int nextTurn = (++room.turn)%room.players.size();
            room.turn = nextTurn;
            room.status= Room.roomStatus.JudgeStatus;
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
