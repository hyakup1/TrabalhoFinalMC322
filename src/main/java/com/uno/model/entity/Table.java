package com.uno.model.entity;

public class Table {
    private final Deck deck = new Deck();
    private final TurnManager turnManager = new TurnManager();
    private final int initialCardCount;

    public Table( int initialCardCount ){
        this.initialCardCount = initialCardCount;
    }

    public void addPlayer( Player player ){
        for( int i = 0; i < initialCardCount; i++ )
            player.draw(deck.draw());

        turnManager.addPlayer(player);
    }

    public void draw(){
        Player player = turnManager.getTurnPlayer();
        player.draw(deck.draw());
    }

    public void drawN( int N ){
        for( int i = 0; i < N; i++ ) draw();
    }

    public void play( Card card ){
        Player player = turnManager.getTurnPlayer();
        if( !player.hasCard(card) ) 
            throw new IllegalArgumentException("Jogada inválida: O jogador não possui a carta " + card.toString() );

        deck.play(card);
        player.play(card);
    }
    
    public TurnManager getTurnManager(){
        return turnManager;
    }
}
