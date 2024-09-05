package com.example.org;

import java.util.List;

public class Receiver {
    public Room room;
    public boolean CheckContains(Player player,int typeId){
        for(int i=0;i<player.getHandCardList().size();i++){
            if(player.getHandCardList().get(i).getTypeId()==typeId) return true;
        }
        return false;
    }

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
        card.RespWithTarget(player,targetPlayer,typeId);
    }

    public void PlayCardWithoutTarget(Player player,int typeId){

    }
}
