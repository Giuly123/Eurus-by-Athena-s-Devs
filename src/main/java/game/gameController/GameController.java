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

/**
 * Gestisce il  flusso dei dati del GameModel
 * e l'update dei dati, qualora ce ne fosse bisogno, nel GameView.
 */
public class GameController
{
    private GameEventsHandler gameEventsHandler;
    private CommandsParser commandsParser;
    private InventoryManager inventoryManager;
    private GameModel gameModel;
    private GameView gameView;
    private boolean isPlaying;

    private Observer<List<UUID>> observerLoadInventory = itemsId -> loadInventory(itemsId);

    /**
     * Inizializza il gioco.
     * @param gameModel model
     * @param gameView view
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     */
    public GameController(GameModel gameModel, GameView gameView, boolean isContinuing)
    {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.isPlaying = true;

        try
        {
            inventoryManager = InventoryManager.getInstance();
            inventoryManager.getOnLoadedInventory().register(observerLoadInventory);

            gameModel.init(isContinuing);
            startThreadRefreshTime();
            gameEventsHandler = new GameEventsHandler(gameModel, gameView);
            commandsParser = new CommandsParser(gameModel, gameView);

            gameView.setTitleFrame(gameModel.getStartConfig().startConfigJson.gameName);
            printFirstMessage(isContinuing, gameModel.getPlayer().endGame);

            gameView.addActionHomeButton(onClickHomeButton);
            gameView.addActionOnTextFiledEnter(onEnter);
            gameView.addActionSaveButton(onSaveButton);
        }
        catch (Exception e)
        {
            onException(e);
        }
    }

    /**
     * Avvia thread che si occupa di aggiornare la label nella view.
     */
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


    /**
     * Aggiorna la label time nella view, superati i 20 minuti
     * inserisce l'easter egg.
     */
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


    /**
     * Mostra il pannello Fatal Error e memorizza nel file log l'errore con la data.
     * @param e eccezione
     */
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


    /**
     * Si occupa di scrivere le prime informazioni quando si inizia una partita o
     * di recuperare le informazioni scritte prima del salvataggio quando si continua una partita.
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     */
    private void printFirstMessage(boolean isContinuing, boolean isEndGame)
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

        if (isEndGame)
        {
            gameView.appendText(Sentences.END_GAME_RESUME_GAME);
        }
        else
        {
            gameModel.getPlayer().observe(false);
        }

        gameView.setEditableSafe(gameView.getTextField(), true);
        gameView.requestFocusSafe(gameView.getTextField());
    }


    /**
     * Azione eseguita al click del bottone Home.
     */
    final private ActionListener onClickHomeButton = e -> {
        this.isPlaying = false;
        inventoryManager.getOnLoadedInventory().unregister(observerLoadInventory);
        gameEventsHandler.dispose();
        gameModel.dispose();
        GUIManager.getInstance().backMainMenu();
    };

    /**
     * Azione eseguita al click del bottone Save.
     */
    final private ActionListener onSaveButton = e -> saveGame();

    /**
     * Azione eseguita quando si invia dal textField un comando.
     */
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

    /**
     * Azione eseguita all'invio del comando nel textField.
     */
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

    /**
     *
     * @return il punto di locazione dello schermo.
     */
    public Point getLocationOnScreen()
    {
        return gameView.getLocationOnScreen();
    }

    /**
     * Esegue il dispose della classe.
     */
    public void dispose()
    {
        gameView.disposeFrame();
    }

    /**
     * Si occupa del loading della lista di UUID degli item
     * presenti nell'inventario al momento del salvataggio.
     * @param items lista UUID degli item
     */
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

    /**
     * Salva informazioni della partita corrente.
     */
    private void saveGame()
    {
        commandsParser.saveGame();
    }


}
