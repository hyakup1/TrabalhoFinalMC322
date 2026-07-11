package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.service.core.PlayerService;
import com.uno.service.core.command.CommandHandler;

/**
 * Command handler responsible for adding players to the game.
 */
public class AddPlayerCommandHandler implements CommandHandler {
    private final PlayerService playerService;

    /**
     * Constructs a new AddPlayerCommandHandler.
     *
     * @param playerService The service used to add players.
     */
    public AddPlayerCommandHandler( PlayerService playerService ){
        this.playerService = playerService;
    }

    /**
     * Adds the player provided in the command parameters.
     *
     * @param command The command containing the player's name.
     */
    @Override
    public void handle( Command command ){
        String[] parameters = command.getParameters();
        if( parameters.length != 1 || parameters[0].isBlank() )
            throw new IllegalArgumentException("Uso do comando: ADD_PLAYER;nome. ");

        playerService.addPlayer(parameters[0]);
    }

    @Override
    public String handleWithOutput( Command command ){
        handle(command);
        return "Jogador adicionado. ";
    }
}
