package game.player;

import game.entity.interactable.Interactable;
import game.entity.item.Item;

public enum UsingItemStatus
{
    used,
    alreadyUsed,
    wrongItem,
    unusable,
    unknown;

    public UsingItemStatusArgs args;

    UsingItemStatus()
    {
        args = new UsingItemStatusArgs();
    }
}