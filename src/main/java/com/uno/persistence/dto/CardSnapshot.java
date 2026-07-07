package com.uno.persistence.dto;

/**
 * A lightweight snapshot of a card for persistence.
 *
 * @param symbol the string representation of the card's symbol
 * @param color  the string representation of the card's color
 */
public record CardSnapshot(String symbol, String color) {}
