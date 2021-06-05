package game.managers;

import game.entity.interactable.Interactable;
import game.gameUtilities.observerPattern.Subject;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

import java.util.*;


public class InteractableHandler
{
    private static InteractableHandler instance;

    private List<UUID> usedIteractable;

    private Map<UUID, Interactable> interactableDictionary;

    private Subject<Interactable> onUnlockInteractable;

    private InteractableHandler() throws Exception
    {
        interactableDictionary = new HashMap<>();
        usedIteractable = new ArrayList<>();
        onUnlockInteractable = new Subject<>();
        loadInteractableCollection();
    }

    public static InteractableHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new InteractableHandler();
        }

        return instance;
    }

    public Subject<Interactable> getOnUnlockInteractable()
    {
        return onUnlockInteractable;
    }


    public Interactable getInteractable(UUID idInteractable)
    {
        return interactableDictionary.get(idInteractable);
    }


    public List<Interactable> getInteractables(List<UUID> interactablesId)
    {
        List<Interactable> result = new ArrayList<>();

        if (interactablesId != null)
        {
            for (UUID id : interactablesId)
            {
                Interactable interactable = getInteractable(id);

                if (interactable != null)
                {
                    result.add(interactable);
                }
            }
        }
        return result;
    }

    public Interactable getInteractable(String name)
    {
        Interactable interactable = null;

        if (interactableDictionary != null)
        {
            Iterator iterator = interactableDictionary.entrySet().iterator();
            while (iterator.hasNext() && interactable == null)
            {
                Map.Entry entry = (Map.Entry) iterator.next();
                Interactable tempInteractable = (Interactable) entry.getValue();

                if (tempInteractable.getName().equalsIgnoreCase(name))
                {
                    interactable = tempInteractable;
                }
                else
                {
                    for(int j = 0; j < tempInteractable.getAlias().size() && interactable == null; j++)
                    {
                        if (tempInteractable.getAlias().get(j).equalsIgnoreCase(name))
                        {
                            interactable = tempInteractable;
                        }
                    }
                }
            }
        }

        return interactable;
    }


    private void loadInteractableCollection() throws Exception
    {
        if (Utilities.fileExist(Utilities.INTERACTABLES_JSON_PATH))
        {
            RootInteractableCollectionJson interactableCollectionJson = JsonParser.GetClassFromJson(Utilities.INTERACTABLES_JSON_PATH,
                    RootInteractableCollectionJson.class);

            for(int i = 0; i < interactableCollectionJson.interactableList.size(); i++)
            {
                Interactable interactable = interactableCollectionJson.interactableList.get(i);
                interactableDictionary.put(interactable.getId(), interactable);
            }

        }
        else
        {
            throw new Exception("File degli interactable non presente sul disco");
        }
    }


    public void setUsedIteractable(List<UUID> usedIteractable)
    {
        if (usedIteractable != null)
        {
            this.usedIteractable = usedIteractable;
        }
    }

    public void addUsedInteractable(Interactable interactable)
    {
        usedIteractable.add(interactable.getId());

        onUnlockInteractable.notifyObservers(interactable);
    }

    public List<UUID> getUsedIteractable()
    {
        return usedIteractable;
    }

    private class RootInteractableCollectionJson
    {
        public List<Interactable> interactableList;
    }

}
