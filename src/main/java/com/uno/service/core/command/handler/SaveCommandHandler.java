package com.uno.service.core.command.handler;

import com.uno.model.Command;
import com.uno.model.entity.Table;
import com.uno.persistence.JsonGameRepository;
import com.uno.persistence.GameStateMapper;
import com.uno.persistence.dto.GameStateSnapshot;
import com.uno.service.core.TableService;
import com.uno.service.core.command.CommandHandler;
import com.uno.exception.PersistenceException;

/**
 * Command handler responsible for saving the current game state.
 */
public class SaveCommandHandler implements CommandHandler {
    private final TableService tableService;
    private final JsonGameRepository repository;

    /**
     * Constructs a new SaveCommandHandler.
     *
     * @param tableService The service used to get the current table.
     * @param repository   The repository used to save game states.
     */
    public SaveCommandHandler(TableService tableService, JsonGameRepository repository) {
        this.tableService = tableService;
        this.repository = repository;
    }

    @Override
    public void handle(Command command) {
        if (command.getParameters().length != 0) {
            throw new IllegalArgumentException("Uso do comando: SAVE. ");
        }
        try {
            Table table = tableService.getTable();
            GameStateSnapshot snapshot = GameStateMapper.toSnapshot(table);
            repository.save(snapshot, "latest");
        } catch (PersistenceException e) {
            throw new RuntimeException("Falha ao salvar o jogo: " + e.getMessage(), e);
        }
    }

    @Override
    public String handleWithOutput(Command command) {
        handle(command);
        return "Jogo salvo com sucesso. ";
    }
}
