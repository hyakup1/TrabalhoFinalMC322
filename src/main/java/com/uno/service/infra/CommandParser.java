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
     */
    public static Command parseCommand(String line) {
        String[] parts = line.split(";");
        String action = parts[0];
        String[] parameters = Arrays.stream(parts).skip(1).toArray(String[]::new);
        return new Command(line, action, parameters);
    }
}