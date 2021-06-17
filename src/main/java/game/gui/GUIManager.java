package game.gui;

import game.gameModel.GameModel;
import game.gameController.GameController;
import game.gameUtilities.Utilities;

import javax.swing.*;

/**
 * Gestisce il cambio di view: da main menu
 * frame al game frame e viceversa.
 */
public class GUIManager
{
    // singleton
    private static GUIManager instance;
    public MainMenuController mainMenuController;
    public boolean isPlaying;

    GameController gameController;

    /**
     *
     * @return l'istanza della classe.
     */
    public static GUIManager getInstance()
    {
        if (instance == null)
        {
            instance = new GUIManager();
        }

        return instance;
    }

    /**
     * Inizializza la view e il model del main menu.
     */
    private GUIManager()
    {
        this.isPlaying = false;

        boolean isFirstTime = !Utilities.fileExist(Utilities.SAVE_JSON_PATH);

        SwingUtilities.invokeLater(() ->
        {
            MainMenuModel mainMenuModel = new MainMenuModel();
            mainMenuController = new MainMenuController(isFirstTime, mainMenuModel);

            //enableGameFrame(false);
            enableMainMenuFrame(true);
        });

    }

    /**
     * Inizia il game e disabilita il frame del main menu.
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     */
    public void startNewGame(boolean isContinuing)
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                GameModel gameModel = new GameModel();
                GameView gameView = new GameView();
                gameView.setVisible(true);
                enableMainMenuFrame(false);

                gameController = new GameController(gameModel, gameView, isContinuing);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    /**
     * Torna al main menu ed effettua il dispose del vecchio game controller.
     */
    public void backMainMenu()
    {
        if (mainMenuController != null && gameController != null)
        {
            mainMenuController.setLocation(gameController.getLocationOnScreen());
            gameController.dispose();
            gameController = null;
        }

        //enableGameFrame(false);
        enableMainMenuFrame(true);
        this.isPlaying = false;
    }


//    private void enableGameFrame(boolean isVisible)
//    {
//        SwingUtilities.invokeLater(() ->
//        {
//            if (gameController != null)
//            {
//                gameController.setVisible(isVisible);
//            }
//        });
//    }

    /**
     * Imposta la proprietà visible al frame del main menu.
     * @param isVisible valore da assegnare alla proprietà visible
     */
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
