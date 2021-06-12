package game.gameModel;

import game.gameUtilities.Stopwatch;
import game.player.StartConfig;
import game.managers.MapManager;
import game.player.Player;
import game.managers.InteractableHandler;
import game.managers.ItemsHandler;
import game.managers.database.GameDatabaseManager;

public class GameModel
{
    private Stopwatch stopwatch;
    private StartConfig startConfig;
    private Player player;
    private MapManager map;
    private ItemsHandler itemsHandler;
    private InteractableHandler interactableHandler;
    private GameDatabaseManager gameDatabaseManager;

    public void setup(Boolean isContinuing) throws Exception
    {
        init(isContinuing);
    }

    private void setStopwatch(boolean isContinuing)
    {
        stopwatch = new Stopwatch();

        if (isContinuing)
        {
            Long value = gameDatabaseManager.getValueFromTable("time", "CURRENTPLAYER");
            stopwatch.start(value != null ? value : 0);
        }
        else
        {
            stopwatch.start(0);
        }
    }

    private void init(Boolean isContinuing) throws Exception
    {
        gameDatabaseManager = GameDatabaseManager.getInstance();

        setStopwatch(isContinuing);

        startConfig = new StartConfig();
        itemsHandler = ItemsHandler.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        map = MapManager.getInstance();
        player = new Player(isContinuing, startConfig);
    }


    public Player getPlayer()
    {
        return player;
    }

    public long getTime()
    {
        return stopwatch.getTempoTrascorsoMillis();
    }

    public void dispose()
    {
        if (stopwatch != null)
        {
            stopwatch.stop();
        }
    }

    public MapManager getMap()
    {
        return map;
    }

    public StartConfig getStartConfig()
    {
        return startConfig;
    }

}
