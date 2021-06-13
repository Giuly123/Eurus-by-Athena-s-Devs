package game.managers;

import game.entity.dialog.DialogEvent;
import game.gameUtilities.Utilities;
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;

import java.util.*;

/**
 * Handler dei dialoghi.
 */
public class DialoguesHandler
{
    private static DialoguesHandler instance;
    private List<UUID> dialoguesMade;
    private Map<UUID, DialogEvent> dialoguesDictionary;


    private DialoguesHandler() throws Exception
    {
        dialoguesDictionary = new HashMap<>();
        dialoguesMade = new ArrayList<>();
        loadDialoguesCollection();
    }

    /**
     *
     * @return istanza della classe.
     * @throws Exception eccezione che si potrebbe generare
     */
    public static DialoguesHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new DialoguesHandler();
        }
        return instance;
    }

    /**
     *
     * @param idDialog UUID del dialogo
     * @return l'entità dialogo
     */
    public DialogEvent getDialog(UUID idDialog)
    {
        return dialoguesDictionary.get(idDialog);
    }

    /**
     * Aggiunge un dialogo alla lista dei dialoghi già fatti.
     * @param dialogEvent dialogo
     */
    public void addDialogToMade(DialogEvent dialogEvent)
    {
        dialoguesMade.add(dialogEvent.getId());
    }

    /**
     * Aggiunge un dialogo alla lista dei dialoghi già fatti.
     * @param dialogEventId UUID del dialogo
     */
    public void addDialogToMade(UUID dialogEventId)
    {
        dialoguesMade.add(dialogEventId);
    }

    /**
     * Imposta la lista dei dialoghi già fatti.
     * @param dialoguesMade lista di UUID dei dialoghi
     */
    public void setDialoguesMade(List<UUID> dialoguesMade)
    {
        if (dialoguesMade != null)
        {
            this.dialoguesMade = dialoguesMade;
        }
    }

    /**
     *
     * @param dialogID UUID del dialogo
     * @return true se il dialogo è già stato fatto
     */
    public boolean isMadeDialog(UUID dialogID)
    {
        return dialoguesMade.contains(dialogID);
    }

    /**
     *
     * @return restituisce la lista dei dialoghi fatti
     */
    public List<UUID> getDialoguesMade()
    {
        return dialoguesMade;
    }

    /**
     * Deserializza le informazioni dal file json.
     * @throws Exception eccezione durante il parse del file
     */
    private void loadDialoguesCollection() throws Exception
    {
        RootDialoguesCollectionJson dialoguesCollectionJson = null;

        if (Utilities.fileExist(Utilities.DIALOGUES_JSON_PATH))
        {
            try
            {
                dialoguesCollectionJson = JsonParserUtilities.getClassFromJson(Utilities.DIALOGUES_JSON_PATH, RootDialoguesCollectionJson.class);

                for(int i = 0; i < dialoguesCollectionJson.dialogEventList.size(); i++)
                {
                    DialogEvent dialog = dialoguesCollectionJson.dialogEventList.get(i);
                    dialoguesDictionary.put(dialog.getId(), dialog);
                }
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file dialogues.json!");
            }
        }
        else
        {
            throw new Exception("File dialogues.json non presente sul disco!");
        }
    }

    /**
     * Classe necessaria per la deserializzazione del file json.
     */
    private class RootDialoguesCollectionJson
    {
        public List<DialogEvent> dialogEventList;
    }
}
