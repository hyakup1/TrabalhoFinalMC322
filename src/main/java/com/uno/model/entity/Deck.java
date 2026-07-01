package com.uno.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.service.core.DeckFactory;

public class Deck {
    private final List<Card> drawPile = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();
    private final DeckFactory deckFactory = new DeckFactory();
    
    public Deck(){
        buildDrawPile();
        discardPile.add(draw());
    }

    public Card draw(){
        if( drawPile.isEmpty() ) buildDrawPile();
        return drawPile.removeLast(); 
    }

    public void play( Card card ){
        if( !canPlay(card) )
            throw new IllegalArgumentException("O jogador não pode jogar a carta " + card.toString() + " sobre a carta " + discardPile.getLast().toString() );
        discardPile.add(card);
    }

    public boolean canPlay( Card card ){
        Card topCard = discardPile.getLast();

        if( topCard.getSymbol() == card.getSymbol() || topCard.getColor() == card.getColor() || card.isJoker() )
            return true;
        
        return false; 
    }

    private void buildDrawPile(){
        deckFactory.getShuffledDeck().stream()
            .forEach( card -> drawPile.add(card) );
    }
}
