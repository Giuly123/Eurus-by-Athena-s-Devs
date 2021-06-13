package game.jsonParser;

import com.google.gson.Gson;
import game.gameUtilities.Utilities;

/**
 * Classe utilities per la serializzazione e
 * deserializzazione dei file json.
 */
public class JsonParser
{
    /**
     * Deserializza una classe da un file json.
     * @param pathFileJson path del file json
     * @param typeClass tipo della classe in cui deve essere fatto il parse del file
     * @param <T> tipo generico
     * @return l'istanza della classe popolata con le informazioni parsate dal file json
     */
    public static <T> T getClassFromJson(String pathFileJson, Class<T> typeClass)
    {
        Gson gson = new Gson();
        return gson.fromJson(Utilities.readFile(pathFileJson), typeClass);
    }

    /**
     * Serializza una classe secondo lo standard json.
     * @param object oggetto da serializzare
     * @param <T> tipo generico
     * @return stringa contenente la classe serializzata
     */
    public static <T> String serializeClassToJson(T object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
