package com.uno.service.core;

import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
import com.uno.model.entity.TurnManager;

/**
 * Service class that handles the flow of the game's turns.
 * <p>
 * It provides a simplified interface to interact with the underlying {@link TurnManager} 
 * to flip directions, end turns, and retrieve the current player.
 */
public class TurnManagerService {
    private final TurnManager turnManager; 

    /**
     * Constructs a new TurnManagerService by extracting the turn manager from the given table.
     *
     * @param table The {@link Table} whose turn manager will be controlled.
     */
    public TurnManagerService( Table table ){
        this.turnManager = table.getTurnManager();
    }

    /**
     * Reverses the current direction of play.
     *
     * @return A string representing the new direction of play.
     */
    public String flipDirection(){
        return turnManager.flipDirection();
    }

    /**
     * Ends the current player's turn and transitions to the next player.
     *
     * @return A string message confirming the end of the turn.
     */
    public String endTurn(){
        return turnManager.endTurn();
    }

    /**
     * Retrieves the player whose turn it currently is.
     *
     * @return The {@link Player} currently taking their turn.
     */
    public Player getCurrentPlayer(){
        return turnManager.getTurnPlayer();
    }
}