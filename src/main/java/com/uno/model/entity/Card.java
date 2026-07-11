package com.uno.model.entity;

import java.util.Objects;

import com.uno.model.valueobject.Color;
import com.uno.model.valueobject.Symbol;

/**
 * Represents a card in the Uno game.
 * <p>
 * This class stores the color and symbol of a card and ensures that, upon 
 * its creation, the combination of these attributes is valid according to the game rules.
 */
public class Card {
    
    /**
     * The symbol or value of the card.
     */
    private final Symbol symbol;
    
    /**
     * The color of the card.
     */
    private final Color color;

    /**
     * Constructs a new Card using the specified symbol and color.
     * <p>
     * It also validates if the generated combination is allowed in the game.
     *
     * @param symbol The {@link Symbol} of the card.
     * @param color  The {@link Color} of the card.
     * @throws IllegalArgumentException if the combination of symbol and color is invalid.
     */
    public Card( Symbol symbol, Color color ){
        this.symbol = symbol; 
        this.color = color; 

        validateCard();
    }

    /**
     * Constructs a new Card from the text names of the symbol and color.
     * <p>
     * The constructor parses the strings into their respective Enums and
     * validates if the generated combination is allowed.
     *
     * @param symbolName The name of the card's symbol (must match a constant from the {@link Symbol} enum).
     * @param colorName  The name of the card's color (must match a constant from the {@link Color} enum).
     * @throws IllegalArgumentException if the symbol name is invalid, the color name is invalid, 
     * or if the combination of symbol and color does not exist in the game.
     */
    public Card( String symbolName, String colorName ){
        this.symbol = validateSymbol(symbolName); 
        this.color = validateColor(colorName); 

        validateCard();
    }

    /**
     * Validates and converts the symbol name into a {@link Symbol} enum value.
     *
     * @param symbolName The symbol name as a String.
     * @return The corresponding value in the {@link Symbol} enum.
     * @throws IllegalArgumentException if the string is null or does not match a valid symbol.
     */
    private Symbol validateSymbol( String symbolName ){
        try{
            return Symbol.valueOf(symbolName);
        }
        catch ( IllegalArgumentException | NullPointerException e ){
            throw new IllegalArgumentException("Símbolo da carta inválido: " + symbolName );
        }
    }

    /**
     * Validates and converts the color name into a {@link Color} enum value.
     *
     * @param colorName The color name as a String.
     * @return The corresponding value in the {@link Color} enum.
     * @throws IllegalArgumentException if the string is null or does not match a valid color.
     */
    private Color validateColor( String colorName ){
        try{
            return Color.valueOf(colorName);
        }
        catch ( IllegalArgumentException | NullPointerException e ){
            throw new IllegalArgumentException("Cor da carta inválida: " + colorName );
        }
    }

    /**
     * Validates the card's integrity according to the Uno business rules.
     * <p>
     * Ensures that:
     * <ul>
     * <li>Special action cards (+4 or Change Color) can be any color (considering their effects have been activated already).</li>
     * <li>Normal or simple action cards cannot be black.</li>
     * </ul>
     *
     * @throws IllegalArgumentException if any argument is null, or if the combination between the {@link Symbol} and the {@link Color} is invalid.
     */
    private void validateCard(){
        if( symbol == null || color == null )
            throw new IllegalArgumentException("Carta com argumentos nulos. ");

        if( symbol != Symbol.PLUS_FOUR && symbol != Symbol.CHANGE_COLOR && color == Color.BLACK )
            throw new IllegalArgumentException("Uma carta com símbolo " + symbol.toString() + " e cor " + color.toString() + " não existe. ");        
    }

    /**
     * Retrieves the symbol of the card.
     *
     * @return The {@link Symbol} of this card.
     */
    public Symbol getSymbol(){
        return symbol; 
    }

    /**
     * Retrieves the color of the card.
     *
     * @return The {@link Color} of this card.
     */
    public Color getColor(){
        return color; 
    }

    /**
     * Checks if this card is a joker card (Wild or Wild Draw Four).
     *
     * @return {@code true} if the card is a {@code CHANGE_COLOR} or {@code PLUS_FOUR}, {@code false} otherwise.
     */
    public boolean isJoker(){
        return symbol == Symbol.CHANGE_COLOR || symbol == Symbol.PLUS_FOUR;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return A string combining the color and symbol names (e.g., "RED ONE").
     */
    @Override
    public String toString(){
        return color.toString() + " " + symbol.toString(); 
    }

    /**
     * Compares this card with another object for equality.
     * <p>
     * Two cards are considered equal if they have the same symbol AND the same color.
     *
     * @param o The object to compare with this card.
     * @return {@code true} if the cards have the same symbol and color, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(color, card.color) &&
               Objects.equals(symbol, card.symbol);
    }

    /**
     * Returns the hash code value for this card.
     *
     * @return The hash code computed based on the color and symbol.
     */
    @Override
    public int hashCode() {
        return Objects.hash(color, symbol);
    }
}
