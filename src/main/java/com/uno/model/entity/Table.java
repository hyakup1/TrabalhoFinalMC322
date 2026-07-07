package com.uno.model.entity;

import com.uno.exception.JogadaInvalidaException;

/**
 * Represents the game table.
 * <p>
 * This class orchestrates the main game components, acting as the central hub 
 * that connects the {@link Deck}, the {@link TurnManager}, and the players.
 */
public class Table {
    private final Deck deck;
    private final TurnManager turnManager;
    private final int initialCardCount;

    /**
     * Constructs a new Table with a specific number of initial cards per player.
     *
     * @param initialCardCount The amount of cards each player should receive when added to the table.
     */
    public Table( int initialCardCount ){
        this.initialCardCount = initialCardCount;
        this.deck = new Deck();
        this.turnManager = new TurnManager();
    }

    /**
     * Reconstructs a Table from previously saved deck and turn manager state.
     * Intended for use by the persistence layer only.
     *
     * @param initialCardCount the initial amount of cards per player
     * @param deck             the reconstructed deck
     * @param turnManager      the reconstructed turn manager
     */
    public Table( int initialCardCount, Deck deck, TurnManager turnManager ){
        this.initialCardCount = initialCardCount;
        this.deck = deck;
        this.turnManager = turnManager;
    }

    /**
     * Retrieves the initial card count per player.
     *
     * @return the initial card count.
     */
    public int getInitialCardCount(){
        return initialCardCount;
    }

    /**
     * Retrieves the deck associated with this table.
     *
     * @return the {@link Deck}.
     */
    public Deck getDeck(){
        return deck;
    }

    /**
     * Adds a new player to the game table.
     * <p>
     * Upon joining, the player automatically draws their initial hand of cards 
     * and is registered in the {@link TurnManager}.
     *
     * @param player The {@link Player} to be added.
     */
    public void addPlayer( Player player ){
        for( int i = 0; i < initialCardCount; i++ )
            player.draw(deck.draw());

        turnManager.addPlayer(player);
    }

    /**
     * Forces the current turn player to draw one card from the deck.
     */
    public void draw(){
        Player player = turnManager.getTurnPlayer();
        player.draw(deck.draw());
    }

    /**
     * Forces the current turn player to draw a specific amount of cards from the deck.
     *
     * @param N The amount of cards to be drawn.
     */
    public void drawN( int N ){
        for( int i = 0; i < N; i++ ) draw();
    }

    /**
     * Executes a play action for the current turn player.
     * <p>
     * Validates if the player holds the card, registers it in the deck's discard pile, 
     * and removes it from the player's hand.
     *
     * @param card The {@link Card} the current player intends to play.
     * @throws JogadaInvalidaException if the player does not have the specified card in their hand, or the card cannot be legally played.
     */
    public void play( Card card ) throws JogadaInvalidaException {
        Player player = turnManager.getTurnPlayer();
        if( !player.hasCard(card) ) 
            throw new JogadaInvalidaException("Jogada inválida: O jogador não possui a carta " + card.toString() );

        deck.play(card);
        player.play(card);
    }
    
    /**
     * Retrieves the turn manager associated with this table.
     *
     * @return The {@link TurnManager} handling the flow of the game.
     */
    public TurnManager getTurnManager(){
        return turnManager;
    }
}