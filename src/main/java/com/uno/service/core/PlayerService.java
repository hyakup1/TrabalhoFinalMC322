package com.uno.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.model.entity.Player;
import com.uno.model.entity.Table;

/**
 * Service class that manages player-related operations in the game.
 * <p>
 * This class acts as an intermediary for creating players, ensuring unique 
 * names, and adding them to the game {@link Table}.
 */
public class PlayerService {
    private Table table; 
    
    /**
     * Constructs a new PlayerService associated with a specific game table.
     *
     * @param table The {@link Table} where the players will be added.
     */
    public PlayerService( Table table ){
        this.table = table; 
    }

    /**
     * Updates the underlying table reference.
     *
     * @param table the new Table instance
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Creates a new player with the given name and adds them to the game.
     *
     * @param playerName The name of the new player to be added.
     * @throws IllegalArgumentException if a player with the same name already exists.
     */
    public void addPlayer( String playerName ){
        Player player = new Player(playerName);

        if( table.getTurnManager().getPlayers().contains(player) ) 
            throw new IllegalArgumentException("Jogador com este nome já existe. ");

        table.addPlayer(player);
    }

    /**
     * Retrieves all registered players in the game.
     *
     * @return An unmodifiable {@link List} of the registered {@link Player}s.
     */
    public List<Player> getPlayers(){
        return Collections.unmodifiableList(table.getTurnManager().getPlayers());
    }

    /**
     * Finds and retrieves a specific player by their name.
     *
     * @param name The exact name of the player to search for.
     * @return The {@link Player} matching the specified name.
     * @throws IllegalArgumentException if no player with the given name is found.
     */
    public Player getPlayerByName( String name ){
        return table.getTurnManager().getPlayers().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado: " + name));
    }
}
