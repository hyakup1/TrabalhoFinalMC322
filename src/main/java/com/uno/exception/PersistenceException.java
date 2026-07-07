package com.uno.exception;

/**
 * Generic checked exception indicating a failure in the persistence layer.
 * <p>
 * This exception is thrown when saving or loading the game state fails
 * due to I/O issues, file corruption, or database connectivity errors.
 * It is a checked exception because the caller can recover by prompting the user
 * to retry the operation or choose another storage location.
 */
public class PersistenceException extends Exception {

    /**
     * Constructs a new PersistenceException with the specified detail message.
     *
     * @param message the detail message in Portuguese.
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceException with the specified detail message and cause.
     *
     * @param message the detail message in Portuguese.
     * @param cause the cause of the exception.
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
