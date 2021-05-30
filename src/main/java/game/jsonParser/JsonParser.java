package game.jsonParser;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser
{
    public static <T> T GetClassFromJson(String pathFileJson, Class<T> typeClass) throws IOException
    {
        String content = new String (Files.readAllBytes(Paths.get(pathFileJson)));
        Gson gson = new Gson();

        return gson.fromJson(content, typeClass);
    }


    public static <T> String SerializeClassToJson(T object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
