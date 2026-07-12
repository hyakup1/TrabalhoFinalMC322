package com.uno.view;

import java.util.List;
import java.util.Scanner;

import com.uno.controller.GameView;
import com.uno.model.entity.Card;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;

/**
 * Concrete implementation of GameView that uses the console (standard I/O)
 * for player interaction, keeping business logic clear of view specifics.
 */
public class ConsoleView implements GameView {

    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showGameState(Table table) {
        Player currentPlayer = table.getTurnManager().getTurnPlayer();
        Card topCard = table.getDeck().getDiscardPile().getLast();
        List<Card> hand = currentPlayer.getHand();

        System.out.println("======================================");
        System.out.println("Sentido do Jogo: " + table.getTurnManager().getDirectionStr());
        System.out.println("Carta no Topo do Descarte: [" + topCard.toString() + "]");
        System.out.println("--------------------------------------");
        System.out.println("Turno de: " + currentPlayer.getName());
        System.out.println("Sua Mão:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("  " + i + ": " + hand.get(i).toString());
        }
        System.out.println("--------------------------------------");
        System.out.println("Comandos Disponíveis:");
        System.out.println("  DRAW                             - Comprar uma carta");
        System.out.println("  PLAY ; <SÍMBOLO> ; <COR>         - Jogar uma carta normal (ex: PLAY;ONE;RED)");
        System.out.println("  PLAY_SKIP ; SKIP ; <COR>         - Jogar carta Bloqueio");
        System.out.println("  PLAY_REVERSE ; REVERSE ; <COR>   - Jogar carta Inverter");
        System.out.println("  PLAY_PLUS_TWO ; PLUS_TWO ; <COR> - Jogar carta +2");
        System.out.println("  PLAY_PLUS_FOUR ; PLUS_FOUR ; <COR_ESCOLHIDA> - Jogar carta +4");
        System.out.println("  PLAY ; CHANGE_COLOR ; <COR_ESCOLHIDA>        - Jogar carta de Mudar Cor");
        System.out.println("  END_TURN                         - Passar a vez");
        System.out.println("======================================");
    }

    @Override
    public String readCommand() {
        System.out.print("Digite seu comando (ação;parametros...): ");
        return scanner.nextLine();
    }

    @Override
    public void showMessage(String message) {
        if (message != null && !message.isBlank()) {
            System.out.println("--> " + message);
        }
    }

    @Override
    public void showError(String errorMessage) {
        System.err.println("[ERRO] " + errorMessage);
    }

    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}