package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for reversing the direction of play.
 */
public class FlipDirectionCommandHandler implements CommandHandler {
    private final TurnManagerService turnManagerService;

    /**
     * Constructs a new FlipDirectionCommandHandler.
     *
     * @param turnManagerService The service used to reverse the direction of play.
     */
    public FlipDirectionCommandHandler( TurnManagerService turnManagerService ){
        this.turnManagerService = turnManagerService;
    }

    /**
     * Reverses the current direction of play.
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
            throw new IllegalArgumentException("Uso do comando: FLIP_DIRECTION. ");

        return "Direção alterada para " + turnManagerService.flipDirection() + ". ";
    }
}
