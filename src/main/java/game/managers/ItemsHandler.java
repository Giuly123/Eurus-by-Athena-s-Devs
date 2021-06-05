package game.managers;

import game.entity.item.Item;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

import java.util.*;
import java.util.Map;

public class ItemsHandler
{
    private static ItemsHandler instance;

    private Map<UUID, Item> itemsDictionary;

    private ItemsHandler() throws Exception
    {
        itemsDictionary = new HashMap<>();
        loadItemsCollection();
    }

    public static ItemsHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new ItemsHandler();
        }

        return instance;
    }


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


    public Item getItem(UUID idItem)
    {
        return itemsDictionary.get(idItem);
    }


    public boolean contain(String nameItem)
    {
        return getItem(nameItem) != null;
    }


    private void loadItemsCollection() throws Exception
    {

        RootItemsCollectionJson itemsCollectionJson = null;

        if (Utilities.fileExist(Utilities.ITEMS_JSON_PATH))
        {
            try
            {
                itemsCollectionJson = JsonParser.GetClassFromJson(Utilities.ITEMS_JSON_PATH, RootItemsCollectionJson.class);

                for(int i = 0; i < itemsCollectionJson.itemsList.size(); i++)
                {
                    Item item = itemsCollectionJson.itemsList.get(i);
                    itemsDictionary.put(item.getId(), item);
                }
            }
            catch (Exception e)
            {
                System.out.println("Errore: problema parsing file items.json");
            }
        }
        else
        {
            throw new Exception("File items.json non presente sul disco");
        }

    }

    private class RootItemsCollectionJson
    {
        public List<Item> itemsList;
    }

}
