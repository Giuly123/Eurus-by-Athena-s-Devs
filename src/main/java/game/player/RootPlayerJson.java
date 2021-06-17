package game.player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Rappresenta il file di salvataggio del gioco nel file save.json.
 */
class RootPlayerJson
{
    public final List<UUID> usedInteractable;
    public final Map<UUID, List<UUID>> usedItems;
    public final List<UUID> inventory;
    public final List<UUID> dialoguesMade;
    public final List<UUID> usedGuessingGame;
    public final int lastPositionRiga;
    public final int lastPositionColonna;
    public boolean endGame;


    public RootPlayerJson(int lastPositionRiga, int lastPositionColonna, List<UUID> usedInteractable, Map<UUID,
            List<UUID>> usedItems, List<UUID> inventory, List<UUID> dialoguesMade, List<UUID> usedGuessingGame, boolean endGame)
    {
        this.usedInteractable = usedInteractable;
        this.usedItems = usedItems;
        this.inventory = inventory;
        this.usedGuessingGame = usedGuessingGame;
        this.dialoguesMade = dialoguesMade;
        this.lastPositionRiga = lastPositionRiga;
        this.lastPositionColonna = lastPositionColonna;
        this.endGame = endGame;
    }


}