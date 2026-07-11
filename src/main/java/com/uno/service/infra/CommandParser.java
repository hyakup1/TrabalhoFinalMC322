package com.uno.service.infra;

import java.util.Arrays;

import com.uno.model.Command;

/**
 * Utility class for parsing command lines into Command objects.
 */
public class CommandParser {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CommandParser() {
    }

    /**
     * Parses a command line into a Command object.
     *
     * @param line the command line to parse
     * @return the parsed Command object
     * @throws IllegalArgumentException if the command line is null or blank
     */
    public static Command parseCommand( String line ){
        if( line == null || line.isBlank() )
            throw new IllegalArgumentException("O comando não pode ser vazio. ");

        String[] parts = line.split(";", -1);
        String action = parts[0].trim();
        if( action.isBlank() )
            throw new IllegalArgumentException("A ação do comando não pode ser vazia. ");

        String[] parameters = Arrays.stream(parts)
            .skip(1)
            .map(String::trim)
            .toArray(String[]::new);

        return new Command(line, action, parameters);
    }
}
