package com.uno.persistence.dto;

import java.util.List;

/**
 * A snapshot representing the entire state of the Uno game.
 *
 * @param initialCardCount the initial count of cards distributed to each player
 * @param drawPile         the cards currently in the draw pile
 * @param discardPile      the cards currently in the discard pile
 * @param players          the players registered at the table and their hands
 * @param currentIndex     the index of the player whose turn it currently is
 * @param direction        the direction of turn progression (1 or -1)
 */
public record GameStateSnapshot(
    int initialCardCount,
    List<CardSnapshot> drawPile,
    List<CardSnapshot> discardPile,
    List<PlayerSnapshot> players,
    int currentIndex,
    int direction
) {}
