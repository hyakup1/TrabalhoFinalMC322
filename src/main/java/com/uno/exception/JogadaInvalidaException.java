package com.uno.exception;

/**
 * Exception thrown when a player attempts an invalid play in the game.
 * <p>
 * This is a checked exception representing a business logic failure (such as
 * trying to play a card that does not match the color/symbol of the top discard
 * card, or trying to play a card that is not in the player's hand). It is considered
 * recoverable because the game loop can catch it, prompt the user for a new play,
 * and continue.
 */
public class JogadaInvalidaException extends Exception {

    /**
     * Constructs a new JogadaInvalidaException with the specified detail message.
     *
     * @param message the detail message in Portuguese.
     */
    public JogadaInvalidaException(String message) {
        super(message);
    }

    /**
     * Constructs a new JogadaInvalidaException with the specified detail message and cause.
     *
     * @param message the detail message in Portuguese.
     * @param cause the cause of the exception.
     */
    public JogadaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
