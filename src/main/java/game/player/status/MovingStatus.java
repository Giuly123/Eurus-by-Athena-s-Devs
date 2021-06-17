package game.player.status;

/**
 * Possibili stati dell'azione 'move'.
 */
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

    /**
     *
     * @return l'argomento dello stato
     */
    public MoveStatusArgs getArgs()
    {
        return args;
    }

}