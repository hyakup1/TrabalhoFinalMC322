package com.uno.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.uno.exception.JogadaInvalidaException;
import com.uno.service.core.DeckFactory;

/**
 * Represents the deck of cards in the game.
 * <p>
 * This class manages both the draw pile (cards waiting to be drawn) 
 * and the discard pile (cards already played).
 */
public class Deck {
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private final DeckFactory deckFactory = new DeckFactory();
    
    /**
     * Constructs a new Deck.
     * <p>
     * It builds the initial draw pile and draws the first card 
     * to start the discard pile.
     */
    public Deck(){
        buildDrawPile();
        while( discardPile.isEmpty() || discardPile.getLast().isJoker() )
            discardPile.add(draw());
    }

    /**
     * Reconstructs a Deck from previously saved pile contents, bypassing the
     * default random shuffle. Intended for use by the persistence layer only.
     *
     * @param drawPile    the saved list of cards in the draw pile
     * @param discardPile the saved list of cards in the discard pile
     */
    public Deck( List<Card> drawPile, List<Card> discardPile ){
        this.drawPile.addAll(drawPile);
        this.discardPile.addAll(discardPile);
    }

    /**
     * Retrieves an unmodifiable copy of the draw pile.
     *
     * @return a List containing the cards currently in the draw pile.
     */
    public List<Card> getDrawPile(){
        return List.copyOf(drawPile);
    }

    /**
     * Retrieves an unmodifiable copy of the discard pile.
     *
     * @return a List containing the cards currently in the discard pile.
     */
    public List<Card> getDiscardPile(){
        return List.copyOf(discardPile);
    }

    /**
     * Draws a card from the top of the draw pile.
     * <p>
     * If the draw pile is empty, it automatically rebuilds it before drawing.
     *
     * @return The {@link Card} drawn from the pile.
     */
    public Card draw(){
        if( drawPile.isEmpty() ) buildDrawPile();
        return drawPile.removeLast(); 
    }

    /**
     * Plays a card onto the discard pile.
     *
     * @param card The {@link Card} to be played.
     * @throws JogadaInvalidaException if the card cannot be legally played on top of the current discard pile.
     */
    public void play( Card card ) throws JogadaInvalidaException {
        if( !canPlay(card) )
            throw new JogadaInvalidaException("O jogador não pode jogar a carta " + card.toString() + " sobre a carta " + discardPile.getLast().toString() );
        discardPile.add(card);
    }

    /**
     * Checks if a card can be legally played according to the game rules.
     * <p>
     * A card can be played if it matches the symbol or color of the top card 
     * on the discard pile, or if it is a joker (Wild/Wild Draw Four) card.
     *
     * @param card The {@link Card} to be evaluated.
     * @return {@code true} if the card can be played, {@code false} otherwise.
     */
    public boolean canPlay( Card card ){
        Card topCard = discardPile.getLast();

        if( topCard.getSymbol() == card.getSymbol() || topCard.getColor() == card.getColor() || card.isJoker() )
            return true;
        
        return false; 
    }

    /**
     * Builds or rebuilds the draw pile by fetching a shuffled deck 
     * from the {@link DeckFactory}.
     */
    private void buildDrawPile(){
        deckFactory.getShuffledDeck().stream()
            .forEach( card -> drawPile.add(card) );
    }
}