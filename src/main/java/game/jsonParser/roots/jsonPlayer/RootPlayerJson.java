package game.jsonParser.roots.jsonPlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RootPlayerJson
{
    public List<UUID> usedInteractable;
    public Map<UUID, List<UUID>> usedItems;
    public List<UUID> inventory;
    public int lastPositionRiga;
    public int lastPositionColonna;
    public boolean endGame;


    public RootPlayerJson(int lastPositionRiga, int lastPositionColonna, List<UUID> usedInteractable, Map<UUID,
            List<UUID>> usedItems, List<UUID> inventory, boolean endGame)
    {
        this.usedInteractable = usedInteractable;
        this.usedItems = usedItems;
        this.inventory = inventory;
        this.lastPositionRiga = lastPositionRiga;
        this.lastPositionColonna = lastPositionColonna;
        this.endGame = endGame;
    }


}