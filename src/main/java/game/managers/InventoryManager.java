package game.managers;


import game.entity.item.Item;
import game.gameUtilities.Subject;

import java.util.*;

public class InventoryManager
{
    private static InventoryManager instance;

    private List<UUID> inventoryList;
    private Map<UUID, List<UUID>> usedItems;

    private ItemsHandler itemsHandler;

    private Subject<List<UUID>> onLoadInventory;

    private Subject<Item> onAddedItemToInventory;

    private Subject<Item> onRemovedItemToInventory;

    private InventoryManager() throws Exception
    {
        itemsHandler = ItemsHandler.getInstance();
        onAddedItemToInventory = new Subject<>();
        onRemovedItemToInventory = new Subject<>();
        onLoadInventory = new Subject<>();
    }

    public static InventoryManager getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new InventoryManager();
        }

        return instance;
    }

    public Subject<Item> getOnAddedItemToInventory()
    {
        return onAddedItemToInventory;
    }

    public Subject<Item> getOnRemovedItemToInventory()
    {
        return onRemovedItemToInventory;
    }

    public Subject<List<UUID>> getOnLoadInventory()
    {
        return onLoadInventory;
    }


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


    public void addItem(UUID itemId)
    {
        Item item = itemsHandler.getItem(itemId);

        if (item != null)
        {
            addItem(item);
        }

    }

    public void addItem(Item item)
    {
//        inventoryDictionary.put(item.getId(), item);
        inventoryList.add(item.getId());
        onAddedItemToInventory.notifyObservers(item);
    }


    public void removeItem(Item item)
    {
//        inventoryDictionary.remove(item.getName());
        inventoryList.remove(item.getId());
        onRemovedItemToInventory.notifyObservers(item);
    }


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


    public boolean inventoryContains(UUID id)
    {
        return inventoryList.contains(id);
    }

    public boolean usedItemsContains(UUID idInteractable, UUID idItem)
    {
        List<UUID> listaId = usedItems.get(idInteractable);
        return listaId.contains(idItem);
    }


    public Item getItem(String name)
    {
        Item item = null;

        item = itemsHandler.getItem(name);

        if (item != null && !inventoryList.contains(item.getId()))
        {
            item = null;
        }

        return item;
    }


    public void setInventoryList(List<UUID> inventoryList)
    {
        this.inventoryList = inventoryList;
        onLoadInventory.notifyObservers(this.inventoryList);
    }

    public void setUsedItemsMap(Map<UUID, List<UUID>> usedItems)
    {
        this.usedItems = usedItems;
    }

    public List<UUID> getInvetoryList()
    {
        return inventoryList;
    }


    public Map<UUID, List<UUID>> getUsedItems()
    {
        return usedItems;
    }
}
