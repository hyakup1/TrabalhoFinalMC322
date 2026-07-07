package com.uno.persistence;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uno.exception.PersistenceException;
import com.uno.exception.SaveNotFoundException;
import com.uno.persistence.dto.GameStateSnapshot;

/**
 * Concrete implementation of GameRepository that stores game states as JSON files.
 * <p>
 * Saves are stored in a "saves/" folder relative to the current working directory.
 * Path traversal is prevented through string sanitization, and preconditions are
 * validated using fail-fast principles.
 */
public class JsonGameRepository implements GameRepository {

    private final Path savesDirectory;
    private final JsonFileStore<GameStateSnapshot> fileStore;

    /**
     * Constructs a JsonGameRepository using the default "saves/" directory.
     */
    public JsonGameRepository() {
        this(Paths.get("saves"));
    }

    /**
     * Constructs a JsonGameRepository using a custom directory.
     *
     * @param savesDirectory the folder where save files are stored
     */
    public JsonGameRepository(Path savesDirectory) {
        if (savesDirectory == null) {
            throw new IllegalArgumentException("O diretório de saves não pode ser nulo.");
        }
        this.savesDirectory = savesDirectory;
        this.fileStore = new JsonFileStore<>();
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        try {
            if (!Files.exists(savesDirectory)) {
                Files.createDirectories(savesDirectory);
            }
        } catch (IOException e) {
            // Unchecked exception for critical configuration failure
            throw new IllegalStateException("Não foi possível criar o diretório de saves: " + savesDirectory, e);
        }
    }

    private String sanitizeSaveName(String saveName) {
        if (saveName == null || saveName.isBlank()) {
            throw new IllegalArgumentException("O nome do save não pode ser nulo ou vazio.");
        }
        // Remove sequence for path traversal
        String sanitized = saveName.replace("..", "")
                                   .replace("/", "")
                                   .replace("\\", "");
        if (sanitized.isBlank()) {
            throw new IllegalArgumentException("O nome do save contém apenas caracteres inválidos.");
        }
        return sanitized;
    }

    private Path getSavePath(String saveName) {
        String sanitized = sanitizeSaveName(saveName);
        return savesDirectory.resolve(sanitized + ".json");
    }

    @Override
    public void save(GameStateSnapshot state, String saveName) throws PersistenceException {
        if (state == null) {
            throw new IllegalArgumentException("O estado do jogo não pode ser nulo.");
        }
        Path targetPath = getSavePath(saveName);
        fileStore.save(state, targetPath);
    }

    @Override
    public GameStateSnapshot load(String saveName) throws SaveNotFoundException, PersistenceException {
        Path targetPath = getSavePath(saveName);
        // Explicitly check for existence beforehand to satisfy fail-fast & custom exception requirements
        if (!Files.exists(targetPath) || !Files.isRegularFile(targetPath)) {
            throw new SaveNotFoundException("O save com o nome '" + sanitizeSaveName(saveName) + "' não existe.");
        }
        return fileStore.load(targetPath, GameStateSnapshot.class);
    }

    @Override
    public List<String> listSaves() {
        ensureDirectoryExists();
        List<String> saves = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(savesDirectory, "*.json")) {
            for (Path entry : stream) {
                String filename = entry.getFileName().toString();
                // Strip the .json extension
                if (filename.endsWith(".json")) {
                    saves.add(filename.substring(0, filename.length() - 5));
                }
            }
        } catch (IOException e) {
            // Fail silently or return what we have in case of IO error on listing
            return Collections.emptyList();
        }
        return saves;
    }

    @Override
    public void delete(String saveName) throws SaveNotFoundException {
        Path targetPath = getSavePath(saveName);
        if (!Files.exists(targetPath) || !Files.isRegularFile(targetPath)) {
            throw new SaveNotFoundException("O save com o nome '" + sanitizeSaveName(saveName) + "' não existe para exclusão.");
        }
        try {
            Files.delete(targetPath);
        } catch (IOException e) {
            // Unchecked error since we verified existence but deletion failed (e.g. permission lock)
            throw new IllegalStateException("Falha ao deletar o arquivo de save: " + targetPath.getFileName(), e);
        }
    }
}
