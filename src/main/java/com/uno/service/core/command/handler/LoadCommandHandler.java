package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.model.entity.Table;
import com.uno.persistence.JsonGameRepository;
import com.uno.persistence.GameStateMapper;
import com.uno.persistence.dto.GameStateSnapshot;
import com.uno.service.core.TableService;
import com.uno.service.core.PlayerService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandHandler;
import com.uno.exception.SaveNotFoundException;
import com.uno.exception.PersistenceException;

/**
 * Command handler responsible for loading the saved game state.
 */
public class LoadCommandHandler implements CommandHandler {
    private final TableService tableService;
    private final PlayerService playerService;
    private final TurnManagerService turnManagerService;
    private final JsonGameRepository repository;

    /**
     * Constructs a new LoadCommandHandler.
     *
     * @param tableService       The service for table operations.
     * @param playerService       The service for player operations.
     * @param turnManagerService The service for turn operations.
     * @param repository         The repository used to load game states.
     */
    public LoadCommandHandler(TableService tableService, PlayerService playerService, 
                              TurnManagerService turnManagerService, JsonGameRepository repository) {
        this.tableService = tableService;
        this.playerService = playerService;
        this.turnManagerService = turnManagerService;
        this.repository = repository;
    }

    @Override
    public void handle(Command command) {
        if (command.getParameters().length != 0) {
            throw new IllegalArgumentException("Uso do comando: LOAD. ");
        }
        try {
            GameStateSnapshot snapshot = repository.load("latest");
            Table loadedTable = GameStateMapper.fromSnapshot(snapshot);
            
            // Update services with the new table
            tableService.setTable(loadedTable);
            playerService.setTable(loadedTable);
            turnManagerService.setTable(loadedTable);
        } catch (SaveNotFoundException e) {
            throw new IllegalArgumentException("Nenhum jogo salvo encontrado.", e);
        } catch (PersistenceException e) {
            throw new RuntimeException("Falha ao carregar o jogo: " + e.getMessage(), e);
        }
    }

    @Override
    public String handleWithOutput(Command command) {
        handle(command);
        return "Jogo carregado com sucesso! ";
    }
}
