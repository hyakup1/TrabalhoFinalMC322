package com.uno.service.core.command.handler;

import com.uno.exception.JogadaInvalidaException;
import com.uno.model.Command;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for playing a reverse card from the current player's hand,
 * reversing the direction of play, and ending the current player's turn.
 */
public class PlayReverseCommandHandler implements CommandHandler {
    private final TableService tableService;
    private final TurnManagerService turnManagerService;

    /**
     * Constructs a new PlayReverseCommandHandler.
     *
     * @param tableService The service used to play cards.
     * @param turnManagerService The service used to reverse the direction of play and end turns.
     */
    public PlayReverseCommandHandler( TableService tableService, TurnManagerService turnManagerService ){
        this.tableService = tableService;
        this.turnManagerService = turnManagerService;
    }

    /**
     * Plays the reverse card described by the command parameters, reverses
     * the current direction of play, and ends the current player's turn.
     *
     * @param command The command containing the card symbol and color.
     * @throws JogadaInvalidaException if the current player cannot play the specified card.
     */
    @Override
    public void handle( Command command ) throws JogadaInvalidaException {
        execute(command);
    }

    @Override
    public String handleWithOutput( Command command ) throws JogadaInvalidaException {
        return execute(command);
    }

    /**
     * Helper method to validate parameters, play the card, flip the game direction, and end the turn.
     *
     * @param command The command to execute.
     * @return The formatted output string containing the new play direction.
     * @throws JogadaInvalidaException if the play is invalid.
     */
    private String execute( Command command ) throws JogadaInvalidaException {
        String[] parameters = command.getParameters();
        if( parameters.length != 2 || parameters[0].isBlank() || parameters[1].isBlank() )
            throw new IllegalArgumentException("Uso do comando: PLAY_REVERSE;símbolo;cor. ");

        tableService.play(parameters[0], parameters[1]);
        String novaDirecao = turnManagerService.flipDirection();
        turnManagerService.endTurn();

        return "Carta jogada. Direção alterada para " + novaDirecao + ". Turno finalizado. ";
    }
}