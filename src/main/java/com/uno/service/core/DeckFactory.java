package com.uno.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.model.entity.Card;
import com.uno.model.valueobject.Color;
import com.uno.model.valueobject.Symbol;

/**
 * Factory class responsible for generating and providing decks of Uno cards.
 */
public class DeckFactory {
    private final List<Card> deck = new ArrayList<>();
    
    /**
     * Constructs a new DeckFactory and initializes a complete standard Uno deck.
     * <p>
     * It iterates through all possible combinations of {@link Color} and {@link Symbol}.
     * Invalid combinations (such as black normal cards) are safely ignored as 
     * exceptions thrown by the {@link Card} constructor are caught and suppressed.
     */
    public DeckFactory(){
        for( Color color : Color.values() ) if( color != Color.BLACK )
            for( Symbol symbol : Symbol.values() ) if( symbol != Symbol.PLUS_FOUR && symbol != Symbol.CHANGE_COLOR ) {
                deck.add(new Card(symbol, color));
            }

        for( int i = 0; i < 4; i++ ){
            deck.add(new Card(Symbol.PLUS_FOUR, Color.BLACK));
            deck.add(new Card(Symbol.CHANGE_COLOR, Color.BLACK));
        }
    } 
        
    /**
     * Shuffles the internally generated deck and returns a copy of it.
     *
     * @return An unmodifiable {@link List} of shuffled {@link Card}s.
     */
    public List<Card> getShuffledDeck(){
        Collections.shuffle(deck);
        return List.copyOf(deck);
    }
}