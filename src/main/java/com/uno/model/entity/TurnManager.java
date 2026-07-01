package com.uno.model.entity;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private final List<Player> players = new ArrayList<>();
    private int currentId = 0; 
    private int direction = 1; 

    public void addPlayer( Player player ){
        players.add(player);
    }

    public Player getTurnPlayer(){
        if( players.isEmpty() )
            throw new IllegalArgumentException("Nenhum jogador registrado. ");

        return players.get(currentId);
    }

    public String endTurn(){
        String playerName = getTurnPlayer().getName();

        currentId += direction;
        currentId %= players.size(); 
        if( currentId < 0 ) currentId += players.size();

        return "Turno de " + playerName + " finalizado. ";
    }

    public String flipDirection(){
        direction *= -1;
        return getDirectionStr();
    }

    public String getDirectionStr(){
        return (direction == 1) ? "Horário" : "Anti-horário";
    }
}
