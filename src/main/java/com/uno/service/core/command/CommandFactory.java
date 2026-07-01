package com.uno.service.core.command;

import java.util.HashMap;
import java.util.Map;

import com.uno.model.Command;

public class CommandFactory {
    private Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandFactory() {

        /*
            Inserir handlers. Ex:
            handlers.put("LIST_TASKS", new ListTasksHandler(reportService));
        */
    }

    public CommandHandler getHandler(Command command) {
        String key = command.getAction();
        CommandHandler handler = handlers.get(key);
        if (handler == null) {
            throw new IllegalArgumentException("Comando '" + key + "' não encontrado.");
        }
        return handler;
    }
}