package game.gameModel;

import game.gameUtilities.Stopwatch;
import game.player.StartConfig;
import game.managers.MapManager;
import game.player.Player;
import game.managers.InteractableHandler;
import game.managers.ItemsHandler;
import game.managers.database.GameDatabaseManager;

/**
 * Gestisce la logica del gioco e comunica al controller
 * il cambio di stato del gioco per mezzo di observer.
 */
public class GameModel
{
    private Stopwatch stopwatch;
    private StartConfig startConfig;
    private Player player;
    private MapManager map;
    private ItemsHandler itemsHandler;
    private InteractableHandler interactableHandler;
    private GameDatabaseManager gameDatabaseManager;


    /**
     * Inizializza il gioco.
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     * @throws Exception errore durante la fase di inizializzazione del gioco
     */
    public void init(Boolean isContinuing) throws Exception
    {
        gameDatabaseManager = GameDatabaseManager.getInstance();

        startConfig = new StartConfig();
        itemsHandler = ItemsHandler.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        map = MapManager.getInstance();
        player = new Player(isContinuing, startConfig);

        setStopwatch(isContinuing);
    }

    /**
     *
     * @return il player.
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     *
     * @return il tempo in millisecondi.
     */
    public long getTime()
    {
        return stopwatch.getTempoTrascorsoMillis();
    }

    /**
     *
     * @return lo stopwatch.
     */
    public Stopwatch getStopwatch()
    {
        return stopwatch;
    }

    /**
     * Esegue il dispose della classe.
     */
    public void dispose()
    {
        if (stopwatch != null)
        {
            stopwatch.stop();
        }
    }

    /**
     *
     * @return il map manager.
     */
    public MapManager getMap()
    {
        return map;
    }

    /**
     *
     * @return restituisce gli startConfig.
     */
    public StartConfig getStartConfig()
    {
        return startConfig;
    }

    /**
     * Inizializza il cronometro.
     * @param isContinuing se deve riprendere dall'ultimo tempo salvato
     */
    private void setStopwatch(boolean isContinuing)
    {
        stopwatch = new Stopwatch();

        if (isContinuing)
        {
            Long value = gameDatabaseManager.getValueFromTable("time", "CURRENTPLAYER");

            if (player.endGame)
            {
                stopwatch.setTimePassed(value != null ? value : 0);
            }
            else
            {
                stopwatch.start(value != null ? value : 0);
            }
        }
        else
        {
            stopwatch.start();
        }
    }

}
