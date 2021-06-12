package game.gameController;

import game.gameUtilities.Utilities;
import game.gameUtilities.pair.Pair;

/**
 * Rappresenta i vari comandi utilizzabili all'interno del gioco.
 */
public enum Command
{
    osserva(new String[]{"osserva","observe"}),
    guarda(new String[]{"guarda","look"}),
    leggi(new String[]{"leggi","read"}),
    usa(new String[]{"usa","use"}),
    interagisci(new String[]{"interagisci", "interact"}),
    prendi(new String[]{"prendi","take","raccogli"}),
    rispondi(new String[]{"rispondi", "reply"}),
    nord(new String[]{"nord","n","su"}),
    sud(new String[]{"sud","s","giu"}),
    est(new String[]{"est","e","destra","dx","east"}),
    ovest(new String[]{"ovest","o","sinistra","sx","west"}),
    salva(new String[]{"salva","save"}),
    help(new String[]{"-h","--help","help"});


    private String argComando;
    private String[] aliasList;
    Command(String[] aliasList)
    {
        this.aliasList = aliasList;
    }

    /**
     * Preposizioni che verranno escapate dal comando.
     */
    static private String[] prepositions = {"con", "la", "il", "la", "lo", "with", "the"};

    /**
     * Lavora la stringa dividendo il comando dall'argomento,
     * se questo è presente, e rimuove l'eventuali preposizioni presenti.
     * @param str comando con eventuale argomento
     * @return pair comando argomento
     */
    private static Pair<String, String> getCommandAndArg(String str)
    {
        Pair<String, String> result = new Pair<>();

        String strings[] = str.split("\\s+", 2);

        result.setKey(strings[0]);

        if (strings.length > 1)
        {
            strings[1] = Utilities.escapePrepositions(strings[1], prepositions);
            result.setValue(strings[1]);
        }

        return result;
    }

    /**
     *
     * @return l'argomento del comando
     */
    public String getArgComando()
    {
        return argComando;
    }

    /**
     * Effettua il parse del comando.
     * @param str comando
     * @return il comando, se esiste.
     */
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