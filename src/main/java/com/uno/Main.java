package com.uno;

import com.uno.model.entity.Table;
import com.uno.service.core.PlayerService;
import com.uno.service.core.TableService;
import com.uno.service.core.TurnManagerService;

public class Main {
    public static void main( String[] args ){
        Table table = new Table(7);
        PlayerService playerService = new PlayerService(table);
        TurnManagerService turnManagerService = new TurnManagerService(table);
        TableService tableService = new TableService(table);

        playerService.addPlayer("Arthur");
        playerService.addPlayer("Caique");
        playerService.addPlayer("Enzo");
        playerService.addPlayer("Pedro");
    }
}