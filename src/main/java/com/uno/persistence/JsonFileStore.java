package com.uno.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.uno.exception.PersistenceException;
import com.uno.exception.SaveNotFoundException;

/**
 * Generic utility store for saving and loading objects from files in JSON format.
 * <p>
 * This class uses Gson for serialization and deserialization. It is fully generic,
 * satisfying the parametric polymorphism requirement. It catches external library
 * exceptions and re-wraps them in the domain-specific persistence exceptions.
 *
 * @param <T> the type of object managed by this store
 */
class JsonFileStore<T> {

    private final Gson gson;

    /**
     * Constructs a new JsonFileStore with pretty-printing enabled.
     */
    public JsonFileStore() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Saves the given object to the specified path in JSON format.
     *
     * @param obj  the object to serialize and save
     * @param path the path to save the file to
     * @throws PersistenceException if saving fails due to an I/O issue or serialization error
     */
    public void save(T obj, Path path) throws PersistenceException {
        if (obj == null) {
            throw new PersistenceException("Cannot save a null object.");
        }
        if (path == null) {
            throw new PersistenceException("Target path cannot be null.");
        }

        try {
            // Ensure parent directories exist
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                gson.toJson(obj, writer);
            }
        } catch (IOException | JsonSyntaxException e) {
            throw new PersistenceException("Failed to save state to " + path.getFileName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Loads an object of the specified type from the given JSON file path.
     *
     * @param path the path to load the file from
     * @param type the Class object representing type T
     * @return the deserialized object
     * @throws PersistenceException  if loading fails due to a corrupt file or structural issue
     * @throws SaveNotFoundException if the file does not exist at the specified path
     */
    public T load(Path path, Class<T> type) throws PersistenceException, SaveNotFoundException {
        if (path == null) {
            throw new PersistenceException("Path to load cannot be null.");
        }

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new SaveNotFoundException("O arquivo de save " + path.getFileName() + " não foi encontrado.");
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            T obj = gson.fromJson(reader, type);
            if (obj == null) {
                throw new PersistenceException("Loaded file is empty: " + path.getFileName());
            }
            return obj;
        } catch (IOException | JsonSyntaxException e) {
            throw new PersistenceException("Falha ao carregar o save do arquivo " + path.getFileName() + ": " + e.getMessage(), e);
        }
    }
}
