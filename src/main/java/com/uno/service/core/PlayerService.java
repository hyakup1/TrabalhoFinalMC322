package com.uno.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.model.entity.Player;
import com.uno.model.entity.Table;

public class PlayerService {
    private final List<Player> players = new ArrayList<>(); 
    private final Table table; 
    
    public PlayerService( Table table ){
        this.table = table; 
    }

    public void addPlayer( String playerName ){
        Player player = new Player(playerName);

        if( players.indexOf(player) != -1 ) 
            throw new IllegalArgumentException("Jogador com este nome já existe. ");

        players.add(player);
        table.addPlayer(player);
    }

    public List<Player> getPlayers(){
        return Collections.unmodifiableList(players);
    }

    public Player getPlayerByName( String name ){
        return players.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jogaor não encontrado: " + name));
    }
}
