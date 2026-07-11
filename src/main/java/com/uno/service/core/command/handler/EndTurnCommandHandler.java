package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for ending the current player's turn.
 */
public class EndTurnCommandHandler implements CommandHandler {
    private final TurnManagerService turnManagerService;

    /**
     * Constructs a new EndTurnCommandHandler.
     *
     * @param turnManagerService The service used to end turns.
     */
    public EndTurnCommandHandler( TurnManagerService turnManagerService ){
        this.turnManagerService = turnManagerService;
    }

    /**
     * Ends the current player's turn.
     *
     * @param command The command to handle.
     */
    @Override
    public void handle( Command command ){
        execute(command);
    }

    @Override
    public String handleWithOutput( Command command ){
        return execute(command);
    }

    private String execute( Command command ){
        if( command.getParameters().length != 0 )
            throw new IllegalArgumentException("Uso do comando: END_TURN. ");

        return turnManagerService.endTurn();
    }
}
