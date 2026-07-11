package com.uno.service.core.command;


import com.uno.model.Command;

/**
 * Contract implemented by every game command handler.
 */
public interface CommandHandler {
    /**
     * Executes the specified command.
     *
     * @param command the command to handle
     */
    void handle( Command command );

    /**
     * Executes the command and returns optional displayable output.
     *
     * <p>The default implementation delegates to {@link #handle(Command)}
     * and returns {@code null} (no output). Handlers may override this
     * method to return a message for the view.</p>
     *
     * @param command the command to handle
     * @return formatted output to display, or {@code null} if none
     */
    default String handleWithOutput( Command command ){
        handle(command);
        return null;
    }
}
