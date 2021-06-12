package game.gameController;

import game.managers.GameEventsHandler;
import game.gameModel.GameModel;
import game.entity.item.Item;
import game.gameUtilities.observerPattern.Observer;
import game.gameUtilities.Sentences;
import game.gameUtilities.Utilities;
import game.managers.InventoryManager;
import game.managers.ItemsHandler;
import game.gui.GUIManager;
import game.gui.GameView;
import game.managers.database.GameDatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

public class GameController
{
    private GameEventsHandler gameEventsHandler;
    private GameDatabaseManager gameDatabaseManager;
    private CommandsParser commandsParser;
    private InventoryManager inventoryManager;
    private GameModel gameModel;
    private GameView gameView;
    private boolean isPlaying;

    private Observer<List<UUID>> observerLoadInventory = itemsId -> loadInventory(itemsId);

    public GameController(GameModel gameModel, GameView gameView, boolean isContinuing)
    {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.isPlaying = true;

        try
        {
            gameDatabaseManager = GameDatabaseManager.getInstance();
            inventoryManager = InventoryManager.getInstance();
            inventoryManager.getOnLoadInventory().register(observerLoadInventory);

            gameModel.setup(isContinuing);
            startThreadRefreshTime();
            gameEventsHandler = new GameEventsHandler(gameModel, gameView);
            commandsParser = new CommandsParser(gameModel, gameView);

            gameView.setTitleFrame(gameModel.getStartConfig().startConfigJson.gameName);
            printFirstMessage(isContinuing);


            gameView.addActionHomeButton(onClickHomeButton);
            gameView.addActionOnTextFiledEnter(onEnter);
            gameView.addActionSaveButton(onSaveButton);

        }
        catch (Exception e)
        {
            onException(e);
        }
    }

    private void startThreadRefreshTime()
    {
        Thread refreshTime = new Thread(()->{
            try
            {
                refreshTime();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } );

        refreshTime.start();
    }


    private void refreshTime()
    {
        while(this.isPlaying)
        {
            long time = gameModel.getTime();

            if (time < Utilities.EASTER_EGG_TIME)
            {
                gameView.setTimeGame(time);
            }
            else
            {
                gameView.setTimeGame(Utilities.EASTER_EGG_TEMPO_STRING);
            }

            try
            {
                Thread.sleep(800);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void onException(Exception e)
    {
        e.printStackTrace();

        String errorMsg = e.getMessage() != null ? e.getMessage() : Sentences.GENERIC_ERROR;

        String fileMsg = "\n\n";
        fileMsg += Utilities.getCurrentData() + "\n";
        fileMsg += errorMsg;

        Utilities.writeFile(Utilities.ERROR_LOG_PATH, fileMsg, true);

        gameView.showFatalError(errorMsg);
    }


    private void printFirstMessage(boolean isContinuing)
    {
        gameView.setEditableSafe(gameView.getTextField(), false);

        if (!isContinuing)
        {
            //gameView.appendText(gameModel.getStartConfig().startConfigJson.gameName);
            gameView.appendText(gameModel.getStartConfig().startConfigJson.prologue);
        }
        else
        {
            if (Utilities.fileExist(Utilities.TEXT_AREA_PATH))
            {
                gameView.appendTextWithoutDelay(Utilities.readFile(Utilities.TEXT_AREA_PATH));
            }
        }

        gameModel.getPlayer().observe(false);
        gameView.setEditableSafe(gameView.getTextField(), true);
        gameView.requestFocusSafe(gameView.getTextField());
    }


    final private ActionListener onClickHomeButton = e -> {
        this.isPlaying = false;
        inventoryManager.getOnLoadInventory().unregister(observerLoadInventory);
        gameEventsHandler.dispose();
        gameModel.dispose();
        GUIManager.getInstance().backMainMenu();
    };


    final private ActionListener onSaveButton = e -> saveGame();


    private void onEnter()
    {
        String string = Utilities.cleanString(gameView.getTextField().getText());

        if(string.length() > 0)
        {
            gameView.setEditableSafe(gameView.getTextField(), false);
            gameView.enableButtons(false);

            gameView.appendText(Sentences.START_STRING_PHRASE + string);

            commandsParser.parseCommand(string);

            gameView.setEditableSafe(gameView.getTextField(), true);
            gameView.enableButtons(true);

            gameView.forceFocusOnAreaText();
            gameView.requestFocusSafe(gameView.getTextField());
        }

        gameView.getTextField().setText("");
    }


    Action onEnter = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Thread thread = new Thread(() -> {
                try {
                    onEnter();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            });

            thread.start();
        }
    };

    
    public Point getLocationOnScreen()
    {
        return gameView.getLocationOnScreen();
    }

    public void dispose()
    {
        gameView.disposeFrame();
    }

    private void loadInventory(List<UUID> items)
    {
        try
        {
            ItemsHandler itemsHandler = ItemsHandler.getInstance();
            for(UUID itemId : items)
            {
                Item item = itemsHandler.getItem(itemId);
                gameView.addItemToInventory(item);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setVisible(boolean isVisible)
    {
        gameView.setVisible(isVisible);
    }

    private void saveGame()
    {
        gameModel.getPlayer().saveFile();
        gameDatabaseManager.updateValue("time", "CURRENTPLAYER", Long.toString(gameModel.getTime()));
        gameDatabaseManager.updateValue("volume", "CURRENTPLAYER", Integer.toString(gameView.getVolumeValue()));
        Utilities.writeFile(Utilities.TEXT_AREA_PATH, gameView.getTextAreaContent(), false);

        gameView.appendText(Sentences.SAVE_GAME);
    }


}
