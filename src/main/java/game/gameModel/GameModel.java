package game.gameModel;


import game.player.StartConfig;
import game.managers.MapManager;
import game.player.Player;
import game.managers.InteractableHandler;
import game.managers.ItemsHandler;

public class GameModel
{
    private StartConfig startConfig;
    private Player player;
    private MapManager map;
    private ItemsHandler itemsHandler;
    private InteractableHandler interactableHandler;


    public void setup(Boolean isContinuing) throws Exception
    {
        init(isContinuing);
    }

    private void init(Boolean isContinuing) throws Exception
    {
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

    public MapManager getMap()
    {
        return map;
    }

    public StartConfig getStartConfig()
    {
        return startConfig;
    }

}
