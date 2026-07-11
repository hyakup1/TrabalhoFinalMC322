package com.uno.service.core.command.handler;

import com.uno.exception.JogadaInvalidaException;
import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for playing a card from the current player's hand.
 */
public class PlayCommandHandler implements CommandHandler {
    private final TableService tableService;

    /**
     * Constructs a new PlayCommandHandler.
     *
     * @param tableService The service used to play cards.
     */
    public PlayCommandHandler( TableService tableService ){
        this.tableService = tableService;
    }

    /**
     * Plays the card described by the command parameters.
     *
     * @param command The command containing the card symbol and color.
     */
    @Override
    public void handle( Command command ){
        String[] parameters = command.getParameters();
        if( parameters.length != 2 || parameters[0].isBlank() || parameters[1].isBlank() )
            throw new IllegalArgumentException("Uso do comando: PLAY;símbolo;cor. ");

        try{
            tableService.play(parameters[0], parameters[1]);
        }
        catch( JogadaInvalidaException e ){
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Override
    public String handleWithOutput( Command command ){
        handle(command);
        return "Carta jogada. ";
    }
}
