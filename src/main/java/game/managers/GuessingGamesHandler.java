package game.managers;

import game.entity.guessingGame.GuessingGame;
import game.gameUtilities.Utilities;
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;

import java.util.*;

/**
 * Handler degli indovinelli.
 */
public class GuessingGamesHandler
{
    private static GuessingGamesHandler instance;
    private List<UUID> resolvedGuessingGame;
    private Map<UUID, GuessingGame> guessingGamesDictionary;

    private GuessingGamesHandler() throws Exception
    {
        guessingGamesDictionary = new HashMap<>();
        resolvedGuessingGame = new ArrayList<>();
        loadGuessingGamesCollection();
    }

    /**
     *
     * @return l'istanza della classe.
     * @throws Exception eccezioni che potrebbero generarsi
     */
    public static GuessingGamesHandler getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new GuessingGamesHandler();
        }

        return instance;
    }

    /**
     * Setta gli indovinelli già risolti.
     * @param guessingGameUsed indovinelli risolti
     */
    public void setUSedGuessingGame(List<UUID> guessingGameUsed)
    {
        if (guessingGameUsed != null)
        {
            this.resolvedGuessingGame = guessingGameUsed;
        }
    }

    /**
     * Aggiunge un indovinello alla lista degli indovinelli risolti.
     * @param guessingGameId UUID dell'indovinello
     */
    public void addGuessingGameToResolved(UUID guessingGameId)
    {
        if (guessingGameId != null)
        {
            resolvedGuessingGame.add(guessingGameId);
        }
    }

    /**
     *
     * @param guessingGameId  UUID dell'indovinello
     * @return true se l'indovinello è risolto
     */
    public boolean isResolvedGuessingGame(UUID guessingGameId)
    {
        return resolvedGuessingGame.contains(guessingGameId);
    }

    /**
     *
     * @param guessingGame indovinello
     * @return true se l'indovinello è risolto
     */
    public boolean isResolvedGuessingGame(GuessingGame guessingGame)
    {
        return resolvedGuessingGame.contains(guessingGame.getId());
    }

    /**
     *
     * @return lista di UUID degli indovinelli già risolti
     */
    public List<UUID> getUsedGuessingGame()
    {
        return resolvedGuessingGame;
    }

    /**
     *
     * @param guessingGameId UUID dell'indovinello
     * @return l'indovinello se esiste
     */
    public GuessingGame getGuessingGame(UUID guessingGameId)
    {
        return guessingGamesDictionary.get(guessingGameId);
    }

    /**
     * Deserializza le informazioni dal file json.
     * @throws Exception eccezione durante il parse del file
     */
    private void loadGuessingGamesCollection() throws Exception
    {
        RootGuessingGamesCollectionJson guessingGamesCollectionJson = null;

        if (Utilities.fileExist(Utilities.GUESSING_GAMES_JSON_PATH))
        {
            try
            {
                guessingGamesCollectionJson = JsonParserUtilities.getClassFromJson(Utilities.GUESSING_GAMES_JSON_PATH, RootGuessingGamesCollectionJson.class);

                for(int i = 0; i < guessingGamesCollectionJson.guessingGameList.size(); i++)
                {
                    GuessingGame guessingGame = guessingGamesCollectionJson.guessingGameList.get(i);
                    guessingGamesDictionary.put(guessingGame.getId(), guessingGame);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("Errore: problema parsing file guessingGames.json");
            }
        }
        else
        {
            throw new Exception("File guessingGames.json non presente sul disco");
        }
    }

    /**
     * Classe necessaria per la deserializzazione del file json.
     */
    private class RootGuessingGamesCollectionJson
    {
        public List<GuessingGame> guessingGameList;
    }
}
