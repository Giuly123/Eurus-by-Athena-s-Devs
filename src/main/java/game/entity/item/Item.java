package game.entity.item;

import game.entity.Entity;
import java.util.List;
import java.util.UUID;

/**
 * Rappresenta le entità item presenti nel file items.json.
 */
public class Item extends Entity
{
    final private String assetName;
    final private ItemType itemType;
    final private boolean isConsumable;

    public Item(UUID id, String name, String description, ItemType itemType,
                String afterUsed, List<String> aliasName, String assetName, boolean isConsumable)
    {
        super(id, name, description, afterUsed, aliasName);
        this.assetName = assetName;
        this.itemType = itemType;
        this.isConsumable = isConsumable;

    }

    /**
     *
     * @return la stringa con il nome dell'item.
     */
    public String getAssetName()
    {
        return assetName;
    }

    /**
     *
     * @return il tipo item.
     */
    public ItemType getItemType()
    {
        return itemType;
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     *
     * @return true se l'item è consumabile.
     */
    public boolean isConsumable() {
        return isConsumable;
    }
}
