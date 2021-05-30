package game.gameController;

import game.entity.item.ItemType;
import game.gameUtilities.Coordinates;
import game.managers.InteractableHandler;
import game.managers.ItemsHandler;
import game.entity.interactable.Interactable;
import game.entity.interactable.InteractableType;
import game.entity.item.Item;
import game.gameUtilities.Sentences;
import game.GameModel;
import game.gui.GameView;

public class CommandsParser
{
    public GameModel gameModel;
    public GameView gameView;
    private InteractableHandler interactableHandler;
    private ItemsHandler itemsHandler;


    public CommandsParser(GameModel gameModel, GameView gameView) throws Exception
    {
        interactableHandler = InteractableHandler.getInstance();
        itemsHandler = ItemsHandler.getInstance();

        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    private void readDocument(String itemName)
    {
        if (itemName != null)
        {
            Item item = itemsHandler.getItem(itemName);
            if(item != null)
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

    private void takeItem(String itemName)
    {
        if (itemName != null)
        {
            Item item = itemsHandler.getItem(itemName);
            gameModel.getPlayer().takeItem(item);
        }
    }

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

    }

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

    private void open(String interactableName)
    {
        if (interactableName != null)
        {
            Interactable interactable = interactableHandler.getInteractable(interactableName);

            if (interactable != null &&
                    (interactable.getInteractableType() == InteractableType.chest
                            || interactable.getInteractableType() == InteractableType.chestGuessingGame))
            {
                interact(interactable);
            }
            else
            {
                gameView.appendText(Sentences.INTERACTABLE_CHEST_ERROR);
            }
        }
        else
        {
            gameView.appendText(Sentences.INTERACTABLE_ERROR);
        }
    }

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

    private void interact(Interactable interactable)
    {
        gameModel.getPlayer().interact(interactable);
    }

    private void observe(String argument)
    {
        if(argument == null || argument.length() == 0)
        {
            gameModel.getPlayer().observe(true);
        }
        else
        {
            lookItem(argument);
        }
    }

    public void parseCommand(String str)
    {
        Command command = Command.parseCommand(str);

        if (command == Command.osserva)
        {
            observe(command.argComando);
        }
        else if (command == Command.guarda)
        {
            lookItem(command.argComando);
        }
        else if (command == Command.leggi)
        {
            readDocument(command.argComando);
        }
        else if (command == Command.usa)
        {
            useItem(command.argComando);
        }
        else if (command == Command.apri)
        {
            open(command.argComando);
        }
        else if (command == Command.interagisci)
        {
            interact(command.argComando);
        }
        else if (command == Command.prendi)
        {
            takeItem(command.argComando);
        }
        else if (command == Command.rispondi)
        {
            giveAnswer(command.argComando);
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
            gameModel.getPlayer().saveFile();
        }
        else if (command == Command.esci)
        {
            // esci
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





}
