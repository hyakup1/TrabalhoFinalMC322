package com.uno.persistence;

import java.util.List;

import com.uno.model.entity.Card;
import com.uno.model.entity.Player;
import com.uno.model.entity.Table;
import com.uno.persistence.dto.GameStateSnapshot;

/**
 * Sanity check / demonstration class for the persistence module.
 * <p>
 * This class runs a simple demo to verify that game states can be successfully
 * converted to snapshots, saved to JSON files, loaded back, and mapped back to Table entities.
 */
public class PersistenceDemo {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Demo de Persistência ===");

        try {
            // 1. Setup a game table
            Table originalTable = new Table(7);
            
            Player arthur = new Player("Arthur");
            Player enzo = new Player("Enzo");
            originalTable.addPlayer(arthur);
            originalTable.addPlayer(enzo);

            Card originalTopDiscard = originalTable.getDeck().getDiscardPile().getLast();
            int originalDrawPileSize = originalTable.getDeck().getDrawPile().size();
            List<Card> arthurHand = arthur.getHand();

            System.out.println("Original Top Discard: " + originalTopDiscard);
            System.out.println("Original Draw Pile Size: " + originalDrawPileSize);
            System.out.println("Arthur's Hand: " + arthurHand);

            // 2. Map to Snapshot and Save
            GameStateSnapshot snapshot = GameStateMapper.toSnapshot(originalTable);
            JsonGameRepository repository = new JsonGameRepository();
            String saveName = "demo_save";
            repository.save(snapshot, saveName);
            System.out.println("Jogo salvo com sucesso no slot: '" + saveName + "'");

            // 3. Load snapshot back and reconstruct Table
            System.out.println("Listando saves disponíveis: " + repository.listSaves());
            GameStateSnapshot loadedSnapshot = repository.load(saveName);
            Table loadedTable = GameStateMapper.fromSnapshot(loadedSnapshot);
            System.out.println("Jogo carregado do slot: '" + saveName + "'");

            // 4. Verify reconstructed state
            Card loadedTopDiscard = loadedTable.getDeck().getDiscardPile().getLast();
            int loadedDrawPileSize = loadedTable.getDeck().getDrawPile().size();
            Player loadedArthur = loadedTable.getTurnManager().getPlayers().get(0);
            List<Card> loadedArthurHand = loadedArthur.getHand();

            System.out.println("Loaded Top Discard: " + loadedTopDiscard);
            System.out.println("Loaded Draw Pile Size: " + loadedDrawPileSize);
            System.out.println("Loaded Arthur's Hand: " + loadedArthurHand);

            // Assertions
            boolean success = true;
            if (originalTable.getInitialCardCount() != loadedTable.getInitialCardCount()) {
                System.err.println("Erro: Initial card count divergente!");
                success = false;
            }
            if (!originalTopDiscard.equals(loadedTopDiscard)) {
                System.err.println("Erro: Top discard card divergente!");
                success = false;
            }
            if (originalDrawPileSize != loadedDrawPileSize) {
                System.err.println("Erro: Draw pile size divergente!");
                success = false;
            }
            if (!arthurHand.equals(loadedArthurHand)) {
                System.err.println("Erro: Hand de Arthur divergente!");
                success = false;
            }
            if (loadedTable.getTurnManager().getPlayers().size() != 2) {
                System.err.println("Erro: Quantidade de jogadores divergente!");
                success = false;
            }

            if (success) {
                System.out.println("\n=== SUCESSO: O estado do jogo foi salvo e carregado com 100% de integridade! ===");
            } else {
                System.err.println("\n=== FALHA: Houve inconsistências no estado carregado. ===");
            }

            // Cleanup the demo file
            repository.delete(saveName);
            System.out.println("Slot '" + saveName + "' excluído com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro durante execução do demo:");
            e.printStackTrace();
        }
    }
}
