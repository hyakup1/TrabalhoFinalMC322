package com.uno.controller;

import com.uno.model.entity.Table;

/**
 * Interface that provides a contract for any game view implementation,
 * ensuring dependency inversion between the game core and the presentation layer.
 */
public interface GameView {
    
    /**
     * Displays the current state of the game based on the table information.
     * @param table the game table containing turn, deck, and player info.
     */
    void showGameState(Table table);

    /**
     * Prompts the current player to input a command and returns it.
     * @return the string representing the command entered by the user.
     */
    String readCommand();

    /**
     * Displays a general message or game output to the user.
     * @param message the message to exhibit.
     */
    void showMessage(String message);

    /**
     * Displays an error message to the user.
     * @param errorMessage the error to exhibit.
     */
    void showError(String errorMessage);

    /**
     * Clears the terminal screen or console window to keep the presentation clean.
     */
    void clearScreen();
}