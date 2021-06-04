package game.player;

import game.managers.MapManager;
import game.StartConfig;
import game.entity.tile.Tile;
import game.gameUtilities.Coordinates;
import game.gameUtilities.Sentences;
import game.gameUtilities.observerPattern.Subject;
import game.managers.InteractableHandler;
import game.managers.InventoryManager;
import game.managers.ItemsHandler;
import game.entity.guessingGame.GuessingGame;
import game.entity.interactable.Interactable;
import game.entity.interactable.InteractableType;
import game.entity.item.Item;
import game.entity.item.ItemType;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;
import game.jsonParser.roots.jsonPlayer.RootPlayerJson;

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
    private Subject<String> onObserve;

    public boolean endGame;
    public InventoryManager inventoryManager;

    private InteractableHandler interactableHandler;
    private ItemsHandler itemsHandler;

    private int currentPositionRiga;
    private int currentPositionColonna;
    private MapManager map;
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

        interactableHandler = InteractableHandler.getInstance();
        inventoryManager = InventoryManager.getInstance();
        itemsHandler = ItemsHandler.getInstance();
        this.map = MapManager.getInstance();
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

    public Subject<String> getOnObserveSubject()
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
            boolean isHere = map.getTile(currentPositionRiga, currentPositionColonna).getItemsToTake().contains(item.getId());

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

        Tile tile = map.getTile(currentPositionRiga, currentPositionColonna);

        Interactable interactable = getInteractableNeedingItem(tile, item);

        if (interactable != null)
        {
            boolean alreadyUsed = interactableHandler.getUsedIteractable().contains(interactable.getId());

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


    // questo metodo serve per utilizzare un oggetti che abbiamo nell invetario e sblocarre una porta adiacente ( da moficare con tipi specifici oggetto )
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


    private boolean isInteractableNeededToEnterUnlocked(Tile tile)
    {
        boolean unlockedNecessaryInteractable = true;

        if (tile != null)
        {
            List<UUID> iteractableNeededToEnter = tile.getInteractableNeededToEnter();

            for (int i = 0; i < iteractableNeededToEnter.size() && unlockedNecessaryInteractable; i++)
            {
                Interactable interactable =
                        interactableHandler.getInteractable(iteractableNeededToEnter.get(i));

                if (interactable != null && interactable.getInteractableType() == InteractableType.door)
                {
                    if (!interactableHandler.getUsedIteractable().contains(interactable.getId()))
                    {
                        unlockedNecessaryInteractable = false;
                    }
                }
                else
                {
                    // per appartenere a questa categoria deve decessariamente essere door ?
                }
            }
        }
        else
        {
            unlockedNecessaryInteractable = false;
        }

        return unlockedNecessaryInteractable;
    }


    private boolean haveNecessaryAnswer(GuessingGame guessingGame)
    {
        return guessingGame == null || guessingGame.isResolved;
    }


    private boolean tileHasNecessaryAnswer(Tile tile)
    {
        boolean hasAnswer = true;

        if (tile != null && tile.hasGuessingGame())
        {
            hasAnswer = haveNecessaryAnswer(tile.getGuessingGameToEnter());
        }

        return hasAnswer;
    }


    private MovingStatus isMovable(Coordinates coordinates)
    {
        MovingStatus status = MovingStatus.offTheMap;

        if (map.isPermittedMovement(currentPositionRiga, currentPositionColonna, coordinates))
        {
            Tile tile = map.getNextTile(currentPositionRiga, currentPositionColonna, coordinates);

            if (isInteractableNeededToEnterUnlocked(tile))
            {
                status = tileHasNecessaryAnswer(tile) ? MovingStatus.moved : MovingStatus.needAnswer;
            }
            else
            {
                status = MovingStatus.needItem;
            }

            status.args.nexTile = tile;
        }

        return status;
    }


    // Restituisce false se e' stato possibile proseguire o meno
    public void tryMove(Coordinates coordinates)
    {
        MovingStatus status = isMovable(coordinates);
        status.args.startTile = map.getTile(currentPositionRiga, currentPositionColonna);
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

        if (haveNecessaryAnswer(interactable.getGuessingGame()))
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

        if (!interactableHandler.getUsedIteractable().contains(interactable.getId()))
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
            if (interactable.getInteractableType() == InteractableType.chest)
            {
                status = interactChest(interactable);
            }
            else if (interactable.getInteractableType() == InteractableType.chestGuessingGame)
            {
                status = interactChest(interactable);
            }
        }

        status.interactable = interactable;

        onTryInteractSubject.notifyObservers(status);
    }



    private List<GuessingGame> getContiguousGuessingGame(List<Tile> contiguousTiles)
    {
        List<GuessingGame> result = new ArrayList<>();

        for(Tile tile : contiguousTiles)
        {
            if (tile.hasGuessingGame() && tile.getGuessingGameToEnter() != null)
            {
                result.add(tile.getGuessingGameToEnter());
            }
        }

        return result;
    }


    private List<GuessingGame> getInteractableGuessingGame(Tile tile)
    {
        List<GuessingGame> result = new ArrayList<>();
        List<Interactable> interactables = interactableHandler.getInteractables(tile.getInteractableHere());

        for (Interactable interactable : interactables)
        {
            if (interactable.getInteractableType() == InteractableType.chestGuessingGame)
            {
                if (interactable.getGuessingGame() != null)
                {
                    result.add(interactable.getGuessingGame());
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

                if (!guessingGame.isResolved)
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
        List<GuessingGame> guessingGames = new ArrayList<>();

        List<Tile> contiguousTiles =  map.getContiguousTiles(currentPositionRiga, currentPositionColonna);
        Tile currentPos = map.getTile(currentPositionRiga, currentPositionColonna);

        guessingGames.addAll(getContiguousGuessingGame(contiguousTiles));

        guessingGames.addAll(getInteractableGuessingGame(currentPos));

        status = trySolveQuestion(guessingGames, answer);

        getOnTrySolveGuessingGameSubject().notifyObservers(status);
    }


    public void observe(boolean isFullDescription)
    {
        String result = "";
        Tile tile = map.getTile(currentPositionRiga, currentPositionColonna);
        String description = isFullDescription ? tile.getFullDescription() : tile.getShortDescription();

        if (tile.isNeededToSwitchOnLight())
        {
            if (interactableHandler.getUsedIteractable().contains(tile.getInteractableToSwitchOnLight()))
            {
                result = Sentences.LOOK_ITEM_DESCRIPTION + description;
            }
            else
            {
                result = Sentences.LOOK_ITEM_DESCRIPTION + tile.getDescriptionOfDarkRoom();
            }
        }
        else
        {
            result = Sentences.LOOK_ITEM_DESCRIPTION + description;
        }

        onObserve.notifyObservers(result);
    }


    public void lookItem(Item item)
    {
        String result;

        if (item != null)
        {
            Tile tile = map.getTile(currentPositionRiga, currentPositionColonna);

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


    private void loadSaveFile(boolean isContinuing, StartConfig startConfig)
    {
        if (isContinuing)
        {
            try
            {
                RootPlayerJson player = JsonParser.GetClassFromJson(Utilities.SAVE_JSON_PATH, RootPlayerJson.class);
                currentPositionRiga = player.lastPositionRiga;
                currentPositionColonna = player.lastPositionColonna;
                interactableHandler.setUsedIteractable(player.usedInteractable);
                inventoryManager.setInventoryList(player.inventory);
                inventoryManager.setUsedItemsMap(player.usedItems);
                endGame = player.endGame;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            currentPositionRiga = startConfig.rootStartConfigJson.startPositionRiga;
            currentPositionColonna = startConfig.rootStartConfigJson.startPositionColonna;
            interactableHandler.setUsedIteractable(new ArrayList<>());
            inventoryManager.setInventoryList(new ArrayList<>());
            inventoryManager.setUsedItemsMap(new HashMap<>());
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
                    endGame);
            String content = JsonParser.SerializeClassToJson(player);
            boolean result = Utilities.writeFile(Utilities.SAVE_JSON_PATH, content);

            if (!result)
            {
                // stampa errore
            }
        }
    }
}
