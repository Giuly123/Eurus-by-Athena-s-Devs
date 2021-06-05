package game.player;

import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

public class StartConfig
{
    public RootStartConfigJson startConfigJson;

    public StartConfig() throws Exception
    {
        loadStartConfig();
    }

    private void loadStartConfig() throws Exception
    {
        if (Utilities.fileExist(Utilities.START_CONFIG_JSON_PATH))
        {
            try
            {
                startConfigJson = JsonParser.GetClassFromJson(Utilities.START_CONFIG_JSON_PATH, RootStartConfigJson.class);
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

    public class RootStartConfigJson
    {
        public String gameName;
        public String prologue;
        public int startPositionRiga;
        public int startPositionColonna;

    }
}

