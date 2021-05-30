package game;

import game.gameUtilities.Utilities;
import game.jsonParser.JsonParser;

public class StartConfig
{
    public RootStartConfigJson rootStartConfigJson;

    public StartConfig() throws Exception
    {
        loadStartConfig();
    }

    private void loadStartConfig() throws Exception
    {
        if (Utilities.fileExist(Utilities.START_CONFIG_JSON_PATH))
        {
            rootStartConfigJson = JsonParser.GetClassFromJson(Utilities.START_CONFIG_JSON_PATH, RootStartConfigJson.class);
        }
        else
        {
            throw new Exception("File StartConfig non presente sul disco");
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

