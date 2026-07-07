package com.uno.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.uno.exception.TurnoVioladoException;

/**
 * Manages the players' turns and the flow of the game.
 * <p>
 * This class keeps track of the registered players, determines whose turn it is, 
 * and controls the direction of play (clockwise or counter-clockwise).
 */
public class TurnManager {
    private final List<Player> players = new ArrayList<>();
    private int currentId = 0; 
    private int direction = 1; 

    /**
     * Adds a player to the turn rotation.
     *
     * @param player The {@link Player} to be added.
     */
    public void addPlayer( Player player ){
        players.add(player);
    }

    /**
     * Retrieves the player whose turn it currently is.
     *
     * @return The {@link Player} holding the current turn.
     * @throws TurnoVioladoException if there are no players registered in the game.
     */
    public Player getTurnPlayer(){
        if( players.isEmpty() )
            throw new TurnoVioladoException("Nenhum jogador registrado. ");

        return players.get(currentId);
    }

    /**
     * Ends the current turn and advances to the next player.
     * <p>
     * The next player is determined based on the current direction of play.
     *
     * @return A string message indicating that the turn has ended.
     */
    public String endTurn(){
        String playerName = getTurnPlayer().getName();

        currentId += direction;
        currentId %= players.size(); 
        if( currentId < 0 ) currentId += players.size();

        return "Turno de " + playerName + " finalizado. ";
    }

    /**
     * Reverses the current direction of play.
     *
     * @return A string representing the new direction of play.
     */
    public String flipDirection(){
        direction *= -1;
        return getDirectionStr();
    }

    /**
     * Retrieves the current direction of play in a human-readable format.
     *
     * @return "Horário" if the play direction is standard (1), or "Anti-horário" if reversed (-1).
     */
    public String getDirectionStr(){
        return (direction == 1) ? "Horário" : "Anti-horário";
    }
}