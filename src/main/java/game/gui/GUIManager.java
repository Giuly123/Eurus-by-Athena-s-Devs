package game.gui;

import game.GameModel;
import game.gameController.GameController;
import game.gameUtilities.Utilities;

import javax.swing.*;


public class GUIManager
{
    // singleton
    private static GUIManager instance;
    public MainMenuController mainMenuController;
    public boolean isPlaying;

    GameController gameController;

    public static GUIManager getInstance()
    {
        if (instance == null)
        {
            instance = new GUIManager();
        }

        return instance;
    }

    private GUIManager()
    {
        this.isPlaying = false;

        boolean isFirstTime = !Utilities.fileExist(Utilities.SAVE_JSON_PATH);

        SwingUtilities.invokeLater(() ->
        {
            MainMenuModel mainMenuModel = new MainMenuModel();
            mainMenuController = new MainMenuController(isFirstTime, mainMenuModel);

            enableGameFrame(false);
            enableMainMenuFrame(true);
        });

    }


    public void startNewGame(boolean isContinuing)
    {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                GameModel gameModel = new GameModel();
                GameView gameView = new GameView();
                gameView.setVisible(true);
                enableMainMenuFrame(false);

                gameController = new GameController(gameModel, gameView, isContinuing);

                return null;
            }
        };

        worker.execute();
    }


    public void backMainMenu()
    {
        if (mainMenuController != null)
        {
            mainMenuController.setLocation(gameController.getLocationOnScreen());
        }

        enableGameFrame(false);
        enableMainMenuFrame(true);
        this.isPlaying = false;
    }


    private void enableGameFrame(boolean isVisible)
    {
        SwingUtilities.invokeLater(() ->
        {
            if (gameController != null)
            {
                gameController.setVisible(isVisible);
            }
        });
    }


    private void enableMainMenuFrame(boolean isVisible)
    {
        SwingUtilities.invokeLater(() ->
        {
            if (isVisible)
            {
                mainMenuController.updateMenu(!Utilities.fileExist(Utilities.SAVE_JSON_PATH));
            }
            mainMenuController.setVisible(isVisible);
        });

    }

}
