package com.uno.service.core.query;

import java.util.List;

import com.uno.model.entity.Card;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;

/**
 * Query class responsible for exposing game state information to the view.
 * <p>
 * This class provides read-only representations of the current game state,
 * preventing the view from directly changing the model entities.
 */
public class GameQuery {
    private final Table table;

    /**
     * Constructs a new GameQuery linked to a specific game table.
     *
     * @param table The {@link Table} whose state will be queried.
     */
    public GameQuery( Table table ){
        this.table = table;
    }

    /**
     * Retrieves the name of the player whose turn it currently is.
     *
     * @return The current player's name.
     */
    public String getCurrentPlayerName(){
        return table.getTurnManager().getTurnPlayer().getName();
    }

    /**
     * Retrieves a read-only textual representation of the current player's hand.
     *
     * @return A {@link List} containing the cards in the current player's hand.
     */
    public List<String> getCurrentPlayerHand(){
        return table.getTurnManager().getTurnPlayer().getHand().stream()
            .map(Card::toString)
            .toList();
    }

    /**
     * Retrieves the card currently on top of the discard pile.
     *
     * @return A textual representation of the top card.
     */
    public String getTopCard(){
        return table.getDeck().getDiscardPile().getLast().toString();
    }

    /**
     * Retrieves the current direction of play.
     *
     * @return A human-readable representation of the play direction.
     */
    public String getDirection(){
        return table.getTurnManager().getDirectionStr();
    }

    /**
     * Retrieves the names of all players registered in the game.
     *
     * @return A read-only {@link List} containing the player names.
     */
    public List<String> getPlayerNames(){
        return table.getTurnManager().getPlayers().stream()
            .map(Player::getName)
            .toList();
    }

    /**
     * Retrieves the amount of cards held by a specific player.
     *
     * @param playerName The exact name of the player to query.
     * @return The amount of cards in the player's hand.
     * @throws IllegalArgumentException if no player with the given name is found.
     */
    public int getPlayerCardCount( String playerName ){
        return table.getTurnManager().getPlayers().stream()
            .filter(player -> player.getName().equals(playerName))
            .findFirst()
            .map(player -> player.getHand().size())
            .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado: " + playerName));
    }

    /**
     * Checks whether any player has emptied their hand and won the game.
     *
     * @return {@code true} if the game has a winner, {@code false} otherwise.
     */
    public boolean hasWinner(){
        return table.getTurnManager().getPlayers().stream()
            .anyMatch(Player::isHandEmpty);
    }

    /**
     * Retrieves the name of the player who has won the game.
     *
     * @return The winner's name.
     * @throws IllegalStateException if the game does not have a winner yet.
     */
    public String getWinnerName(){
        return table.getTurnManager().getPlayers().stream()
            .filter(Player::isHandEmpty)
            .findFirst()
            .map(Player::getName)
            .orElseThrow(() -> new IllegalStateException("O jogo ainda não possui um vencedor. "));
    }
}
