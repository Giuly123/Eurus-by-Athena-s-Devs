package game.entity.interactable;

import game.entity.Entity;
import game.entity.guessingGame.GuessingGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Interactable extends Entity
{
    final private InteractableType interactableType;
    final private GuessingGame guessingGame;
    final private List<UUID> itemsNeededToUse;
    final private List<UUID> containedItems;
    final private boolean endGame;

    public Interactable(UUID id,
                        String name,
                        String description,
                        String afterUsed,
                        List<String> aliasName,
                        InteractableType interactableType,
                        GuessingGame guessingGame,
                        List<UUID> itemsNeededToUse,
                        List<UUID> containedItems,
                        boolean endGame)
    {
        super(id, name, description, afterUsed, aliasName);
        this.interactableType = interactableType;
        this.itemsNeededToUse = itemsNeededToUse;
        this.containedItems = containedItems;
        this.endGame = endGame;
        this.guessingGame = guessingGame;
    }

    public InteractableType getInteractableType()
    {
        return interactableType;
    }

    public List<UUID> getItemsNeededToUse()
    {
        return itemsNeededToUse != null ? itemsNeededToUse : new ArrayList<>();
    }

    public List<UUID> getContainedItems()
    {
        return containedItems != null ? containedItems : new ArrayList<>();
    }

    public GuessingGame getGuessingGame()
    {
        return guessingGame;
    }

    public boolean isEndGame()
    {
        return endGame;
    }

}
