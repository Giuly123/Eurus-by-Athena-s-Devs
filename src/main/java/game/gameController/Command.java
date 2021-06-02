package game.gameController;

import game.gameUtilities.pair.Pair;

public enum Command
{
    osserva(new String[]{"osserva"}),
    guarda(new String[]{"guarda"}),
    leggi(new String[]{"leggi", "read"}),
    usa(new String[]{"usa","use"}),
    apri(new String[]{"apri"}),
    interagisci(new String[]{"interagisci"}),
    prendi(new String[]{"prendi", "take", "raccogli"}),
    rispondi(new String[]{"rispondi"}),
    nord(new String[]{"nord","n"}),
    sud(new String[]{"sud","s"}),
    est(new String[]{"est","e"}),
    ovest(new String[]{"ovest","o"}),
    salva(new String[]{"salva","save"}),
    esci(new String[]{"exit","esci"}),
    help(new String[]{"-h","--help","help"});


    public String argComando;
    private String[] aliasList;
    Command(String[] aliasList)
    {
        this.aliasList = aliasList;
    }


    private static Pair<String, String> getCommandAndArg(String str)
    {
        Pair<String, String> result = new Pair<>();

        String strings[] = str.split("\\s+");

        result.setKey(strings[0]);

        if (strings.length > 1)
        {
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