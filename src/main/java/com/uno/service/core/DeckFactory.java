package com.uno.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.model.entity.Card;
import com.uno.model.valueobject.Color;
import com.uno.model.valueobject.Symbol;

public class DeckFactory {
    private final List<Card> deck = new ArrayList<>();
    public DeckFactory(){
        for( Color color : Color.values() )
            for( Symbol symbol : Symbol.values() ){
                try{
                    deck.add(new Card(symbol, color));
                }   
                catch ( Exception e ){}
            }

        } 
        
    public List<Card> getShuffledDeck(){
        Collections.shuffle(deck);
        return List.copyOf(deck);
    }
}
