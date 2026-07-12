package com.uno.service.core.command.handler;

import com.uno.exception.JogadaInvalidaException;
import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for playing a skip card from the current player's hand,
 * ending their turn, and immediately ending the next player's turn.
 */
public class PlaySkipCommandHandler implements CommandHandler {
    private final TableService tableService;
    private final TurnManagerService turnManagerService;

    /**
     * Constructs a new PlaySkipCommandHandler.
     *
     * @param tableService The service used to play cards.
     * @param turnManagerService The service used to manipulate and end turns.
     */
    public PlaySkipCommandHandler( TableService tableService, TurnManagerService turnManagerService ){
        this.tableService = tableService;
        this.turnManagerService = turnManagerService;
    }

    /**
     * Plays the skip card described by the command parameters, ends the current
     * player's turn, and immediately skips the next player by ending their turn as well.
     *
     * @param command The command containing the card symbol and color.
     * @throws JogadaInvalidaException if the current player cannot play the specified card.
     */
    @Override
    public void handle( Command command ) throws JogadaInvalidaException {
        String[] parameters = command.getParameters();
        if( parameters.length != 2 || parameters[0].isBlank() || parameters[1].isBlank() )
            throw new IllegalArgumentException("Uso do comando: PLAY_SKIP;símbolo;cor. ");

        tableService.play(parameters[0], parameters[1]);
        turnManagerService.endTurn();
        turnManagerService.endTurn();
    }

    @Override
    public String handleWithOutput( Command command ) throws JogadaInvalidaException {
        handle(command);
        return "Carta jogada. Próximo jogador pulado! ";
    }
}