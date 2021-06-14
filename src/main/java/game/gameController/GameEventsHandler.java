package game.gameController;

import game.entity.dialog.DialogEvent;
import game.gameModel.GameModel;
import game.entity.guessingGame.GuessingGame;
import game.entity.interactable.Interactable;
import game.entity.interactable.InteractableType;
import game.entity.item.Item;
import game.entity.item.ItemType;
import game.gameUtilities.observerPattern.Observer;
import game.gameUtilities.Sentences;
import game.gui.GameView;
import game.managers.DialoguesHandler;
import game.managers.GuessingGamesHandler;
import game.managers.InteractableHandler;
import game.managers.InventoryManager;
import game.player.status.*;

import java.util.UUID;

/**
 * Handler degli eventi del gioco
 */
class GameEventsHandler
{
    private GameModel gameModel;
    private GameView gameView;

    private InteractableHandler interactableHandler;
    private DialoguesHandler dialoguesHandler;
    private GuessingGamesHandler guessingGamesHandler;

    private Observer<Interactable> observerUsedInteractable = interactable -> onUsedInteractable(interactable);
    private Observer<MovingStatus> observerTryMovePlayer = moveArgs -> onTryMovePlayer(moveArgs);
    private Observer<AnswerStatus> observeTryResolveGuessingGame = statusArgs -> onTryResolveGuessingGame(statusArgs);
    private Observer<UsingItemStatus> observerTryUseItem = useItemArgs -> onTryUseItem(useItemArgs);
    private Observer<InteractStatus> observerTryInteract = interactArgs -> onTryInteract(interactArgs);
    private Observer<TakeItemStatus> observerTryTakeItem = takeItemArgs -> onTryTakeItem(takeItemArgs);
    private Observer<String> observerLookItem = itemDescription -> onLookItem(itemDescription);
    private Observer<ObserveArgs> observerObserve = observeArgs -> onObserve(observeArgs);
    private Observer<Item> observerAddItemToInventory = item -> onAddItemToInventory(item);
    private Observer<Item> observerRemoveItemToInventory = item -> onRemoveItemToInventory(item);

    /**
     * Registra gli observer ai vari eventi del gioco.
     * @param gameModel game model
     * @param gameView game view
     * @throws Exception eccezioni che potrebbero generarsi
     */
    public GameEventsHandler(GameModel gameModel, GameView gameView) throws Exception
    {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        dialoguesHandler = DialoguesHandler.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        guessingGamesHandler = GuessingGamesHandler.getInstance();


        interactableHandler.getOnUsedInteractable().register(observerUsedInteractable);
        gameModel.getPlayer().getOnTryMovePlayerSubject().register(observerTryMovePlayer);
        gameModel.getPlayer().getOnTrySolveGuessingGameSubject().register(observeTryResolveGuessingGame);
        gameModel.getPlayer().getOnObserveSubject().register(observerObserve);
        gameModel.getPlayer().getOnTryUseItemSubject().register(observerTryUseItem);
        gameModel.getPlayer().getOnTryInteractSubject().register(observerTryInteract);
        gameModel.getPlayer().getOnTryTakeItemSubject().register(observerTryTakeItem);
        gameModel.getPlayer().getOnLookItem().register(observerLookItem);

        inventoryManager.getOnAddedItemToInventory().register(observerAddItemToInventory);
        inventoryManager.getOnRemovedItemToInventory().register(observerRemoveItemToInventory);

        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    /**
     * Azione che viene eseguita dopo che un interactable
     * è stato aggiunto alla lista degli interactable usati.
     * @param interactable interactable
     */
    private void onUsedInteractable(Interactable interactable)
    {
        gameView.appendText(interactable.getAfterUsed());
        checkEndGame(interactable);
    }

    /**
     * Azione che viene effettuata dopo aver
     * eseguito il comando take.
     * @param status stato dell'operazione
     */
    private void onTryTakeItem(TakeItemStatus status)
    {
        if (status == TakeItemStatus.taken)
        {
            gameView.appendText(Sentences.LOOK_ITEM_DESCRIPTION + status.getItem().getDescription());
        }
        else if (status == TakeItemStatus.alreadyTaken)
        {
            gameView.appendText(Sentences.TAKE_ITEM_ALREADY_TAKEN);
        }
        else if (status == TakeItemStatus.wrongItem)
        {
            gameView.appendText(Sentences.TAKE_ITEM_WRONG);
        }
    }

    /**
     * Controlla se hai terminato il gioco.
     * @param interactable interactable
     */
    private void checkEndGame(Interactable interactable)
    {
        if (interactable.isEndGame())
        {
            gameModel.getStopwatch().pause();
            gameView.appendText(Sentences.END_GAME_STRING);
            gameModel.getPlayer().endGame = true;
        }
    }

    /**
     * Azione che viene effettuata dopo aver
     * eseguito il comando use.
     * @param status stato dell'operazione
     */
    private void onTryInteract(InteractStatus status)
    {
        if (status == InteractStatus.used)
        {
            gameView.appendText(Sentences.INTERACTABLE_USED + status.getInteractable().getName());
        }
        else if (status == InteractStatus.alreadyUsed)
        {
            gameView.appendText(Sentences.INTERACTABLE_ALREADY_USED);
        }
        else if (status == InteractStatus.needItem)
        {
            gameView.appendText(Sentences.INTERACTABLE_NEED_ITEM);
        }
        else if (status == InteractStatus.wrongInteractable)
        {
            gameView.appendText(Sentences.INTERACTABLE_WRONG);
        }
        else if (status == InteractStatus.needAnswer)
        {
            gameView.appendText(Sentences.INTERACTABLE_NEED_ANSWER);
            GuessingGame guessingGame = guessingGamesHandler.getGuessingGame(status.getInteractable().getGuessingGameId());
            gameView.appendText(Sentences.MOVE_NEED_ANSWER_2 + guessingGame.getText());
        }
    }

    /**
     *
     * @param interactable interactable
     * @return true se l'interactable è stato usato
     */
    private boolean tryUseInteractable(Interactable interactable)
    {
        boolean used = false;

        if(interactable.getInteractableType() == InteractableType.switchOnLight)
        {
            interactableHandler.addUsedInteractable(interactable);
            used = true;
        }

        else if (interactable.getInteractableType() == InteractableType.door)
        {
            if (gameModel.getPlayer().haveNecessaryItemsToUseInteractable(interactable))
            {
                interactableHandler.addUsedInteractable(interactable);
                used = true;
            }
        }

        return used;
    }

    /**
     * Azione che viene effettuata dopo aver eseguito il comando use.
     * @param status stato dell'operazione
     */
    private void onTryUseItem(UsingItemStatus status)
    {
        if (status == UsingItemStatus.wrongItem)
        {
            gameView.appendText(Sentences.USE_ITEM_WRONG);
        }
        else if(status == UsingItemStatus.unusable)
        {
            gameView.appendText(Sentences.USE_ITEM_UNUSABLE);
        }
        else if (status == UsingItemStatus.used)
        {
            gameView.appendText(Sentences.USE_ITEM_USED + status.getArgs().item.getName());
            gameView.appendText(status.getArgs().item.getAfterUsed());

            if (status.getArgs().item.getItemType() == ItemType.itemToUseInteractable)
            {
                tryUseInteractable(status.getArgs().interactable);
            }

            if(status.getArgs().item.isConsumable())
            {
                gameModel.getPlayer().inventoryManager.removeItem(status.getArgs().item);
            }
        }
        else if (status == UsingItemStatus.alreadyUsed)
        {
            gameView.appendText(Sentences.USE_ITEM_ALREADY_USED);
        }
        else if (status == UsingItemStatus.unknown)
        {
            gameView.appendText(Sentences.USE_ITEM_UNKNOWN);
        }

    }

    /**
     * Azione che viene effettuata dopo avere eseguito il comando reply.
     * @param answerStatus stato della risposta
     */
    private void onTryResolveGuessingGame(AnswerStatus answerStatus)
    {
        if (answerStatus == AnswerStatus.noQuestions)
        {
            gameView.appendText(Sentences.GIVE_ANSWER_NO_QUESTIONS);
        }
        else if (answerStatus == AnswerStatus.notSolved)
        {
            gameView.appendText(Sentences.GIVE_ANSWER_NOT_SOLVED);
        }
        else if (answerStatus == AnswerStatus.alreadySolved || answerStatus == AnswerStatus.definitelyAlreadyResolved)
        {
            gameView.appendText(Sentences.GIVE_ANSWER_ALREADY_SOLVED);
        }
        else if (answerStatus == AnswerStatus.solved)
        {
            gameView.appendText(Sentences.GIVE_ANSWER_SOLVED);
            gameView.appendText(answerStatus.getGuessingGame().getAfterAnswered());
            guessingGamesHandler.addGuessingGameToResolved(answerStatus.getGuessingGame().getId());
        }
    }

    /**
     * Controlla se c'è un dialogo e nel caso lo stampa.
     * @param dialogId UUID del dialogo
     */
    private void checkDialogEvent(UUID dialogId)
    {
        checkDialogEvent(dialoguesHandler.getDialog(dialogId));
    }

    /**
     * Controlla se c'è un dialogo e nel caso lo stampa.
     * @param dialog evento dialogo
     */
    private void checkDialogEvent(DialogEvent dialog)
    {
        if(dialog != null && !dialoguesHandler.isMadeDialog(dialog.getId()))
        {
            gameView.appendText(dialog.getDialogText());
            dialoguesHandler.addDialogToMade(dialog.getId());
        }
    }

    /**
     * Azione che viene effettuato dopo
     * aver eseguito un comando di spostamento.
     * @param status stato dello spostamento
     */
    private void onTryMovePlayer(MovingStatus status)
    {
        MoveStatusArgs args = status.getArgs();

        if (status == MovingStatus.moved)
        {
            gameView.appendText(Sentences.MOVE_MOVED + args.coordinates.name());
            gameModel.getPlayer().observe(false);
        }
        else if (status == MovingStatus.needItem)
        {
            String description = interactableHandler.getInteractable(args.nextTile.getInteractableNeededToEnter()).getDescription();
            gameView.appendText(description);
            gameView.appendText(Sentences.MOVE_NEED_ITEMS);
        }
        else if (status == MovingStatus.needAnswer)
        {
            gameView.appendText(Sentences.MOVE_NEED_ANSWER_1);

            Interactable interactable = interactableHandler.getInteractable(args.nextTile.getInteractableNeededToEnter());
            GuessingGame guessingGame = guessingGamesHandler.getGuessingGame(interactable.getGuessingGameId());
            if (guessingGame != null)
            {
                gameView.appendText(Sentences.MOVE_NEED_ANSWER_2 + guessingGame.getText());
            }
        }
        else if (status == MovingStatus.offTheMap)
        {
            gameView.appendText(args.startTile.getDescriptionOffLimitsArea());
        }
        else
        {
            gameView.appendText(Sentences.MOVE_ERROR);
        }
    }

    /**
     * Azione che viene effettuata dopo aver eseguito il comando observe.
     * @param args argomenti dell'operazione (descizione tile e dialogID)
     */
    private void onObserve(ObserveArgs args)
    {
        checkDialogEvent(args.dialogId);
        gameView.appendText(args.text);
    }

    /**
     * Azione che viene effettuata dopo aver eseguito il comando look.
     * @param description descrizione dell'oggetto
     */
    private void onLookItem(String description)
    {
        gameView.appendText(description);
    }

    /**
     * Azione che viene effettuata dopo aver rimosso un item dall'inventario.
     * @param item item rimosso dall'inventario
     */
    private void onRemoveItemToInventory(Item item)
    {
        gameView.removeItemToInventory(item);
    }

    /**
     * Azione che viene effettuata dopo aver aggiunto un item all'inventario.
     * @param item item aggiunto all'inventario
     */
    private void onAddItemToInventory(Item item)
    {
        gameView.addItemToInventory(item);
        gameView.appendText(Sentences.TAKE_ITEM_TAKEN + item.getName());
    }

    /**
     * Disiscrive tutti gli observer.
     */
    private void unregisterAllObservers()
    {
        interactableHandler.getOnUsedInteractable().unregister(observerUsedInteractable);
        gameModel.getPlayer().getOnTryMovePlayerSubject().unregister(observerTryMovePlayer);
        gameModel.getPlayer().getOnTrySolveGuessingGameSubject().unregister(observeTryResolveGuessingGame);
        gameModel.getPlayer().getOnObserveSubject().unregister(observerObserve);
        gameModel.getPlayer().getOnTryUseItemSubject().unregister(observerTryUseItem);
        gameModel.getPlayer().getOnTryInteractSubject().unregister(observerTryInteract);
        gameModel.getPlayer().getOnTryTakeItemSubject().unregister(observerTryTakeItem);
        gameModel.getPlayer().getOnLookItem().unregister(observerLookItem);
    }

    /**
     * Esegue il dispose della classe.
     */
    public void dispose()
    {
        unregisterAllObservers();
    }
}
