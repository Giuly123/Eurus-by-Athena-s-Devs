package game.player.status;

public enum MovingStatus
{
    moved,
    needItem,
    needAnswer,
    offTheMap,
    unknown;

    public MoveStatusArgs args;

    MovingStatus()
    {
        this.args = new MoveStatusArgs();
    }
}