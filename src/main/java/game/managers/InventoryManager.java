package game.managers;

import game.entity.item.Item;
import game.gameUtilities.observerPattern.Subject;

import java.util.*;

/**
 * Manager dell'inventario
 */
public class InventoryManager
{
    private static InventoryManager instance;

    private List<UUID> inventoryList;
    private Map<UUID, List<UUID>> usedItems;

    private ItemsHandler itemsHandler;

    private Subject<List<UUID>> onLoadedInventory;
    private Subject<Item> onAddedItemToInventory;
    private Subject<Item> onRemovedItemToInventory;

    private InventoryManager() throws Exception
    {
        itemsHandler = ItemsHandler.getInstance();
        onAddedItemToInventory = new Subject<>();
        onRemovedItemToInventory = new Subject<>();
        onLoadedInventory = new Subject<>();
    }

    /**
     *
     * @return l'istanza della classe.
     * @throws Exception eccezione che si potrebbe generare
     */
    public static InventoryManager getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new InventoryManager();
        }

        return instance;
    }

    /**
     *
     * @return il subject onAddedItemToInventory
     */
    public Subject<Item> getOnAddedItemToInventory()
    {
        return onAddedItemToInventory;
    }

    /**
     *
     * @return il subject onRemovedItemToInventory
     */
    public Subject<Item> getOnRemovedItemToInventory()
    {
        return onRemovedItemToInventory;
    }

    /**
     *
     * @return il subject onLoadInventory
     */
    public Subject<List<UUID>> getOnLoadedInventory()
    {
        return onLoadedInventory;
    }

    /**
     * Aggiunga una lista di item all'inventario.
     * @param itemsId lista di UUID degli item
     * @return true se la lista è stata aggiunta all'invetario
     */
    public boolean addItems(List<UUID> itemsId)
    {
        boolean isSuccessful = false;

        if (itemsId != null)
        {
            for (int i = 0; i < itemsId.size(); i++)
            {
                addItem(itemsId.get(i));
            }

            isSuccessful = true;
        }

        return isSuccessful;
    }

    /**
     * Aggiunge un item all'inventario.
     * @param itemId UUID dell'item
     */
    public void addItem(UUID itemId)
    {
        Item item = itemsHandler.getItem(itemId);

        if (item != null)
        {
            addItem(item);
        }
    }

    /**
     * Aggiunge un item all'inventario.
     * @param item item da aggiungere
     */
    public void addItem(Item item)
    {
        inventoryList.add(item.getId());
        onAddedItemToInventory.notifyObservers(item);
    }

    /**
     * Rimuove un item dall'inventario.
     * @param item item da rimuovere
     */
    public void removeItem(Item item)
    {
        inventoryList.remove(item.getId());
        onRemovedItemToInventory.notifyObservers(item);
    }

    /**
     * Aggiunge un item al dizionario degli item utilizzati.
     * @param interactableId UUID dell'interactable con cui ha interagito l'item
     * @param itemId UUID dell'item
     */
    public void addUsedItem(UUID interactableId, UUID itemId)
    {
        List<UUID> listId = usedItems.get(interactableId);
        if (listId == null)
        {
            listId = new ArrayList<UUID>();
            usedItems.put(interactableId, listId);
        }
        listId.add(itemId);
//        onAddedItemToInventory.notifyObservers(item);
    }

    /**
     *
     * @param id UUID dell'item
     * @return true se l'inventario contiene quell'item
     */
    public boolean inventoryContains(UUID id)
    {
        return inventoryList.contains(id);
    }

    /**
     *
     * @param idInteractable UUID dell'interactable
     * @param idItem UUID dell'item
     * @return true se l'item è stato usato con quell'interactable
     */
    public boolean usedItemsContains(UUID idInteractable, UUID idItem)
    {
        boolean result = false;

        List<UUID> listaId = usedItems.get(idInteractable);

        if (listaId != null)
        {
            result = listaId.contains(idItem);
        }

        return result;
    }

    /**
     *
     * @param name nome dell'item
     * @return l'item se è presente nell'inventario
     */
    public Item getItem(String name)
    {
        Item result = null;

        List<Item> itemsList = itemsHandler.getItemsWithSameAlias(name);

        for(int i = 0; i < itemsList.size() && result == null; i++)
        {
            if (inventoryList.contains(itemsList.get(i).getId()))
            {
                result = itemsList.get(i);
            }
        }

        return result;
    }

    /**
     * Imposta la lista dell'inventario
     * @param inventoryList lista di UUID
     */
    public void setInventoryList(List<UUID> inventoryList)
    {
        if (inventoryList != null)
        {
            this.inventoryList = inventoryList;
            onLoadedInventory.notifyObservers(this.inventoryList);
        }
    }

    /**
     * Imposta il dizionario degl'item usati.
     * @param usedItems UUID dell'item usato
     */
    public void setUsedItemsMap(Map<UUID, List<UUID>> usedItems)
    {
        if (usedItems != null)
        {
            this.usedItems = usedItems;
        }
    }

    /**
     *
     * @return la lista dell'inventario
     */
    public List<UUID> getInvetoryList()
    {
        return inventoryList;
    }

    /**
     *
     * @return map degli UUID degl'item usati
     */
    public Map<UUID, List<UUID>> getUsedItems()
    {
        return usedItems;
    }
}
