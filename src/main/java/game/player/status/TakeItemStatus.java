package game.player.status;

import game.entity.item.Item;

public enum TakeItemStatus
{
    taken,
    alreadyTaken,
    wrongItem;

    public Item item;
}