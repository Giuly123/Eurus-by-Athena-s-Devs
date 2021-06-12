package game.player.status;

public enum MovingStatus
{
    moved,
    needItem,
    needAnswer,
    offTheMap,
    unknown;

    private MoveStatusArgs args;

    MovingStatus()
    {
        this.args = new MoveStatusArgs();
    }

    public MoveStatusArgs getArgs()
    {
        return args;
    }

    public void setArgs(MoveStatusArgs args)
    {
        this.args = args;
    }
}