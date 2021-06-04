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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

public class GameController
{

    private GameEventsHandler gameEventsHandler;
    private CommandsParser commandsParser;
    private GameModel gameModel;
    private GameView gameView;

    private Observer<List<UUID>> observerLoadInventory = itemsId -> loadInventory(itemsId);

    public GameController(GameModel gameModel, GameView gameView, boolean isContinuing)
    {
        this.gameModel = gameModel;
        this.gameView = gameView;

        try
        {
            InventoryManager.getInstance().getOnLoadInventory().register(observerLoadInventory);

            gameModel.setup(isContinuing);
            gameEventsHandler = new GameEventsHandler(gameModel, gameView);
            commandsParser = new CommandsParser(gameModel, gameView);

            printFirstDescription(isContinuing);

            gameView.addActionHomeButton(onClickHomeButton);
            gameView.addActionOnTextFiledEnter(onEnter);
            gameView.addActionSaveButton(onSaveButton);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utilities.writeFile(Utilities.ERROR_LOG_PATH, e.getMessage());
            gameView.showFatalError(e.getMessage());
        }
    }


    private void printFirstDescription(boolean isContinuing)
    {
        gameView.setEditableSafe(gameView.getTextField(), false);

        if (!isContinuing)
        {
            gameView.appendText(gameModel.getStartConfig().rootStartConfigJson.gameName);
            gameView.appendText(gameModel.getStartConfig().rootStartConfigJson.prologue);
        }

        gameModel.getPlayer().observe(false);
        gameView.setEditableSafe(gameView.getTextField(), true);
        gameView.requestFocusSafe(gameView.getTextField());
    }


    final private ActionListener onClickHomeButton = e -> GUIManager.getInstance().backMainMenu();

    final private ActionListener onSaveButton = e -> saveGame();


    private void onEnter(String string)
    {
        gameView.setEditableSafe(gameView.getTextField(), false);

        gameView.appendText(Sentences.START_STRING_PHRASE + string);

        commandsParser.parseCommand(string);

        gameView.setEditableSafe(gameView.getTextField(), true);

        gameView.requestFocusSafe(gameView.getTextField());
    }


    Action onEnter = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Thread thread = new Thread(() -> {
                try {
                    String textFiledContent = Utilities.cleanString(gameView.getTextField().getText());

                    if(textFiledContent.length() > 0)
                    {
                        onEnter(textFiledContent);
                    }

                    gameView.getTextField().setText("");
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
    }


}
