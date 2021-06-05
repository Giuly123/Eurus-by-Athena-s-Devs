package game.player.status;

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