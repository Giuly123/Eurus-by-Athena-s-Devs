package game.managers;

import game.entity.interactable.Interactable;
import game.gameUtilities.observerPattern.Subject;
import game.gameUtilities.Utilities;
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;

import java.util.*;

/**
 * Handler degli interactable.
 */
public class InteractableHandler
{
    private static InteractableHandler instance;

    private List<UUID> usedIteractable;

    private Map<UUID, Interactable> interactableDictionary;

    private Subject<Interactable> onUsedInteractable;

    private InteractableHandler() throws Exception
    {
        interactableDictionary = new HashMap<>();
        usedIteractable = new ArrayList<>();
        onUsedInteractable = new Subject<>();
        loadInteractableCollection();
    }

    /**
     *
     * @return l'istanza della classe
     * @throws Exception eccezioni che potrebbero generarsi
     */
    public static InteractableHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new InteractableHandler();
        }

        return instance;
    }

    /**
     *
     * @return il soggetto onUsedInteractable
     */
    public Subject<Interactable> getOnUsedInteractable()
    {
        return onUsedInteractable;
    }

    /**
     *
     * @param idInteractable UUID dell'interactable
     * @return l'interactale se esiste
     */
    public Interactable getInteractable(UUID idInteractable)
    {
        return interactableDictionary.get(idInteractable);
    }

    /**
     *
     * @param interactablesId lista di UUID degli interactable
     * @return la lista degli interactable
     */
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

    /**
     * Restituisce un interactable che ha come nome
     * o come alias la stringa passata come parametro.
     * @param name nome dell'interactable
     * @return restituisce l'interactable se esiste
     */
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

    /**
     * Imposta la lista di interactable usati
     * @param usedIteractable lista di UUID di interactable
     */
    public void setUsedIteractable(List<UUID> usedIteractable)
    {
        if (usedIteractable != null)
        {
            this.usedIteractable = usedIteractable;
        }
    }

    /**
     * Aggiunge un interactable alla lista di interactable usati
     * @param interactable interactable
     */
    public void addUsedInteractable(Interactable interactable)
    {
        usedIteractable.add(interactable.getId());

        onUsedInteractable.notifyObservers(interactable);
    }

    /**
     *
     * @return la lista di UUID degli interactable usati
     */
    public List<UUID> getUsedIteractable()
    {
        return usedIteractable;
    }

    /**
     *
     * @param interactableId UUID dell'interactable
     * @return true se è stato usato
     */
    public boolean isUsedInteractalbe(UUID interactableId)
    {
        return usedIteractable.contains(interactableId);
    }

    /**
     * Deserializza le informazioni dal file json.
     * @throws Exception eccezione durante il parse del file
     */
    private void loadInteractableCollection() throws Exception
    {
        RootInteractableCollectionJson interactableCollectionJson = null;

        if (Utilities.fileExist(Utilities.INTERACTABLES_JSON_PATH))
        {
            try
            {
                interactableCollectionJson = JsonParserUtilities.getClassFromJson(Utilities.INTERACTABLES_JSON_PATH, RootInteractableCollectionJson.class);

                for(int i = 0; i < interactableCollectionJson.interactableList.size(); i++)
                {
                    Interactable interactable = interactableCollectionJson.interactableList.get(i);
                    interactableDictionary.put(interactable.getId(), interactable);
                }
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file interactables.json");
            }

        }
        else
        {
            throw new Exception("File degli interactable non presente sul disco");
        }
    }

    /**
     * Classe necessaria per la deserializzazione del file json.
     */
    private class RootInteractableCollectionJson
    {
        public List<Interactable> interactableList;
    }

}
