package game.managers;

import game.entity.dialog.DialogEvent;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

import java.util.*;

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

    public static DialoguesHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new DialoguesHandler();
        }
        return instance;
    }


    public DialogEvent getDialog(UUID idDialog)
    {
        return dialoguesDictionary.get(idDialog);
    }


    public void addDialogToMade(DialogEvent dialogEvent)
    {
        dialoguesMade.add(dialogEvent.getId());
    }

    public void addDialogToMade(UUID dialogEventId)
    {
        dialoguesMade.add(dialogEventId);
    }


    public void setDialoguesMade(List<UUID> dialoguesMade)
    {
        if (dialoguesMade != null)
        {
            this.dialoguesMade = dialoguesMade;
        }
    }

    public boolean isMadeDialog(UUID dialogID)
    {
        return dialoguesMade.contains(dialogID);
    }


    public List<UUID> getDialoguesMade()
    {
        return dialoguesMade;
    }

    private void loadDialoguesCollection() throws Exception
    {
        RootDialoguesCollectionJson dialoguesCollectionJson = null;

        if (Utilities.fileExist(Utilities.DIALOGUES_JSON_PATH))
        {
            try
            {
                dialoguesCollectionJson = JsonParser.GetClassFromJson(Utilities.DIALOGUES_JSON_PATH, RootDialoguesCollectionJson.class);

                for(int i = 0; i < dialoguesCollectionJson.dialogEventList.size(); i++)
                {
                    DialogEvent dialog = dialoguesCollectionJson.dialogEventList.get(i);
                    dialoguesDictionary.put(dialog.getId(), dialog);
                }
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file dialogues.json");
            }
        }
        else
        {
            throw new Exception("File dialogues.json non presente sul disco");
        }
    }


    private class RootDialoguesCollectionJson
    {
        public List<DialogEvent> dialogEventList;
    }
}
