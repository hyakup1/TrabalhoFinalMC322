package com.uno.persistence;

import java.util.List;

import com.uno.exception.PersistenceException;
import com.uno.exception.SaveNotFoundException;
import com.uno.persistence.dto.GameStateSnapshot;

/**
 * Interface representing the repository contract for saving and loading Uno game states.
 * <p>
 * This acts as the port in the hexagonal or ports-and-adapters architecture style,
 * decoupling the domain from the concrete data storage mechanism.
 */
public interface GameRepository {
    
    /**
     * Saves the given game snapshot to a save slot or name.
     *
     * @param state      the game state snapshot to save
     * @param saveName   the name of the save file/slot
     * @throws PersistenceException if the save operation fails
     */
    void save(GameStateSnapshot state, String saveName) throws PersistenceException;
    
    /**
     * Loads a game snapshot from a save slot or name.
     *
     * @param saveName   the name of the save file/slot to load
     * @return the loaded GameStateSnapshot
     * @throws SaveNotFoundException if the specified save does not exist
     * @throws PersistenceException  if the load operation fails due to formatting or other errors
     */
    GameStateSnapshot load(String saveName) throws SaveNotFoundException, PersistenceException;
    
    /**
     * Lists the names of all available save files/slots.
     *
     * @return a List of save slot names
     */
    List<String> listSaves();
    
    /**
     * Deletes the specified save file/slot.
     *
     * @param saveName   the name of the save to delete
     * @throws SaveNotFoundException if the save file does not exist
     */
    void delete(String saveName) throws SaveNotFoundException;
}
