package game.managers;

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
import game.player.status.*;

import java.util.UUID;

public class GameEventsHandler
{
    private GameModel gameModel;
    private GameView gameView;

    private InteractableHandler interactableHandler;
    private DialoguesHandler dialoguesHandler;
    private GuessingGamesHandler guessingGamesHandler;


    public GameEventsHandler(GameModel gameModel, GameView gameView) throws Exception
    {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        dialoguesHandler = DialoguesHandler.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        guessingGamesHandler = GuessingGamesHandler.getInstance();

        Observer<Interactable> observerUnlockInteractable = interactable -> onUnlockInteractable(interactable);
        Observer<MovingStatus> observerTryMovePlayer = moveArgs -> onTryMovePlayer(moveArgs);
        Observer<AnswerStatus> observeTryResolveGuessingGame = statusArgs -> onTryResolveGuessingGame(statusArgs);
        Observer<UsingItemStatus> observerTryUseItem = useItemArgs -> onTryUseItem(useItemArgs);
        Observer<InteractStatus> observerTryInteract = interactArgs -> onTryInteract(interactArgs);
        Observer<TakeItemStatus> observerTryTakeItem = takeItemArgs -> onTryTakeItem(takeItemArgs);
        Observer<String> observerLookItem = ItemDescription -> onLookItem(ItemDescription);
        Observer<String> observerObserve = description -> onObserve(description);
        Observer<Item> observerAddItemToInventory = item -> addItemToInventory(item);
        Observer<Item> observerRemoveItemToInventory = item -> removeItemToInventory(item);

        interactableHandler.getOnUnlockInteractable().register(observerUnlockInteractable);
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


    private void onUnlockInteractable(Interactable interactable)
    {
        gameView.appendText(interactable.getAfterUsed());

        checkEndGame(interactable);
    }


    private void onTryTakeItem(TakeItemStatus status)
    {
        if (status == TakeItemStatus.taken)
        {
            gameView.appendText(Sentences.LOOK_ITEM_DESCRIPTION + status.item.getDescription());
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


    private void checkEndGame(Interactable interactable)
    {
        if (interactable.isEndGame())
        {
            gameView.appendText(Sentences.END_GAME_STRING);
            gameModel.getPlayer().endGame = true;
        }
    }

    private void onTryInteract(InteractStatus status)
    {
        if (status == InteractStatus.used)
        {
            gameView.appendText(Sentences.INTERACTABLE_USED + status.interactable.getName());
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
            GuessingGame guessingGame = guessingGamesHandler.getGuessingGame(status.interactable.getGuessingGameId());
            gameView.appendText(Sentences.MOVE_NEED_ANSWER_2 + guessingGame.getText());
        }
    }


    private boolean tryUnlockInteractable(Interactable interactable)
    {
        boolean unlocked = false;

        if(interactable.getInteractableType() == InteractableType.switchOnLight)
        {
            interactableHandler.addUsedInteractable(interactable);
            unlocked = true;
        }

        else if (interactable.getInteractableType() == InteractableType.door)
        {
            if (gameModel.getPlayer().haveNecessaryItemsToUseIt(interactable))
            {
                interactableHandler.addUsedInteractable(interactable);
                unlocked = true;
            }
        }

        return unlocked;
    }


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
            gameView.appendText(Sentences.USE_ITEM_USED + status.args.item.getName());
            gameView.appendText(status.args.item.getAfterUsed());

            if(status.args.item.isConsumable())
            {
                gameModel.getPlayer().inventoryManager.removeItem(status.args.item);
            }

            if (status.args.item.getItemType() == ItemType.itemToUseInteractable)
            {
                tryUnlockInteractable(status.args.interactable);
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
            gameView.appendText(answerStatus.guessingGame.getAfterAnswered());
            guessingGamesHandler.addGuessingGameToResolved(answerStatus.guessingGame.getId());
        }
    }


    private void checkDialogEvent(UUID dialogId)
    {
        checkDialogEvent(dialoguesHandler.getDialog(dialogId));
    }

    private void checkDialogEvent(DialogEvent dialog)
    {
        if(dialog != null && !dialoguesHandler.isMadeDialog(dialog.getId()))
        {
            gameView.appendText(dialog.getDialogText());
            dialoguesHandler.addDialogToMade(dialog.getId());
        }
    }

    private void onTryMovePlayer(MovingStatus status)
    {
        MoveStatusArgs args = status.args;

        if (status == MovingStatus.moved)
        {
            checkDialogEvent(args.nextTile.getDialogId());
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
            GuessingGame guessingGame = guessingGamesHandler.getGuessingGame(args.nextTile.getGuessingGameToEnterId());
            gameView.appendText(Sentences.MOVE_NEED_ANSWER_2 + guessingGame.getText());
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


    private void onObserve(String description)
    {
        gameView.appendText(description);
    }


    private void onLookItem(String description)
    {
        gameView.appendText(description);
    }


    private void removeItemToInventory(Item item)
    {
        gameView.removeItemToInventory(item);
    }

    private void addItemToInventory(Item item)
    {
        gameView.addItemToInventory(item);
        gameView.appendText(Sentences.TAKE_ITEM_TAKEN + item.getName());
    }

}
