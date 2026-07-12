package com.uno.service.core.command;

import java.util.HashMap;
import java.util.Map;

import com.uno.model.Command;
import com.uno.service.core.PlayerService;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.handler.AddPlayerCommandHandler;
import com.uno.service.core.command.handler.DrawCommandHandler;
import com.uno.service.core.command.handler.DrawNCommandHandler;
import com.uno.service.core.command.handler.EndTurnCommandHandler;
import com.uno.service.core.command.handler.FlipDirectionCommandHandler;
import com.uno.service.core.command.handler.PlayCommandHandler;
import com.uno.service.core.command.handler.PlayPlusFourCommandHandler;
import com.uno.service.core.command.handler.PlayPlusTwoCommandHandler;
import com.uno.service.core.command.handler.PlayReverseCommandHandler;
import com.uno.service.core.command.handler.PlaySkipCommandHandler;

/**
 * Factory class responsible for registering and retrieving command handlers.
 */
public class CommandFactory {
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    /**
     * Constructs a CommandFactory and registers all commands supported by the game.
     *
     * @param playerService The service responsible for player operations.
     * @param tableService The service responsible for table operations.
     * @param turnManagerService The service responsible for turn operations.
     */
    public CommandFactory( PlayerService playerService, TableService tableService, TurnManagerService turnManagerService ){
        handlers.put("ADD_PLAYER", new AddPlayerCommandHandler(playerService));
        handlers.put("DRAW", new DrawCommandHandler(tableService));
        handlers.put("DRAW_N", new DrawNCommandHandler(tableService));
        handlers.put("PLAY", new PlayCommandHandler(tableService, turnManagerService));
        handlers.put("END_TURN", new EndTurnCommandHandler(turnManagerService));
        handlers.put("FLIP_DIRECTION", new FlipDirectionCommandHandler(turnManagerService));
        
        // Action Card Handlers
        handlers.put("PLAY_SKIP", new PlaySkipCommandHandler(tableService, turnManagerService));
        handlers.put("PLAY_REVERSE", new PlayReverseCommandHandler(tableService, turnManagerService));
        handlers.put("PLAY_PLUS_TWO", new PlayPlusTwoCommandHandler(tableService, turnManagerService));
        handlers.put("PLAY_PLUS_FOUR", new PlayPlusFourCommandHandler(tableService, turnManagerService));
    }

    /**
     * Retrieves the handler registered for a specific command.
     *
     * @param command The command whose handler will be retrieved.
     * @return The {@link CommandHandler} associated with the command action.
     * @throws IllegalArgumentException if the command action is not registered.
     */
    public CommandHandler getHandler( Command command ){
        String key = command.getAction();
        CommandHandler handler = handlers.get(key);
        if( handler == null )
            throw new IllegalArgumentException("Comando '" + key + "' não encontrado.");

        return handler;
    }
}