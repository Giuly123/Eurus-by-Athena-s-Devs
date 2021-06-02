package game.jsonParser;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonParser
{
    public static <T> T GetClassFromJson(String pathFileJson, Class<T> typeClass) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream stream = Files.lines( Paths.get(pathFileJson), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> stringBuilder.append(s).append("\n"));
            Gson gson = new Gson();
            return gson.fromJson(stringBuilder.toString(), typeClass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> String SerializeClassToJson(T object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
