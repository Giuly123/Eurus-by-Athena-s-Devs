package game.entity.item;

import game.entity.Entity;
import java.util.List;
import java.util.UUID;

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

    public String getAssetName()
    {
        return assetName;
    }

    public ItemType getItemType()
    {
        return itemType;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean isConsumable() {
        return isConsumable;
    }
}
