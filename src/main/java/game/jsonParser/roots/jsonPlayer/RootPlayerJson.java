package game.jsonParser.roots.jsonPlayer;

import game.entity.dialog.DialogEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RootPlayerJson
{
    public final List<UUID> usedInteractable;
    public final Map<UUID, List<UUID>> usedItems;
    public final List<UUID> inventory;
    public final int lastPositionRiga;
    public final int lastPositionColonna;
    public final List<UUID> dialoguesMade;
    public boolean endGame;


    public RootPlayerJson(int lastPositionRiga, int lastPositionColonna, List<UUID> usedInteractable, Map<UUID,
            List<UUID>> usedItems, List<UUID> inventory, List<UUID> dialoguesMade, boolean endGame)
    {
        this.usedInteractable = usedInteractable;
        this.usedItems = usedItems;
        this.inventory = inventory;
        this.lastPositionRiga = lastPositionRiga;
        this.lastPositionColonna = lastPositionColonna;
        this.dialoguesMade = dialoguesMade;
        this.endGame = endGame;
    }


}