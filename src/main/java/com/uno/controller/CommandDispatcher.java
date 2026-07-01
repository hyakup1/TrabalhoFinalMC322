package com.uno.controller;

import com.uno.model.Command;
import com.uno.service.core.command.CommandFactory;
import com.uno.service.core.command.CommandHandler;
import com.uno.service.infra.CommandParser;
/**
 * Service class for dispatching commands to the appropriate handlers for users, projects, and tasks.
 */
public class CommandDispatcher {
    private final CommandFactory commandFactory;
    private static CommandDispatcher instance;

    public CommandDispatcher(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }


    public String process(String line) {
        Command command = CommandParser.parseCommand(line);

        if (command != null) {
            return this.dispatchCommand(command);
        }
        return null;
    }

    public String dispatchCommand(Command command) {
        CommandHandler handler = commandFactory.getHandler(command);
        return handler.handleWithOutput(command);
    }
}