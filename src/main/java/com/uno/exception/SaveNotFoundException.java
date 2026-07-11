package com.uno.exception;

/**
 * Exception thrown when a requested save game state cannot be found.
 * <p>
 * This is a specialization of {@link PersistenceException} and is checked.
 * It is thrown when trying to load a save file or slot that does not exist.
 * The application can recover by notifying the user that the file is missing and
 * allowing them to start a new game or select a different save.
 */
public class SaveNotFoundException extends PersistenceException {

    /**
     * Constructs a new SaveNotFoundException with the specified detail message.
     *
     * @param message the detail message in Portuguese.
     */
    public SaveNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new SaveNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message in Portuguese.
     * @param cause the cause of the exception.
     */
    public SaveNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
