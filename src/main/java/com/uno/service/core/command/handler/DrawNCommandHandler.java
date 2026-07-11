package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for drawing a specific amount of cards.
 */
public class DrawNCommandHandler implements CommandHandler {
    private final TableService tableService;

    /**
     * Constructs a new DrawNCommandHandler.
     *
     * @param tableService The service used to draw cards.
     */
    public DrawNCommandHandler( TableService tableService ){
        this.tableService = tableService;
    }

    /**
     * Draws the amount of cards provided in the command parameters.
     *
     * @param command The command containing the amount of cards.
     */
    @Override
    public void handle( Command command ){
        String[] parameters = command.getParameters();
        if( parameters.length != 1 )
            throw new IllegalArgumentException("Uso do comando: DRAW_N;quantidade. ");

        try{
            int amount = Integer.parseInt(parameters[0]);
            if( amount <= 0 )
                throw new IllegalArgumentException("A quantidade de cartas deve ser positiva. ");

            tableService.drawN(amount);
        }
        catch( NumberFormatException e ){
            throw new IllegalArgumentException("A quantidade de cartas deve ser um número inteiro. ", e);
        }
    }

    @Override
    public String handleWithOutput( Command command ){
        handle(command);
        return "Cartas compradas. ";
    }
}
