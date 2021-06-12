package game.player.status;

import game.entity.item.Item;

public enum TakeItemStatus
{
    taken,
    alreadyTaken,
    wrongItem;

    private Item item;

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }
}