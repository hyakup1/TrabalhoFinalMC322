package com.uno;

import com.uno.controller.CommandDispatcher;
import com.uno.controller.GameView;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
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

        // Game Loop
        boolean isGameOver = false;
        while (!isGameOver) {
            view.clearScreen();
            view.showGameState(table);

            try {
                String input = view.readCommand();
                String output = commandDispatcher.process(input);
                view.showMessage(output);
            } catch (Exception e) {
                view.showError(e.getMessage());
            }

            // Check game over condition
            for (Player player : playerService.getPlayers()) {
                if (player.isHandEmpty()) {
                    isGameOver = true;
                    view.showMessage("Jogo finalizado! Vencedor: " + player.getName());
                    break;
                }
            }

            view.showMessage("Pressione ENTER para continuar. "); 
            view.readCommand();
        }
    }
}