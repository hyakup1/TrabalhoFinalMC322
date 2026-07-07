package com.uno.persistence.dto;

import java.util.List;

/**
 * A lightweight snapshot of a player for persistence.
 *
 * @param name the player's name
 * @param hand the player's current hand of cards as CardSnapshots
 */
public record PlayerSnapshot(String name, List<CardSnapshot> hand) {}
