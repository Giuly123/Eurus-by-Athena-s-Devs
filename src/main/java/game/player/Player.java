package game.player;

import game.managers.*;
import game.entity.tile.Tile;
import game.gameUtilities.Coordinates;
import game.gameUtilities.Sentences;
import game.gameUtilities.observerPattern.Subject;
import game.entity.guessingGame.GuessingGame;
import game.entity.interactable.Interactable;
import game.entity.interactable.InteractableType;
import game.entity.item.Item;
import game.entity.item.ItemType;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;
import game.jsonParser.roots.jsonPlayer.RootPlayerJson;
import game.player.status.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Player
{
    private Subject<MovingStatus> onTryMovePlayerSubject;
    private Subject<AnswerStatus> onTrySolveGuessingGameSubject;
    private Subject<UsingItemStatus> onTryUseItemSubject;
    private Subject<InteractStatus> onTryInteractSubject;
    private Subject<TakeItemStatus> onTryTakeItemSubject;
    private Subject<String> onLookItem;
    private Subject<ObserveArgs> onObserve;

    public boolean endGame;
    public InventoryManager inventoryManager;
    private MapManager map;

    private InteractableHandler interactableHandler;
    private ItemsHandler itemsHandler;
    private DialoguesHandler dialoguesHandler;
    private GuessingGamesHandler guessingGamesHandler;

    private int currentPositionRiga;
    private int currentPositionColonna;
    private boolean isLoaded = false;

    public Player(boolean isContinuing, StartConfig startConfig) throws Exception
    {
        onTryMovePlayerSubject = new Subject<>();
        onTrySolveGuessingGameSubject = new Subject<>();
        onTryUseItemSubject = new Subject<>();
        onTryInteractSubject = new Subject<>();
        onTryTakeItemSubject = new Subject<>();
        onLookItem = new Subject<>();
        onObserve = new Subject<>();

        itemsHandler = ItemsHandler.getInstance();
        interactableHandler = InteractableHandler.getInstance();
        dialoguesHandler = DialoguesHandler.getInstance();
        guessingGamesHandler = GuessingGamesHandler.getInstance();

        map = MapManager.getInstance();
        inventoryManager = InventoryManager.getInstance();

        loadSaveFile(isContinuing, startConfig);
        isLoaded = true;
    }


    public Subject<MovingStatus> getOnTryMovePlayerSubject()
    {
        return onTryMovePlayerSubject;
    }

    public Subject<AnswerStatus> getOnTrySolveGuessingGameSubject()
    {
        return onTrySolveGuessingGameSubject;
    }

    public Subject<UsingItemStatus> getOnTryUseItemSubject()
    {
        return onTryUseItemSubject;
    }

    public Subject<InteractStatus> getOnTryInteractSubject()
    {
        return onTryInteractSubject;
    }

    public Subject<TakeItemStatus> getOnTryTakeItemSubject()
    {
        return onTryTakeItemSubject;
    }

    public Subject<ObserveArgs> getOnObserveSubject()
    {
        return onObserve;
    }

    public Subject<String> getOnLookItem()
    {
        return onLookItem;
    }


    public void takeItem(Item item)
    {
        TakeItemStatus status = TakeItemStatus.wrongItem;

        if (item != null)
        {
            boolean isHere = getCurrentTile().getItemsToTake().contains(item.getId());
            boolean isPossessed = inventoryManager.inventoryContains(item.getId());

            if (!isPossessed)
            {
                if (isHere)
                {
                    inventoryManager.addItem(item);
                    status = TakeItemStatus.taken;
                }
            }
            else
            {
                status = TakeItemStatus.alreadyTaken;
            }

            status.item = item;
        }

        onTryTakeItemSubject.notifyObservers(status);
    }


    private Interactable getInteractableNeedingItem(Tile tile, Item item)
    {
        Interactable result = null;
        List<UUID> tileInteractable = tile.getInteractableHere();

        for (int i = 0; i < tileInteractable.size() && result == null; i++)
        {
            Interactable interactable =
                    interactableHandler.getInteractable(tileInteractable.get(i));

            if (interactable != null)
            {
                List<UUID> itemsNeededToUse = interactable.getItemsNeededToUse();

                for (int j = 0; j < itemsNeededToUse.size() && result == null; j++)
                {
                    if (item.getId().equals(itemsNeededToUse.get(j)))
                    {
                        result = interactable;
                    }
                }
            }
            else
            {
                System.out.println("Errore: questo id " + tileInteractable.get(i) + " non esiste");
            }
        }

        return result;
    }


    private UsingItemStatus useKey(Item item)
    {
        UsingItemStatus status = UsingItemStatus.wrongItem;
        Tile tile = getCurrentTile();
        Interactable interactable = getInteractableNeedingItem(tile, item);

        if (interactable != null)
        {
            boolean alreadyUsed = interactableHandler.isUsedInteractalbe(interactable.getId());

            if (!alreadyUsed)
            {
                inventoryManager.addUsedItem(interactable.getId(), item.getId());
                status = UsingItemStatus.used;
            }
            else
            {
                status = UsingItemStatus.alreadyUsed;
            }

            status.args.interactable = interactable;
        }
        return status;
    }


    public void useItem(Item item)
    {
        UsingItemStatus status = UsingItemStatus.unknown;

        if (item.getItemType() == ItemType.itemToUseInteractable)
        {
            status = useKey(item);
        }
        else if (item.getItemType() == ItemType.document)
        {
            status = UsingItemStatus.unusable;
        }

        status.args.item = item;
        onTryUseItemSubject.notifyObservers(status);
    }


    public boolean haveNecessaryItemsToUseIt(Interactable interactable)
    {
        boolean isUsable = true;
        List<UUID> neededItems = interactable.getItemsNeededToUse();

        for (int i = 0; i < neededItems.size() && isUsable; i++)
        {
            if (!inventoryManager.usedItemsContains(interactable.getId(), neededItems.get(i)))
            {
                isUsable = false;
            }
        }

        return isUsable;
    }


    private MovingStatus isInteractableUnlocked(Tile tile)
    {
        MovingStatus status = MovingStatus.moved;
        UUID iteractableNeededToEnter = tile.getInteractableNeededToEnter();
        Interactable interactable = interactableHandler.getInteractable(iteractableNeededToEnter);

        if (interactable != null)
        {
            if (interactable.getInteractableType() == InteractableType.door)
            {
                if (!interactableHandler.isUsedInteractalbe(interactable.getId()))
                {
                    status = MovingStatus.needItem;
                }
            }
            else if (interactable.getInteractableType() == InteractableType.doorGuessingGame)
            {
                if (!haveNecessaryAnswer(iteractableNeededToEnter))
                {
                    status = MovingStatus.needAnswer;
                }
            }
        }

        return status;
    }


    private boolean haveNecessaryAnswer(UUID guessingGame)
    {
        return guessingGame == null || guessingGamesHandler.isResolvedGuessingGame(guessingGame);
    }


    private MovingStatus isMovable(Coordinates coordinates)
    {
        MovingStatus status = MovingStatus.offTheMap;

        if (map.isPermittedMovement(currentPositionRiga, currentPositionColonna, coordinates))
        {
            Tile tile = map.getNextTile(currentPositionRiga, currentPositionColonna, coordinates);

            status = isInteractableUnlocked(tile);

            status.args.nextTile = tile;
        }

        return status;
    }


    // Restituisce false se e' stato possibile proseguire o meno
    public void tryMove(Coordinates coordinates)
    {
        MovingStatus status = isMovable(coordinates);
        status.args.startTile = getCurrentTile();
        status.args.coordinates = coordinates;

        if (coordinates == Coordinates.North)
        {
            if (status == MovingStatus.moved)
            {
                currentPositionRiga++;
            }
        }
        else if (coordinates == Coordinates.South)
        {
            if (status == MovingStatus.moved)
            {
                currentPositionRiga--;
            }
        }
        else if (coordinates == Coordinates.East)
        {
            if (status == MovingStatus.moved)
            {
                currentPositionColonna++;
            }
        }
        else if (coordinates == Coordinates.West)
        {
            if (status == MovingStatus.moved)
            {
                currentPositionColonna--;
            }
        }

        onTryMovePlayerSubject.notifyObservers(status);
    }


    private InteractStatus interactChestGuessingGame(Interactable interactable)
    {
        InteractStatus status = InteractStatus.needAnswer;

        if (haveNecessaryAnswer(interactable.getGuessingGameId()))
        {
            interactableHandler.addUsedInteractable(interactable);
            inventoryManager.addItems(interactable.getContainedItems());
            status = InteractStatus.used;
        }

        return status;
    }


    private InteractStatus interactNormalChest(Interactable interactable)
    {
        InteractStatus status = InteractStatus.needItem;

        if (haveNecessaryItemsToUseIt(interactable))
        {
            interactableHandler.addUsedInteractable(interactable);
            inventoryManager.addItems(interactable.getContainedItems());

            status = InteractStatus.used;
        }

        return status;
    }


    private InteractStatus interactChest(Interactable interactable)
    {
        InteractStatus status = InteractStatus.alreadyUsed;

        if (!interactableHandler.isUsedInteractalbe(interactable.getId()))
        {
            if (interactable.getInteractableType() == InteractableType.chest)
            {
                status = interactNormalChest(interactable);
            }
            else if (interactable.getInteractableType() == InteractableType.chestGuessingGame)
            {
                status = interactChestGuessingGame(interactable);
            }
        }

        return status;
    }


    public void interact(Interactable interactable)
    {
        InteractStatus status = InteractStatus.wrongInteractable;

        if (interactable != null)
        {
            Tile currentTile = getCurrentTile();
            boolean isHere =
                    currentTile.getInteractableHere().contains(interactable.getId());

            if (isHere)
            {
                if (interactable.getInteractableType() == InteractableType.chest)
                {
                    status = interactChest(interactable);
                }
                else if (interactable.getInteractableType() == InteractableType.chestGuessingGame)
                {
                    status = interactChest(interactable);
                }
            }
        }

        status.interactable = interactable;
        onTryInteractSubject.notifyObservers(status);
    }




    private List<GuessingGame> getInteractableGuessingGame(Tile tile)
    {
        List<GuessingGame> result = new ArrayList<>();
        List<Interactable> interactables = interactableHandler.getInteractables(tile.getInteractableHere());

        for (Interactable interactable : interactables)
        {
            if (interactable.getInteractableType() == InteractableType.chestGuessingGame || interactable.getInteractableType() == InteractableType.doorGuessingGame)
            {
                if (interactable.getGuessingGameId() != null)
                {
                    GuessingGame guessingGame = guessingGamesHandler.getGuessingGame(interactable.getGuessingGameId());

                    if (guessingGame != null)
                    {
                        result.add(guessingGame);
                    }
                }
            }
        }
        return result;
    }


    private AnswerStatus trySolveQuestion(List<GuessingGame> guessingGames, String answer)
    {
        AnswerStatus status = AnswerStatus.noQuestions;
        GuessingGame guessingGameFound = null;

        for (int i = 0; i < guessingGames.size() && guessingGameFound == null; i++)
        {
            GuessingGame guessingGame = guessingGames.get(i);

            if (guessingGame != null)
            {
                status = status.getMajor(AnswerStatus.alreadySolved);
                boolean isResolved = guessingGamesHandler.isResolvedGuessingGame(guessingGame.getId());

                if (!isResolved)
                {
                    status = status.getMajor(AnswerStatus.notSolved);

                    if (guessingGame.giveAnswer(answer))
                    {
                        status = status.getMajor(AnswerStatus.solved);
                        guessingGameFound = guessingGame;
                    }
                }
                else
                {
                    if (guessingGame.giveAnswer(answer))
                    {
                        status = status.getMajor(AnswerStatus.definitelyAlreadyResolved);
                    }
                }
            }
        }

        status.guessingGame = guessingGameFound;
        return status;
    }


    public void solveQuestion(String answer)
    {
        AnswerStatus status;
        Tile currentPos = getCurrentTile();
        status = trySolveQuestion(getInteractableGuessingGame(currentPos), answer);

        getOnTrySolveGuessingGameSubject().notifyObservers(status);
    }


    public void observe(boolean isFullDescription)
    {
        String text = "";
        Tile tile = getCurrentTile();
        String description = isFullDescription ? tile.getFullDescription() : tile.getShortDescription();

        if (tile.isNeededToSwitchOnLight())
        {
            if (interactableHandler.isUsedInteractalbe(tile.getInteractableToSwitchOnLight()))
            {
                text = Sentences.LOOK_ITEM_DESCRIPTION + description;
            }
            else
            {
                text = Sentences.LOOK_ITEM_DESCRIPTION + tile.getDescriptionOfDarkRoom();
            }
        }
        else
        {
            text = Sentences.LOOK_ITEM_DESCRIPTION + description;
        }

        onObserve.notifyObservers(new ObserveArgs(text, tile.getDialogId()));
    }


    public void lookItem(Item item)
    {
        String result;

        if (item != null)
        {
            Tile tile = getCurrentTile();

            if (tile.getItemsToTake().contains(item.getId()) || inventoryManager.inventoryContains(item.getId()))
            {
                if (item.getItemType() == ItemType.document)
                {
                    result = Sentences.READ_DOCUMENT + item.getDescription();
                }
                else
                {
                    result = Sentences.LOOK_ITEM_DESCRIPTION + item.getDescription();
                }
            }
            else
            {
                result = Sentences.LOOK_ITEM_WRONG;
            }
        }
        else
        {
            result = Sentences.LOOK_ITEM_WRONG;
        }

        onLookItem.notifyObservers(result);
    }

    private Tile getCurrentTile()
    {
        return map.getTile(currentPositionRiga, currentPositionColonna);
    }


    private void loadSaveFile(boolean isContinuing, StartConfig startConfig) throws Exception
    {
        if (isContinuing)
        {
            if (Utilities.fileExist(Utilities.SAVE_JSON_PATH))
            {
                try
                {
                    RootPlayerJson player = JsonParser.GetClassFromJson(Utilities.SAVE_JSON_PATH, RootPlayerJson.class);
                    currentPositionRiga = player.lastPositionRiga;
                    currentPositionColonna = player.lastPositionColonna;
                    interactableHandler.setUsedIteractable(player.usedInteractable);
                    inventoryManager.setInventoryList(player.inventory);
                    inventoryManager.setUsedItemsMap(player.usedItems);
                    dialoguesHandler.setDialoguesMade(player.dialoguesMade);
                    guessingGamesHandler.setUSedGuessingGame(player.usedGuessingGame);
                    endGame = player.endGame;
                } catch (IOException e)
                {
                    throw new Exception("Errore: problema parsing file save.json");
                }
            }
            else
            {
                throw new Exception("File save.json non presente sul disco");
            }

        }
        else
        {
            currentPositionRiga = startConfig.startConfigJson.startPositionRiga;
            currentPositionColonna = startConfig.startConfigJson.startPositionColonna;
            interactableHandler.setUsedIteractable(new ArrayList<>());
            inventoryManager.setInventoryList(new ArrayList<>());
            inventoryManager.setUsedItemsMap(new HashMap<>());
            dialoguesHandler.setDialoguesMade(new ArrayList<>());
            dialoguesHandler.setDialoguesMade(new ArrayList<>());
            guessingGamesHandler.setUSedGuessingGame(new ArrayList<>());
            endGame = false;
        }

    }

    public void saveFile()
    {
        if (isLoaded)
        {
            RootPlayerJson player = new RootPlayerJson(
                    currentPositionRiga,
                    currentPositionColonna,
                    interactableHandler.getUsedIteractable(),
                    inventoryManager.getUsedItems(),
                    inventoryManager.getInvetoryList(),
                    dialoguesHandler.getDialoguesMade(),
                    guessingGamesHandler.getUsedGuessingGame(),
                    endGame);
            String content = JsonParser.SerializeClassToJson(player);
            boolean result = Utilities.writeFile(Utilities.SAVE_JSON_PATH, content, false);

            if (!result)
            {
                // stampa errore
            }
        }
    }
}
