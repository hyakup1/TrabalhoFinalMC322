package com.uno.service.core;

import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
import com.uno.model.entity.TurnManager;

public class TurnManagerService {
    private final TurnManager turnManager; 

    public TurnManagerService( Table table ){
        this.turnManager = table.getTurnManager();
    }

    public String flipDirection(){
        turnManager.flipDirection();
        return turnManager.getDirectionStr();
    }

    public String endTurn(){
        return turnManager.endTurn();
    }

    public Player getCurrentPlayer(){
        return turnManager.getTurnPlayer();
    }
}
