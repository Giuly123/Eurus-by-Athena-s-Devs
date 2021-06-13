package game.player;

import game.gameUtilities.Utilities;
import game.gameUtilities.jsonParserUtilities.JsonParserUtilities;

/**
 * Classe che rappresenta la configurazione iniziale.
 */
public class StartConfig
{
    public RootStartConfigJson startConfigJson;

    public StartConfig() throws Exception
    {
        loadStartConfig();
    }

    /**
     * Deserializza le informazioni dal file json.
     * @throws Exception eccezione durante il parse del file
     */
    private void loadStartConfig() throws Exception
    {
        if (Utilities.fileExist(Utilities.START_CONFIG_JSON_PATH))
        {
            try
            {
                startConfigJson = JsonParserUtilities.getClassFromJson(Utilities.START_CONFIG_JSON_PATH, RootStartConfigJson.class);
            }
            catch (Exception e)
            {
                throw new Exception("Errore: problema parsing file startConfig.json");
            }
        }
        else
        {
            throw new Exception("File startConfig.json non presente sul disco");
        }
    }

    /**
     * Classe necessaria per la deserializzazione del file json.
     */
    public class RootStartConfigJson
    {
        public String gameName;
        public String prologue;
        public int startPositionRiga;
        public int startPositionColonna;
    }
}

