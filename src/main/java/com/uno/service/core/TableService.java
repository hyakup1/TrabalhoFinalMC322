package com.uno.service.core;

import com.uno.exception.JogadaInvalidaException;
import com.uno.model.entity.Card;
import com.uno.model.entity.Table;

/**
 * Service class providing operations to interact with the game table.
 * <p>
 * This class encapsulates the actions a player can perform during their turn, 
 * such as drawing or playing cards on the {@link Table}.
 */
public class TableService {
    private Table table; 

    /**
     * Constructs a new TableService linked to a specific game table.
     *
     * @param table The {@link Table} to be managed by this service.
     */
    public TableService( Table table ){
        this.table = table; 
    }

    /**
     * Updates the underlying table reference.
     *
     * @param table the new Table instance
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Retrieves the underlying table reference.
     *
     * @return the current Table instance
     */
    public Table getTable() {
        return table;
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
     * <p>
     * The exception is propagated through the command boundary so the view can notify
     * the player and request another play.
     *
     * @param symbolName The string representation of the card's symbol.
     * @param colorName  The string representation of the card's color.
     * @throws JogadaInvalidaException if the play is invalid (e.g. card not in hand, color/symbol mismatch).
     */
    public void play( String symbolName, String colorName ) throws JogadaInvalidaException {
        Card card = new Card( symbolName, colorName );
        table.play(card);
    }
}
