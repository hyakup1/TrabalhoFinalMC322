package com.uno.service.core;

import com.uno.model.entity.Card;
import com.uno.model.entity.Table;

public class TableService {
    private final Table table; 

    public TableService( Table table ){
        this.table = table; 
    }

    public void draw(){
        table.draw();
    }

    public void drawN( int N ){
        table.drawN(N);
    }

    public void play( String symbolName, String colorName ){
        Card card = new Card( symbolName, colorName );
        table.play(card);
    }
}
