package com.uno.persistence;

import java.util.List;

import com.uno.model.entity.Card;
import com.uno.model.entity.Deck;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
import com.uno.model.entity.TurnManager;
import com.uno.persistence.dto.CardSnapshot;
import com.uno.persistence.dto.GameStateSnapshot;
import com.uno.persistence.dto.PlayerSnapshot;

/**
 * Utility mapper class for converting between domain objects and data transfer snapshots.
 * <p>
 * This acts as the boundary or coupling point between the persistence layer DTOs and
 * the core game domain entity classes.
 */
public final class GameStateMapper {

    private GameStateMapper() {
        // Prevent instantiation of utility class
    }

    /**
     * Converts a game Table entity to a persistent GameStateSnapshot.
     *
     * @param table the active game table
     * @return a GameStateSnapshot representing the current game state
     */
    public static GameStateSnapshot toSnapshot(Table table) {
        TurnManager turnManager = table.getTurnManager();
        Deck deck = table.getDeck();

        List<PlayerSnapshot> players = turnManager.getPlayers().stream()
            .map(GameStateMapper::toPlayerSnapshot)
            .toList();

        return new GameStateSnapshot(
            table.getInitialCardCount(),
            toCardSnapshots(deck.getDrawPile()),
            toCardSnapshots(deck.getDiscardPile()),
            players,
            turnManager.getCurrentIndex(),
            turnManager.getDirection()
        );
    }

    /**
     * Reconstructs a game Table entity from a GameStateSnapshot.
     *
     * @param snapshot the game state snapshot to restore from
     * @return the reconstructed Table entity
     */
    public static Table fromSnapshot(GameStateSnapshot snapshot) {
        Deck deck = new Deck(
            fromCardSnapshots(snapshot.drawPile()),
            fromCardSnapshots(snapshot.discardPile())
        );

        List<Player> players = snapshot.players().stream()
            .map(GameStateMapper::fromPlayerSnapshot)
            .toList();
        TurnManager turnManager = new TurnManager(players, snapshot.currentIndex(), snapshot.direction());

        return new Table(snapshot.initialCardCount(), deck, turnManager);
    }

    private static PlayerSnapshot toPlayerSnapshot(Player player) {
        return new PlayerSnapshot(player.getName(), toCardSnapshots(player.getHand()));
    }

    private static Player fromPlayerSnapshot(PlayerSnapshot snapshot) {
        Player player = new Player(snapshot.name());
        fromCardSnapshots(snapshot.hand()).forEach(player::draw);
        return player;
    }

    private static List<CardSnapshot> toCardSnapshots(List<Card> cards) {
        return cards.stream()
            .map(c -> new CardSnapshot(c.getSymbol().name(), c.getColor().name()))
            .toList();
    }

    private static List<Card> fromCardSnapshots(List<CardSnapshot> snapshots) {
        return snapshots.stream()
            .map(s -> new Card(s.symbol(), s.color()))
            .toList();
    }
}
