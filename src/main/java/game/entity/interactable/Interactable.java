package game.entity.interactable;

import game.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Rappresenta le entità interactable presenti nel file interactables.json.
 */
public class Interactable extends Entity
{
    final private InteractableType interactableType;
    final private UUID guessingGameId;
    final private List<UUID> itemsNeededToUse;
    final private List<UUID> containedItems;
    final private boolean endGame;

    public Interactable(UUID id,
                        String name,
                        String description,
                        String afterUsed,
                        List<String> aliasName,
                        InteractableType interactableType,
                        UUID guessingGameId,
                        List<UUID> itemsNeededToUse,
                        List<UUID> containedItems,
                        boolean endGame)
    {
        super(id, name, description, afterUsed, aliasName);
        this.interactableType = interactableType;
        this.itemsNeededToUse = itemsNeededToUse;
        this.containedItems = containedItems;
        this.endGame = endGame;
        this.guessingGameId = guessingGameId;
    }

    /**
     *
     * @return il tipo interactable.
     */
    public InteractableType getInteractableType()
    {
        return interactableType;
    }

    /**
     *
     * @return la lista degli UUID necessari per usare l'interactable.
     */
    public List<UUID> getItemsNeededToUse()
    {
        return itemsNeededToUse != null ? itemsNeededToUse : new ArrayList<>();
    }

    /**
     *
     * @return la lista degli UUID degli items contenuti nell'interactable.
     */
    public List<UUID> getContainedItems()
    {
        return containedItems != null ? containedItems : new ArrayList<>();
    }

    /**
     *
     * @return l'UUID dell'indovinello presente per interagire con l'interactable.
     */
    public UUID getGuessingGameId()
    {
        return guessingGameId;
    }

    /**
     *
     * @return true se l'entità interactable chiude il gioco.
     */
    public boolean isEndGame()
    {
        return endGame;
    }
}
