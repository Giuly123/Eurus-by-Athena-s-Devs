package game.managers;

import game.entity.guessingGame.GuessingGame;
import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuessingGamesHandler
{
    private static GuessingGamesHandler instance;
    private List<UUID> resolvedGuessingGame;
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


    public void setUSedGuessingGame(List<UUID> guessingGameUsed)
    {
        if (guessingGameUsed != null)
        {
            this.resolvedGuessingGame = guessingGameUsed;
        }
    }

    public boolean isResolvedGuessingGame(UUID guessingGameId)
    {
        return resolvedGuessingGame.contains(guessingGameId);
    }

    public boolean isResolvedGuessingGame(GuessingGame guessingGame)
    {
        return resolvedGuessingGame.contains(guessingGame.getId());
    }

    public List<UUID> getUsedGuessingGame()
    {
        return resolvedGuessingGame;
    }

    public GuessingGame getGuessingGame(UUID guessingGameId)
    {
        return guessingGamesDictionary.get(guessingGameId);
    }


    private void loadGuessingGamesCollection() throws Exception
    {
        RootGuessingGamesCollectionJson guessingGamesCollectionJson = null;

        if (Utilities.fileExist(Utilities.GUESSING_GAMES_JSON_PATH))
        {
            try
            {
                guessingGamesCollectionJson = JsonParser.GetClassFromJson(Utilities.GUESSING_GAMES_JSON_PATH, RootGuessingGamesCollectionJson.class);

                for(int i = 0; i < guessingGamesCollectionJson.guessingGameList.size(); i++)
                {
                    GuessingGame guessingGame = guessingGamesCollectionJson.guessingGameList.get(i);
                    guessingGamesDictionary.put(guessingGame.getId(), guessingGame);
                }
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file guessingGames.json");
            }
        }
        else
        {
            throw new Exception("File guessingGames.json non presente sul disco");
        }
    }


    private class RootGuessingGamesCollectionJson
    {
        public List<GuessingGame> guessingGameList;
    }
}
