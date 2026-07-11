package com.uno.service.core.command;


import com.uno.exception.JogadaInvalidaException;
import com.uno.model.Command;

/**
 * Contract implemented by every game command handler.
 */
public interface CommandHandler {
    /**
     * Executes the specified command.
     *
     * @param command the command to handle
     * @throws JogadaInvalidaException if the command attempts an invalid play
     */
    void handle( Command command ) throws JogadaInvalidaException;

    /**
     * Executes the command and returns optional displayable output.
     *
     * <p>The default implementation delegates to {@link #handle(Command)}
     * and returns {@code null} (no output). Handlers may override this
     * method to return a message for the view.</p>
     *
     * @param command the command to handle
     * @return formatted output to display, or {@code null} if none
     * @throws JogadaInvalidaException if the command attempts an invalid play
     */
    default String handleWithOutput( Command command ) throws JogadaInvalidaException {
        handle(command);
        return null;
    }
}
