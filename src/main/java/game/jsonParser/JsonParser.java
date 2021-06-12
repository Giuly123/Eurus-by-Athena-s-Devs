package game.jsonParser;

import com.google.gson.Gson;
import game.gameUtilities.Utilities;


public class JsonParser
{
    public static <T> T getClassFromJson(String pathFileJson, Class<T> typeClass)
    {
        Gson gson = new Gson();
        return gson.fromJson(Utilities.readFile(pathFileJson), typeClass);
    }


    public static <T> String serializeClassToJson(T object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
