package com.uno.exception;

/**
 * Exception thrown when the game turn flow or protocol is violated.
 * <p>
 * This is an unchecked exception representing a critical contract/API violation.
 * For example, attempting to retrieve the current turn player when no players are registered.
 * It is considered non-recoverable under normal game flow as it indicates a developer/logic bug.
 */
public class TurnoVioladoException extends RuntimeException {

    /**
     * Constructs a new TurnoVioladoException with the specified detail message.
     *
     * @param message the detail message in Portuguese.
     */
    public TurnoVioladoException(String message) {
        super(message);
    }

    /**
     * Constructs a new TurnoVioladoException with the specified detail message and cause.
     *
     * @param message the detail message in Portuguese.
     * @param cause the cause of the exception.
     */
    public TurnoVioladoException(String message, Throwable cause) {
        super(message, cause);
    }
}
