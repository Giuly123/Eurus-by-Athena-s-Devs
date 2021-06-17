package game.gameController;

import game.entity.item.ItemType;
import game.gameUtilities.Coordinates;
import game.gameUtilities.Utilities;
import game.managers.InteractableHandler;
import game.managers.ItemsHandler;
import game.entity.interactable.Interactable;
import game.entity.item.Item;
import game.gameUtilities.Sentences;
import game.gameModel.GameModel;
import game.gui.GameView;
import game.managers.database.GameDatabaseManager;

/**
 * Si occupa di effettuare il parse del comando e di invocare l'azione correlata.
 */
class CommandsParser
{
    private GameModel gameModel;
    private GameView gameView;
    private GameController gameController;

    private GameDatabaseManager gameDatabaseManager;
    private InteractableHandler interactableHandler;
    private ItemsHandler itemsHandler;


    public CommandsParser(GameController gameController) throws Exception
    {
        gameDatabaseManager = GameDatabaseManager.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        itemsHandler = ItemsHandler.getInstance();

        this.gameModel = gameController.gameModel;
        this.gameView = gameController.gameView;
        this.gameController = gameController;
    }


    /**
     * Se consentito, invoca l'azione di
     * lettura del testo (descrizione) degli item di tipo document.
     * @param itemName nome dell'item
     */
    private void readDocument(String itemName)
    {
        if (itemName != null)
        {
            Item item = itemsHandler.getItem(itemName);

            if (item != null)
            {
                if (item.getItemType() == ItemType.document)
                {
                    gameModel.getPlayer().lookItem(item);
                }
                else
                {
                    gameView.appendText(Sentences.LOOK_ITEM_ISNT_DOCUMENT);
                }
            }
            else
            {
                gameView.appendText(Sentences.LOOK_ITEM_WRONG);
            }
        }
        else
        {
            gameView.appendText(Sentences.LOOK_ITEM_INCOMPLETE);
        }
    }

    /**
     * Se consentito, invoca l'azione di osservazione dell'item.
     * @param itemName nome dell'item
     */
    private void lookItem(String itemName)
    {
        if (itemName != null)
        {
            Item item = itemsHandler.getItem(itemName);
            gameModel.getPlayer().lookItem(item);
        }
        else
        {
            gameView.appendText(Sentences.LOOK_ITEM_INCOMPLETE);
        }
    }

    /**
     * Se consentito, invoca l'azione di raccolta dell'item.
     * @param itemName nome dell'item
     */
    private void takeItem(String itemName)
    {
        if (itemName != null)
        {
            Item item = itemsHandler.getItem(itemName);
            gameModel.getPlayer().takeItem(item);
        }
        else
        {
            gameView.appendText(Sentences.TAKE_ERROR);
        }
    }

    /**
     * Se consentito, invoca l'azione di utilizzo dell'item.
     * @param itemName nome dell'item
     */
    private void useItem(String itemName)
    {
        if (itemName != null)
        {
            Item item = gameModel.getPlayer().inventoryManager.getItem(itemName);

            if (item != null)
            {
                gameModel.getPlayer().useItem(item);
            }
            else
            {
                gameView.appendText(Sentences.USE_ITEM_NOT_OWNED);
            }
        }
        else
        {
            gameView.appendText(Sentences.USE_ERROR);
        }
    }

    /**
     * Se consentito, invoca l'azione di risposta ad un indovinello.
     * @param answer risposta
     */
    private void giveAnswer(String answer)
    {
        if (answer != null)
        {
            gameModel.getPlayer().solveQuestion(answer);
        }
        else
        {
            gameView.appendText(Sentences.GIVE_ANSWER_ERROR);
        }
    }

//    private void open(String interactableName)
//    {
//        if (interactableName != null)
//        {
//            Interactable interactable = interactableHandler.getInteractable(interactableName);
//
//            if (interactable != null &&
//                    (interactable.getInteractableType() == InteractableType.chest
//                            || interactable.getInteractableType() == InteractableType.chestGuessingGame))
//            {
//                interact(interactable);
//            }
//            else
//            {
//                gameView.appendText(Sentences.INTERACTABLE_CHEST_ERROR);
//            }
//        }
//        else
//        {
//            gameView.appendText(Sentences.INTERACTABLE_ERROR);
//        }
//    }

    /**
     * Se consentito, invoca l'azione di interazione con un interactable.
     * @param interactableName nome dell'interactable
     */
    private void interact(String interactableName)
    {
        if (interactableName != null)
        {
            Interactable interactable = interactableHandler.getInteractable(interactableName);
            interact(interactable);
        }
        else
        {
            gameView.appendText(Sentences.INTERACTABLE_ERROR);
        }
    }

    /**
     * Invoca l'azione di interazione con un interactable.
     * @param interactable interactable
     */
    private void interact(Interactable interactable)
    {
        gameModel.getPlayer().interact(interactable);
    }


    /**
     * Se consentito, invoca l'azione di osservazione dell'item.
     * @param argument argomento (item da osservare)
     */
    private void observe(String argument)
    {
        if (argument == null || argument.length() == 0)
        {
            gameModel.getPlayer().observe(true);
        }
        else
        {
            lookItem(argument);
        }
    }


    /**
     * Comandi ammessi dopo la fine del gioco.
     * @param command comando
     */
    private void CommandsAllowedAfterEndGame(Command command)
    {
        if (command == Command.salva)
        {
            gameModel.getPlayer().saveFile();
            gameView.appendText(Sentences.SAVE_GAME);
        }
        else if (command == Command.help)
        {
            gameView.appendText(Sentences.HELP_MESSAGE);
        }
        else if (command == null)
        {
            gameView.appendText(Sentences.WRONG_COMMAND_ENTERED);
        }
        else
        {
            gameView.appendText(Sentences.END_GAME_HELP_STRING);
        }
    }

    /**
     * Comandi ammessi in game.
     * @param command comandi
     */
    private void CommandsAllowedBeforeEndGame(Command command)
    {
        if (command == Command.osserva)
        {
            observe(command.getArgComando());
        }
        else if (command == Command.guarda)
        {
            lookItem(command.getArgComando());
        }
        else if (command == Command.leggi)
        {
            readDocument(command.getArgComando());
        }
        else if (command == Command.usa)
        {
            useItem(command.getArgComando());
        }
//        else if (command == Command.apri)
//        {
//            open(command.argComando);s
//        }
        else if (command == Command.interagisci)
        {
            interact(command.getArgComando());
        }
        else if (command == Command.prendi)
        {
            takeItem(command.getArgComando());
        }
        else if (command == Command.rispondi)
        {
            giveAnswer(command.getArgComando());
        }
        else if (command == Command.nord)
        {
            gameModel.getPlayer().tryMove(Coordinates.North);
        }
        else if (command == Command.sud)
        {
            gameModel.getPlayer().tryMove(Coordinates.South);
        }
        else if (command == Command.est)
        {
            gameModel.getPlayer().tryMove(Coordinates.East);
        }
        else if (command == Command.ovest)
        {
            gameModel.getPlayer().tryMove(Coordinates.West);
        }
        else if (command == Command.salva)
        {
            saveGame();
        }
        else if (command == Command.help)
        {
            gameView.appendText(Sentences.HELP_MESSAGE);
        }
        else
        {
            gameView.appendText(Sentences.WRONG_COMMAND_ENTERED);
        }
    }


    /**
     * Salva informazioni della partita corrente.
     */
    public void saveGame()
    {
        gameModel.getPlayer().saveFile();
        gameDatabaseManager.updateValue("time", "CURRENTPLAYER", "currentplayer", Long.toString(gameModel.getTime()));
        gameDatabaseManager.updateValue("volume", "CURRENTPLAYER", "currentplayer", Integer.toString(gameView.getVolumeValue()));
        Utilities.writeFile(Utilities.TEXT_AREA_PATH, gameView.getTextAreaContent(), false);
        gameView.appendText(Sentences.SAVE_GAME);
    }

    /**
     * Effettua il parse del comando passato come stringa.
     * @param str comando sotto forma di stringa
     */
    public void parseCommand(String str)
    {
        Command command = Command.parseCommand(str);

        if (gameModel.getPlayer().endGame)
        {
            CommandsAllowedAfterEndGame(command);
        }
        else
        {
            CommandsAllowedBeforeEndGame(command);
        }
    }


}
