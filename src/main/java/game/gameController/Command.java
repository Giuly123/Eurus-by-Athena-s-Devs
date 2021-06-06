package game.gameController;

import game.gameUtilities.Utilities;
import game.gameUtilities.pair.Pair;

public enum Command
{
    osserva(new String[]{"osserva","observe"}),
    guarda(new String[]{"guarda","look"}),
    leggi(new String[]{"leggi","read"}),
    usa(new String[]{"usa","use"}),
//    apri(new String[]{"apri","open"}),
    interagisci(new String[]{"interagisci", "interact"}),
    prendi(new String[]{"prendi","take","raccogli"}),
    rispondi(new String[]{"rispondi", "reply"}),
    nord(new String[]{"nord","n","su"}),
    sud(new String[]{"sud","s","giu"}),
    est(new String[]{"est","e","destra","dx"}),
    ovest(new String[]{"ovest","o","sinistra","sx"}),
    salva(new String[]{"salva","save"}),
    help(new String[]{"-h","--help","help"});


    public String argComando;
    private String[] aliasList;
    Command(String[] aliasList)
    {
        this.aliasList = aliasList;
    }

    static private String[] prepositions = {"con", "la", "il", "la", "lo", "with", "the"};

    private static Pair<String, String> getCommandAndArg(String str)
    {
        Pair<String, String> result = new Pair<>();

        String strings[] = str.split("\\s+", 2);

        result.setKey(strings[0]);

        if (strings.length > 1)
        {
            strings[1] = Utilities.escapePreposizioni(strings[1], prepositions);
            result.setValue(strings[1]);
        }

        return result;
    }


    public static Command parseCommand(String str)
    {
        Command found = null;

        Command[] commandList = Command.values();

        Pair<String, String> commandAndArg = getCommandAndArg(str);

        for (int i = 0; i< commandList.length && found == null; ++i)
        {
            Command cmd = commandList[i];
            for (int j = 0; j<cmd.aliasList.length && found == null; ++j)
            {
                if(commandAndArg.getKey().equalsIgnoreCase(cmd.aliasList[j]))
                {
                    found = cmd;
                }
            }
        }

        if (found != null)
        {
            found.argComando = commandAndArg.getValue();
        }

        return found;
    }


}