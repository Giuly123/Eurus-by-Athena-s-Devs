package game.player.status;

public enum UsingItemStatus
{
    used,
    alreadyUsed,
    wrongItem,
    unusable,
    unknown;

    private UsingItemStatusArgs args;

    UsingItemStatus()
    {
        args = new UsingItemStatusArgs();
    }

    public UsingItemStatusArgs getArgs()
    {
        return args;
    }

    public void setArgs(UsingItemStatusArgs args)
    {
        this.args = args;
    }
}