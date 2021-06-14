package game.gameUtilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * CLasse di Utility statica che esegue operazioni eterogenee e frequentemente utilizzate.
 */
public class Utilities
{
    public static final String GAME_NAME = "Athena's Adventure Game";

    public static final String ICON_HOME_PATH = "assets/icons/home_icon.png";
    public static final String ICON_SAVE_PATH = "assets/icons/save_icon.png";
    public static final String BACKGROUND_PATH = "assets/icons/sfondo.jpg";
    public static final String MUSIC_PATH = "assets/sounds/music.wav";

    // paths files
    public static final String PATH_LOGO = "assets/icons/logo.png";
    public static final String ERROR_LOG_PATH = "assets/errorLog.txt";
    public static final String START_CONFIG_JSON_PATH = "assets/game/startConfig.json";
    public static final String ITEMS_JSON_PATH = "assets/game/items.json";
    public static final String INTERACTABLES_JSON_PATH = "assets/game/interactables.json";
    public static final String DIALOGUES_JSON_PATH = "assets/game/dialogues.json";
    public static final String GUESSING_GAMES_JSON_PATH = "assets/game/guessingGames.json";
    public static final String MAP_JSON_PATH = "assets/game/map.json";
    public static final String SAVE_JSON_PATH = "assets/save.json";
    public static final String TEXT_AREA_PATH = "assets/textArea.txt";

    public static final String texturesPath = "assets/textures/";
    
    public static final int timeDelayTyperWrite = 5;

    static final int NUM = 10;
    static final int DIVISION_PER_SECOND = 1000;
    static final int DIVISION_PER_MINUTE = 60000;
    static final int SECOND = 60;

    public static final String EASTER_EGG_TEMPO_STRING = "Chronos dice: <<Non ti serve, sei scarso!>>";
    public static final long EASTER_EGG_TIME = 1200000;

    /**
     * Verifica se un file esiste.
     * @param path del file
     * @return true se esiste e non è una directory
     */
    public static Boolean fileExist(String path)
    {
        File f = new File(path);
        return  f.exists() && !f.isDirectory();
    }

    /**
     * Si occupa di togliere gli spazi in eccesso da una stringa.
     * @param str stringa su cui lavorare
     * @return la stringa senza spazi in eccesso
     */
    public static String cleanString(String str)
    {
        return str.trim();
    }

    /**
     * Divide una stringa ogni volta che c'è un separatore.
     * @param str stringa su cui lavorare
     * @param separator separatore
     * @return l'array delle stringhe separate
     */
    public static String[] splitString(String str, char separator)
    {
        String s = String.valueOf(separator);
        return str.split(s, -1);
    }

    /**
     * Rimuove le preposizioni dalla stringa.
     * @param startString stringa su cui lavorare
     * @param listString array di preposizioni da rimuovere
     * @return stringa lavorata
     */
    public static String removePrepositions(String startString, String[] listString)
    {
        for(String string : listString)
        {
            int indexOf = startString.indexOf(string);
            if (indexOf > -1)
            {
                if (indexOf == 0)
                {
                    startString = startString.replaceFirst(string + " ", "");
                }

                startString = startString.replace(" " + string + " ", " ");
            }
        }

        return cleanString(startString);
    }

    /**
     * Legge un file.
     * @param filePath file path da leggere
     * @return il contenuto del file letto sotto forma di stringa
     */
    public static String readFile(String filePath)
    {
        String result = "";

        StringBuilder stringBuilder = new StringBuilder();
        try (Stream stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> stringBuilder.append(s).append("\n"));
            result = stringBuilder.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = "";
        }

        return result;
    }


    /**
     * Scrive un file.
     * @param filePath file path da scrivere
     * @param content il contenuto del file
     * @param append se deve concatenare il file
     * @return true se l'operazione di scrittura è andata a buon fine
     */
    public static Boolean writeFile(String filePath, String content, boolean append)
    {
        Boolean isSuccessfully = true;

        Writer out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath, append), "UTF-8"));

            out.write(content);
        }
        catch (Exception e)
        {
            isSuccessfully = false;
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return isSuccessfully;
    }


    /**
     * Effettua il parse da millisecondi a stringa nel formato ("mm:ss").
     * @param millisecondi tempo in millisecondi
     * @return stringa tempo nel formato ("mm:ss")
     */
    public static String parseTime(final long millisecondi) {
        int seconds = (int) ((millisecondi / DIVISION_PER_SECOND) % SECOND);
        int minutes = (int) (millisecondi / DIVISION_PER_MINUTE);

        String time = "";
        if (minutes < NUM) {
            time += "0";
        }
        time += minutes + ":";
        if (seconds < NUM) {
            time += "0";
        }
        time += seconds;
        return time;
    }

//    public static Boolean writeFile(String filePath, String content, boolean append)
//    {
//        Boolean isSuccessfully = true;
//
//        try {
//            FileWriter fileWriter = new FileWriter(filePath, append);
//
//            fileWriter.write(content);
//            fileWriter.close();
//
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//            isSuccessfully = false;
//        }
//
//        return isSuccessfully;
//    }

    /**
     *
     * @return la data e l'ora corrente sotto forma di stringa
     */
    public static String getCurrentData()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
