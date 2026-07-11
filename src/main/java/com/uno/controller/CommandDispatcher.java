package com.uno.controller;

import com.uno.model.Command;
import com.uno.service.core.command.CommandFactory;
import com.uno.service.core.command.CommandHandler;
import com.uno.service.infra.CommandParser;

/**
 * Controller class responsible for dispatching game commands to their handlers.
 */
public class CommandDispatcher {
    private final CommandFactory commandFactory;

    /**
     * Constructs a new CommandDispatcher using the specified command factory.
     *
     * @param commandFactory The factory used to retrieve command handlers.
     */
    public CommandDispatcher( CommandFactory commandFactory ){
        this.commandFactory = commandFactory;
    }

    /**
     * Parses and dispatches a textual command.
     *
     * @param line The command line to process.
     * @return An optional message produced by the command handler.
     */
    public String process( String line ){
        Command command = CommandParser.parseCommand(line);
        return dispatchCommand(command);
    }

    /**
     * Dispatches an already parsed command to its registered handler.
     *
     * @param command The command to dispatch.
     * @return An optional message produced by the command handler.
     */
    public String dispatchCommand( Command command ){
        CommandHandler handler = commandFactory.getHandler(command);
        return handler.handleWithOutput(command);
    }
}
