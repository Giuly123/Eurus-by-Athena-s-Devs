package game.managers;

import game.entity.dialog.DialogEvent;
import game.entity.guessingGame.GuessingGame;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuessingGamesHandler
{
    private static GuessingGamesHandler instance;
    private List<UUID> guessingGameUsed;
    private Map<UUID, GuessingGame> guessingGamesDictionary;

    private GuessingGamesHandler() throws Exception
    {
        loadGuessingGamesCollection();
    }

    public static GuessingGamesHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new GuessingGamesHandler();
        }

        return instance;
    }


    public void setGuessingGameUsed(List<UUID> guessingGameUsed)
    {
        if (guessingGameUsed != null)
        {
            this.guessingGameUsed = guessingGameUsed;
        }
    }

    public boolean isUsedGuessingGame(UUID guessingGameId)
    {
        return guessingGameUsed.contains(guessingGameId);
    }


    private void loadGuessingGamesCollection() throws Exception
    {
        RootGuessingGamesCollectionJson guessingGamesCollectionJson = null;

        if (Utilities.fileExist(Utilities.DIALOGUES_JSON_PATH))
        {
            try
            {
                guessingGamesCollectionJson = JsonParser.GetClassFromJson(Utilities.DIALOGUES_JSON_PATH, RootGuessingGamesCollectionJson.class);

                for(int i = 0; i < guessingGamesCollectionJson.guessingGameList.size(); i++)
                {
                    GuessingGame guessingGame = guessingGamesCollectionJson.guessingGameList.get(i);
                    guessingGamesDictionary.put(guessingGame.getId(), guessingGame);
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


    private class RootGuessingGamesCollectionJson
    {
        public List<GuessingGame> guessingGameList;
    }
}
