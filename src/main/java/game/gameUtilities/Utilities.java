package game.gameUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Classe per variabili e metodi statici
public class Utilities
{
    public static final String GAME_NAME = "GiocoBello Alpha 0.01";

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

    public static final String texturesPath = "assets/textures/";

    public static final int timeDelayTyperWrite = 5;
    public static final char separatorChar = ' ';

    public static Boolean fileExist(String path)
    {
        File f = new File(path);
        return  f.exists() && !f.isDirectory();
    }


    public static String cleanString(String str)
    {
        return str.trim();
    }


    public static String[] splitString(String str, char separator)
    {
        String s = String.valueOf(separator);
        return str.split(s, -1);
    }


    public static String escapePreposizioni(String startString, String[] listString)
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



    public static Boolean writeFile(String filePath, String content, boolean append)
    {
        Boolean isSuccessfully = true;

        try {
            FileWriter fileWriter = new FileWriter(filePath, append);

            fileWriter.write(content);
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            isSuccessfully = false;
        }

        return isSuccessfully;
    }


    public static String getCurrentData()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
