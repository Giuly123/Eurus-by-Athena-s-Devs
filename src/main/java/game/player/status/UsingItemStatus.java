package game.player.status;

/**
 * Possibili stati dell'azione 'use'.
 */
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

    /**
     *
     * @return argomento dello stato.
     */
    public UsingItemStatusArgs getArgs()
    {
        return args;
    }

}