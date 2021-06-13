package game.player;

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
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;
import game.managers.*;
import game.player.status.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Gestisce la logica del gioco.
 */
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

    /**
     * Costruttore della classe player che inizializza il game
     * e deserializza e importa le informazioni dell'ultimo salvataggio.
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     * @param startConfig informazioni iniziali
     * @throws Exception eccezioni relative al parse dei json
     */
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

    /**
     *
     * @return il subject onTryMovePlayerSubject
     */
    public Subject<MovingStatus> getOnTryMovePlayerSubject()
    {
        return onTryMovePlayerSubject;
    }

    /**
     *
     * @return il subject onTrySolveGuessingGameSubject
     */
    public Subject<AnswerStatus> getOnTrySolveGuessingGameSubject()
    {
        return onTrySolveGuessingGameSubject;
    }

    /**
     *
     * @return il subject onTryUseItemSubject
     */
    public Subject<UsingItemStatus> getOnTryUseItemSubject()
    {
        return onTryUseItemSubject;
    }

    /**
     *
     * @return il subject onTryInteractSubject
     */
    public Subject<InteractStatus> getOnTryInteractSubject()
    {
        return onTryInteractSubject;
    }

    /**
     *
     * @return il subject onTryTakeItemSubject
     */
    public Subject<TakeItemStatus> getOnTryTakeItemSubject()
    {
        return onTryTakeItemSubject;
    }

    /**
     *
     * @return il subject onObserve
     */
    public Subject<ObserveArgs> getOnObserveSubject()
    {
        return onObserve;
    }

    /**
     *
     * @return il subject onLookItem
     */
    public Subject<String> getOnLookItem()
    {
        return onLookItem;
    }

    /**
     * Aggiunge all'inventario l'oggetto passato come parametro, se:
     * - è diverso da null;
     * - se è presente nella tile corrente;
     * - se non si è già raccolto.
     * Notifica agli observer, registrati al soggetto onTryTakeItemSubject,
     * lo stato di questa operazione.
     * @param item item da prendere
     */
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

            status.setItem(item);
        }
        onTryTakeItemSubject.notifyObservers(status);
    }

    /**
     *
     * @param tile tile corrente
     * @param item item che necessita
     * @return l'interactable nella tile corrente che contiene nella sua lista
     * itemNeededToUse l'item passato come parametro.
     */
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

    /**
     *
     * @param item item da usare
     * @return lo stato dell'operazione
     */
    private UsingItemStatus tryUseItem(Item item)
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

            status.getArgs().interactable = interactable;
        }
        return status;
    }

    /**
     * Prova ad utilizzare l'oggetto. Se l'operazione va a buon
     * fine aggiunge l'item al dizionario degl'item utilizzati.
     * Notifica agli observer, registrati al soggetto onTryUseItemSubject,
     * lo stato di questa operazione.
     * @param item item da usare
     */
    public void useItem(Item item)
    {
        UsingItemStatus status = UsingItemStatus.unknown;

        if (item.getItemType() == ItemType.itemToUseInteractable)
        {
            status = tryUseItem(item);
        }
        else if (item.getItemType() == ItemType.document)
        {
            status = UsingItemStatus.unusable;
        }

        status.getArgs().item = item;
        onTryUseItemSubject.notifyObservers(status);
    }

    /**
     * Controlla se possiedi nell'inventario gli item necessari
     * per utilizzare l'interactable passato come parametro.
     * @param interactable interactable passato
     * @return true se possiedi tutti gli item
     */
    public boolean haveNecessaryItemsToUseInteractable(Interactable interactable)
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

    /**
     *
     * @param tile tile corrente
     * @return stato di questo controllo
     */
    private MovingStatus isInteractableNeededToEnterUnlocked(Tile tile)
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
                if (!haveNecessaryAnswer(interactable.getGuessingGameId()))
                {
                    status = MovingStatus.needAnswer;
                }
            }
        }

        return status;
    }


    /**
     * Controlla se uno spostamento è consentito.
     * @param coordinates coordinate verso la quale si vuole effettuare lo spostamento
     * @return stato dell'operazione
     */
    private MovingStatus isMovingAllowed(Coordinates coordinates)
    {
        MovingStatus status = MovingStatus.offTheMap;

        if (map.isPermittedMovement(currentPositionRiga, currentPositionColonna, coordinates))
        {
            Tile tile = map.getNextTile(currentPositionRiga, currentPositionColonna, coordinates);
            status = isInteractableNeededToEnterUnlocked(tile);
            status.getArgs().nextTile = tile;
        }

        return status;
    }

    /**
     * Prova ad effettuare lo spostamento nella direzione passata come parametro.
     * Notifica agli observer, registrati al soggetto onTryMovePlayerSubject,
     * lo stato di questa operazione.
     * @param coordinates coordinate verso la quale si vuole effettuare lo spostamento
     */
    public void tryMove(Coordinates coordinates)
    {
        MovingStatus status = isMovingAllowed(coordinates);
        status.getArgs().startTile = getCurrentTile();
        status.getArgs().coordinates = coordinates;

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

    /**
     * Controlla se hai già risposto all'indovinello passato come parametro.
     * @param guessingGame UUID dell'indovinello
     * @return true se hai giò risposto all'indovinello
     */
    private boolean haveNecessaryAnswer(UUID guessingGame)
    {
        return guessingGame == null || guessingGamesHandler.isResolvedGuessingGame(guessingGame);
    }

    /**
     * Interagisci con un interactable di tipo chest con indovinello.
     * Se l'operazione va a buon fine aggiunge l'interactable alla lista
     * degli interactable usati. Aggiunge gli oggetti contenuti
     * nell'inteactable all'inventario. L'operazione va
     * a buon fine se la risposta alla domanda è stata data.
     * @param interactable interactable con indovinello
     * @return lo stato dell'operazione
     */
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

    /**
     * Interagisci con un interactable di tipo senza indovinello.
     * Se l'operazione va a buon fine aggiunge l'interactable alla lista
     * degli interactable usati. Aggiunge gli oggetti contenuti
     * nell'interactable all'inventario. L'operazione va a
     * buon fine se tutti gli item necessari sono stati usati, se questi ci sono.
     * @param interactable interactable senza indovinello
     * @return lo stato dell'operazione
     */
    private InteractStatus interactNormalChest(Interactable interactable)
    {
        InteractStatus status = InteractStatus.needItem;

        if (haveNecessaryItemsToUseInteractable(interactable))
        {
            interactableHandler.addUsedInteractable(interactable);
            inventoryManager.addItems(interactable.getContainedItems());
            status = InteractStatus.used;
        }

        return status;
    }

    /**
     * Gestisce l'interazione con un interactable di tipo chest.
     * @param interactable interactable
     * @return lo stato dell'operazione
     */
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

    /**
     * Interagisce con un interactable.
     * Se l'operazione va a buon fine aggiunge l'interactable alla lista
     * degli interactable usati. Aggiunge gli oggetti contenuti
     * nell'interactable all'inventario, se di tipo chest.
     * Notifica agli observer, registrati al subject onTryInteractSubject, lo stato di questa operazione.
     * @param interactable interactable
     */
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

        status.setInteractable(interactable);
        onTryInteractSubject.notifyObservers(status);
    }

    /**
     *
     * @param tile tile nella quale è presente l'interactable
     * @return restituisce la lista degli indovinelli presenti nella
     * tile passata come parametro
     */
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

    /**
     * Prova a risolvere uno degli indovinelli presente
     * nella lista passata come parametro usando come risposta
     * la stringa passata come parametro. Lo stato dell'operazione
     * viene determinato sulla base di un sistema di priorità.
     * @param guessingGames indovinello proposto
     * @param answer risposta
     * @return stato dell'operazione
     */
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

        status.setGuessingGame(guessingGameFound);
        return status;
    }

    /**
     * Prova a risolvere un indovinello presente
     * nella tile corrente usando come risposta
     * la stringa passata come parametro.
     * Notifica agli observer, registrati al soggetto onTrySolveGuessingGameSubject,
     * lo stato di questa operazione.
     * @param answer risposta
     */
    public void solveQuestion(String answer)
    {
        AnswerStatus status;
        Tile currentPos = getCurrentTile();
        status = trySolveQuestion(getInteractableGuessingGame(currentPos), answer);

        onTrySolveGuessingGameSubject.notifyObservers(status);
    }

    /**
     * Notifica agli observer, registrati al subject onObserve,
     * la descrizione della tile corrente. Questa può essere:
     * - Corta;
     * - Dettagliata;
     * - Limitata.
     * Corta e dettagliata dipendono dal valore passato come parametro,
     * limitata dallo stato corrente della tile (illuminata o meno).
     * @param isFullDescription true se la descrizione è dettagliata
     */
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

    /**
     * Notifica agli observer, registrati al subject onLookItem,
     * la descrizione dell'item passato come
     * parametro. Se questo è di tipo document ci sarà
     * una variazione nella stringa.
     * @param item item da osservare
     */
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

    /**
     *
     * @return la tile corrente
     */
    private Tile getCurrentTile()
    {
        return map.getTile(currentPositionRiga, currentPositionColonna);
    }

    /**
     * Deserializza le informazioni dal file json se isContinuing
     * vale true, altrimenti inizializza da zero il game.
     * @param isContinuing se deve riprendere dall'ultimo salvataggio effettuato
     * @param startConfig informazioni iniziali
     * @throws Exception eccezione durante il parse del file
     */
    private void loadSaveFile(boolean isContinuing, StartConfig startConfig) throws Exception
    {
        if (isContinuing)
        {
            if (Utilities.fileExist(Utilities.SAVE_JSON_PATH))
            {
                RootPlayerJson player = JsonParserUtilities.getClassFromJson(Utilities.SAVE_JSON_PATH, RootPlayerJson.class);
                currentPositionRiga = player.lastPositionRiga;
                currentPositionColonna = player.lastPositionColonna;
                interactableHandler.setUsedIteractable(player.usedInteractable);
                inventoryManager.setInventoryList(player.inventory);
                inventoryManager.setUsedItemsMap(player.usedItems);
                dialoguesHandler.setDialoguesMade(player.dialoguesMade);
                guessingGamesHandler.setUSedGuessingGame(player.usedGuessingGame);
                endGame = player.endGame;
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

    /**
     * Serializza le informazioni relative al salvataggio
     * e le scrive nel file save.json.
     */
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
            String content = JsonParserUtilities.serializeClassToJson(player);
            boolean result = Utilities.writeFile(Utilities.SAVE_JSON_PATH, content, false);

            if (!result)
            {
                System.err.println("Problema durante la scrittura del file di salvataggio!");
            }
        }
    }
}
