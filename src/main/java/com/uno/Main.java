package com.uno;

import com.uno.controller.CommandDispatcher;
import com.uno.controller.GameView;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
import com.uno.persistence.JsonGameRepository;
import com.uno.persistence.GameStateMapper;
import com.uno.service.core.PlayerService;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;
import com.uno.service.core.command.CommandFactory;
import com.uno.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(7);
        PlayerService playerService = new PlayerService(table);
        TurnManagerService turnManagerService = new TurnManagerService(table);
        TableService tableService = new TableService(table);

        CommandFactory commandFactory = new CommandFactory(playerService, tableService, turnManagerService);
        CommandDispatcher commandDispatcher = new CommandDispatcher(commandFactory);
        GameView view = new ConsoleView();

        JsonGameRepository repository = new JsonGameRepository();
        boolean loaded = false;
        if (repository.listSaves().contains("latest")) {
            view.showMessage("Um jogo anterior salvo foi detectado. Deseja continuar? (S/N): ");
            String resposta = view.readCommand();
            if (resposta != null && (resposta.trim().equalsIgnoreCase("S") || 
                                     resposta.trim().equalsIgnoreCase("SIM") || 
                                     resposta.trim().equalsIgnoreCase("Y") || 
                                     resposta.trim().equalsIgnoreCase("YES"))) {
                try {
                    commandDispatcher.process("LOAD");
                    loaded = true;
                } catch (Exception e) {
                    view.showError("Falha ao carregar o jogo salvo: " + e.getMessage());
                }
            }
        }

        if (!loaded) {
            // Adding players via commands for standardization
            try {
                commandDispatcher.process("ADD_PLAYER ; Arthur");
                commandDispatcher.process("ADD_PLAYER ; Caique");
                commandDispatcher.process("ADD_PLAYER ; Enzo");
                commandDispatcher.process("ADD_PLAYER ; Pedro");
            } catch (Exception e) {
                view.showError("Falha na inicialização do jogo: " + e.getMessage());
                return;
            }
        }

        // Game Loop
        boolean isGameOver = false;
        while (!isGameOver) {
            view.clearScreen();
            Table activeTable = tableService.getTable();
            view.showGameState(activeTable);

            try {
                String input = view.readCommand();
                String output = commandDispatcher.process(input);
                view.showMessage(output);

                // Auto-save on any successful input command
                try {
                    repository.save(GameStateMapper.toSnapshot(tableService.getTable()), "latest");
                } catch (Exception e) {
                    // Silently ignore auto-save failures
                }
            } catch (Exception e) {
                view.showError(e.getMessage());
            }

            // Check game over condition
            for (Player player : playerService.getPlayers()) {
                if (player.isHandEmpty()) {
                    isGameOver = true;
                    view.showMessage("Jogo finalizado! Vencedor: " + player.getName());
                    // Delete the save file when the game is finished
                    try {
                        repository.delete("latest");
                    } catch (Exception e) {
                        // Silently ignore deletion error
                    }
                    break;
                }
            }

            view.showMessage("Pressione ENTER para continuar. "); 
            view.readCommand();
        }
    }
}