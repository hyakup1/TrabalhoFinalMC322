package com.uno.service.core.command;


import com.uno.model.Command;

public interface CommandHandler {
    void handle(Command command);

    /**
     * Executes the command and returns optional displayable output.
     *
     * <p>The default implementation delegates to {@link #handle(Command)}
     * and returns {@code null} (no output). Report handlers override this
     * method to return the formatted report text.</p>
     *
     * @param command the command to handle
     * @return formatted output to display, or {@code null} if none
     */
    default String handleWithOutput(Command command) {
        handle(command);
        return null;
    }
}