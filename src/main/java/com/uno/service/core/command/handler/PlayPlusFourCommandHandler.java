package com.uno.service.core.command.handler;

import com.uno.exception.JogadaInvalidaException;
import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for playing a +4 (PLUS_FOUR) card from the current player's hand,
 * forcing the next player to draw 4 cards and skipping their turn.
 */
public class PlayPlusFourCommandHandler implements CommandHandler {
    private final TableService tableService;
    private final TurnManagerService turnManagerService;

    /**
     * Constructs a new PlayPlusFourCommandHandler.
     *
     * @param tableService The service used to play and draw cards.
     * @param turnManagerService The service used to manipulate and end turns.
     */
    public PlayPlusFourCommandHandler( TableService tableService, TurnManagerService turnManagerService ){
        this.tableService = tableService;
        this.turnManagerService = turnManagerService;
    }

    /**
     * Plays the +4 card described by the command parameters, passes the turn to the next player,
     * forces them to draw 4 cards, and immediately ends their turn as well.
     *
     * @param command The command containing the card symbol and color.
     * @throws JogadaInvalidaException if the current player cannot play the specified card.
     */
    @Override
    public void handle( Command command ) throws JogadaInvalidaException {
        String[] parameters = command.getParameters();
        if( parameters.length != 2 || parameters[0].isBlank() || parameters[1].isBlank() )
            throw new IllegalArgumentException("Uso do comando: PLAY_PLUS_FOUR;símbolo;cor. ");

        tableService.play(parameters[0], parameters[1]);
        turnManagerService.endTurn();
        tableService.drawN(4);
        turnManagerService.endTurn();
    }

    @Override
    public String handleWithOutput( Command command ) throws JogadaInvalidaException {
        handle(command);
        return "Carta jogada. Próximo jogador comprou 4 cartas e perdeu a vez! ";
    }
}