package com.uno.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the Uno game.
 * <p>
 * This class manages the player's identity (name) and their current hand of cards.
 * It provides methods to draw cards, play cards, and inspect the player's status.
 */
public class Player {
    
    /**
     * The collection of cards currently held by the player.
     */
    private final List<Card> hand = new ArrayList<>();
    
    /**
     * The name of the player.
     */
    private final String name; 

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name The name of the player.
     * @throws IllegalArgumentException if the provided name is null or blank.
     */
    public Player( String name ){
        validateName(name); 
        this.name = name;
    }

    /**
     * Validates the player's name to ensure it is not null or empty.
     *
     * @param name The name to be validated.
     * @throws IllegalArgumentException if the name is null or consists only of whitespace.
     */
    private void validateName( String name ){
        if( name == null || name.isBlank() )
            throw new IllegalArgumentException("O nome do jogador não pode ser vazio. ");
    }

    /**
     * Adds a drawn card to the player's hand.
     *
     * @param card The {@link Card} to be added to the hand.
     */
    public void draw( Card card ){
        hand.add(card);
    }

    /**
     * Plays a specific card from the player's hand, removing it from their current collection.
     *
     * @param card The {@link Card} the player intends to play.
     * @throws IllegalArgumentException if the player does not have the specified card in their hand.
     */
    public void play( Card card ){
        if( !hasCard(card) )
            throw new IllegalArgumentException("Jogada inválida: o jogador não possui a carta " + card.getSymbol().toString() + " " + card.getColor().toString() );

        hand.remove(hand.indexOf(card));
    }

    public boolean hasCard( Card card ){
        return hand.indexOf(card) != -1;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The player's name as a String.
     */
    public String getName(){
        return name; 
    }

    /**
     * Retrieves an unmodifiable view of the player's current hand.
     * <p>
     * This prevents external modification of the player's hand directly through the returned list.
     *
     * @return An unmodifiable {@link List} containing the player's {@link Card}s.
     */
    public List<Card> getHand(){
        return Collections.unmodifiableList(hand);
    }

    /**
     * Checks whether the player's hand is empty.
     * <p>
     * An empty hand typically indicates that the player has won the game.
     *
     * @return {@code true} if the player has no cards left in their hand, {@code false} otherwise.
     */
    public boolean isHandEmpty(){
        return hand.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}