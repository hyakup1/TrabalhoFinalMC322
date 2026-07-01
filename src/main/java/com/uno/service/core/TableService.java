package com.uno.service.core;

import com.uno.model.entity.Card;
import com.uno.model.entity.Table;

/**
 * Service class providing operations to interact with the game table.
 * <p>
 * This class encapsulates the actions a player can perform during their turn, 
 * such as drawing or playing cards on the {@link Table}.
 */
public class TableService {
    private final Table table; 

    /**
     * Constructs a new TableService linked to a specific game table.
     *
     * @param table The {@link Table} to be managed by this service.
     */
    public TableService( Table table ){
        this.table = table; 
    }

    /**
     * Instructs the current player to draw a single card from the deck.
     */
    public void draw(){
        table.draw();
    }

    /**
     * Instructs the current player to draw a specific number of cards from the deck.
     *
     * @param N The amount of cards to be drawn.
     */
    public void drawN( int N ){
        table.drawN(N);
    }

    /**
     * Attempts to play a card for the current player based on its symbol and color names.
     *
     * @param symbolName The string representation of the card's symbol.
     * @param colorName  The string representation of the card's color.
     */
    public void play( String symbolName, String colorName ){
        Card card = new Card( symbolName, colorName );
        table.play(card);
    }
}