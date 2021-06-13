package game.managers;

import game.entity.item.Item;
import game.gameUtilities.Utilities;
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;

import java.util.*;
import java.util.Map;

/**
 * Handler degl'item.
 */
public class ItemsHandler
{
    private static ItemsHandler instance;

    private Map<UUID, Item> itemsDictionary;

    private ItemsHandler() throws Exception
    {
        itemsDictionary = new HashMap<>();
        loadItemsCollection();
    }

    /**
     *
     * @return l'istanza della classe.
     * @throws Exception eccezione che si potrebbe generare
     */
    public static ItemsHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new ItemsHandler();
        }

        return instance;
    }

    /**
     *
     * @param name nome o alias dell'item
     * @return la lista dei vari item con lo stesso nome/alias
     */
    public List<Item> getItemsWithSameAlias(String name)
    {
        List<Item> itemsList = new ArrayList<>();

        if(itemsDictionary != null)
        {
            Iterator iterator = itemsDictionary.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                Item tempItem = (Item) entry.getValue();

                if (tempItem.getName().equalsIgnoreCase(name))
                {
                    itemsList.add(tempItem);
                }
                else
                {
                    boolean found = false;

                    for(int j = 0; j < tempItem.getAlias().size() && !found; j++)
                    {
                        if (tempItem.getAlias().get(j).equalsIgnoreCase(name))
                        {
                            itemsList.add(tempItem);
                            found = true;
                        }
                    }
                }
            }
        }

        return itemsList;
    }

    /**
     *
     * @param name nome o alias dell'item
     * @return l'item con lo stesso nome / alias se esiste
     */
    public Item getItem(String name)
    {
        Item item = null;

        if(itemsDictionary != null)
        {
            Iterator iterator = itemsDictionary.entrySet().iterator();
            while (iterator.hasNext() && item == null)
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                Item tempItem = (Item) entry.getValue();

                if (tempItem.getName().equalsIgnoreCase(name))
                {
                    item = tempItem;
                }
                else
                {
                    for(int j = 0; j < tempItem.getAlias().size() && item == null; j++)
                    {
                        if (tempItem.getAlias().get(j).equalsIgnoreCase(name))
                        {
                            item = tempItem;
                        }
                    }
                }
            }
        }

        return item;
    }

    /**
     *
     * @param idItem UUID dell'item
     * @return l'item se esiste
     */
    public Item getItem(UUID idItem)
    {
        return itemsDictionary.get(idItem);
    }

    /**
     *
     * @param nameItem nome o alias dell'item
     * @return true se l'item con quel nome è contenuto nel dizionario degl'item
     */
    public boolean contain(String nameItem)
    {
        return getItem(nameItem) != null;
    }

    /**
     * Deserializza le informazioni dal file json.
     * @throws Exception eccezione durante il parse del file
     */
    private void loadItemsCollection() throws Exception
    {
        RootItemsCollectionJson itemsCollectionJson = null;

        if (Utilities.fileExist(Utilities.ITEMS_JSON_PATH))
        {
            try
            {
                itemsCollectionJson = JsonParserUtilities.getClassFromJson(Utilities.ITEMS_JSON_PATH, RootItemsCollectionJson.class);

                for(int i = 0; i < itemsCollectionJson.itemsList.size(); i++)
                {
                    Item item = itemsCollectionJson.itemsList.get(i);
                    itemsDictionary.put(item.getId(), item);
                }
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file items.json");
            }
        }
        else
        {
            throw new Exception("File items.json non presente sul disco");
        }

    }

    /**
     * Classe necessaria per la deserializzazione del file json.
     */
    private class RootItemsCollectionJson
    {
        public List<Item> itemsList;
    }

}
