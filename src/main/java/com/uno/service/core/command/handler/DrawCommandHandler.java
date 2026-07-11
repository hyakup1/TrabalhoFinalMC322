package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for drawing one card from the deck.
 */
public class DrawCommandHandler implements CommandHandler {
    private final TableService tableService;

    /**
     * Constructs a new DrawCommandHandler.
     *
     * @param tableService The service used to draw a card.
     */
    public DrawCommandHandler( TableService tableService ){
        this.tableService = tableService;
    }

    /**
     * Draws one card for the current player.
     *
     * @param command The command to handle.
     */
    @Override
    public void handle( Command command ){
        if( command.getParameters().length != 0 )
            throw new IllegalArgumentException("Uso do comando: DRAW. ");

        tableService.draw();
    }

    @Override
    public String handleWithOutput( Command command ){
        handle(command);
        return "Carta comprada. ";
    }
}
