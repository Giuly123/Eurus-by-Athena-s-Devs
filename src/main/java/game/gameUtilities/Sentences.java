package game.gameUtilities;

public class Sentences
{
    public static final String ERROR_MESSAGE_GENERIC = "";
    public static final String ERROR_MESSAGE_PARSING = "";

    public static final String HELP_COMMAND = " - Digita 'help' per mostrare la lista dei comandi possibili\n";
    public static final String NORTH_COMMAND = " - Digita 'nord'/'n'/'su' per far muovere il protagonista verso nord\n";
    public static final String SOUTH_COMMAND = " - Digita 'sud'/'s'/'giu' per far muovere il protagonista verso sud\n";
    public static final String WEST_COMMAND = " - Digita 'ovest'/'o'/'sinistra'/'sx per far muovere il protagonista " +
        "verso ovest\n";
    public static final String EAST_COMMAND = " - Digita 'est'/'e'/'destra'/'dx per far muovere il protagonista verso " +
        "est\n";
    public static final String OPEN_COMMAND = " - Digita 'apri + nome oggetto' / 'interagisci [con][con il] + nome " +
        "oggetto' per\n   aprire eventuali oggetti di tipo chest / chest dove è richiesto un pin o altri oggetti\n";
    public static final String READ_COMMAND = " - Digita 'leggi + nome oggetto' per leggere oggetti di tipo documento\n";
    public static final String LOOK_COMMAND = " - Digita 'guarda + nome oggetto' / 'osserva + nome oggetto' per " +
        "osservare\n   l'oggetto. Mostra la descrizione dell'oggetto\n";
    public static final String SEE_TILE_COMMAND = " - Digita 'osserva' per avere più informazioni nella stanza in cui " +
        "ti trovi\n";
    public static final String USE_COMMAND = " - Digita 'usa + nome oggetto' per usare un oggetto presente " +
        "nell'inventario\n";
    public static final String TO_ANSWER_COMMAND = " - Digita 'rispondi' per rispondere agli indovinelli presenti\n";
    public static final String TAKE_COMMAND = " - Digita 'prendi + nome oggetto' per raccogliere un oggetto\n";

    public static final String HELP_MESSAGE =
        "I comandi possibili sono:\n\n" + HELP_COMMAND + NORTH_COMMAND + SOUTH_COMMAND + WEST_COMMAND + EAST_COMMAND +
            OPEN_COMMAND + READ_COMMAND + LOOK_COMMAND + SEE_TILE_COMMAND + USE_COMMAND + TO_ANSWER_COMMAND +TAKE_COMMAND + "\n";

    public static final String WRONG_COMMAND_ENTERED = "Questo comando non esiste";

    public static final String START_STRING_PHRASE = ">> ";

    public static final String LOOK_ITEM_DESCRIPTION = "Descrizione: ";
    public static final String LOOK_ITEM_WRONG = "Non possiedi questo oggetto e non si trova nelle vicinanze";
    public static final String LOOK_ITEM_INCOMPLETE = "Devi aggiungere l'oggetto che vuoi guardare";
    public static final String LOOK_ITEM_ISNT_DOCUMENT = "Questo oggetto non puo' essere letto";


    public static final String TAKE_ITEM_TAKEN = "Hai preso: ";
    public static final String TAKE_ITEM_ALREADY_TAKEN = "Hai gia' preso questo oggetto";
    public static final String TAKE_ITEM_WRONG = "Questo oggetto non e' nella stanza";

    public static final String USE_ITEM_USED = "Hai usato: ";
    public static final String USE_ITEM_ALREADY_USED = "Hai gia' usato questo oggetto";
    public static final String USE_ITEM_WRONG = "Questo oggetto non va usato qui";
    public static final String USE_ITEM_UNUSABLE = "Questo oggetto non puo' essere 'usato', prova a leggerlo";
    public static final String USE_ITEM_NOT_OWNED = "Non possiedi questo oggetto nell'inventario";
    public static final String USE_ITEM_UNKNOWN = "MSG DI ERRORE: C'e' un problema con questo oggetto, il typeItem " +
            "selezionato nel file json non corrisponde a nessuno dei tipi ammissibili";
    public static final String READ_DOCUMENT = "C'e' scritto: ";


    public static final String GIVE_ANSWER_ERROR = "Devi dare una risposta stronzo";
    public static final String GIVE_ANSWER_NO_QUESTIONS = "Non ci sono indovinelli da risolvere";
    public static final String GIVE_ANSWER_NOT_SOLVED = "Risposta sbagliata";
    public static final String GIVE_ANSWER_ALREADY_SOLVED = "Hai gia' risposto a questa domanda";
    public static final String GIVE_ANSWER_SOLVED = "Complimenti hai risposto correttamente all'indovinello";


    public static final String MOVE_MOVED = "Ti sei spostato a : ";
    public static final String MOVE_NEED_ITEMS = "Per passare da questa parte devi prima usare un oggetto";
    public static final String MOVE_NEED_ANSWER_1 = "Per passare da questa parte devi prima rispondere a una " +
            "domanda\n(digitare rispondi con la risposta)";
    public static final String MOVE_NEED_ANSWER_2 = "Domanda: ";
    public static final String MOVE_ERROR = "Errore spostamento";



    public static final String INTERACTABLE_ERROR = "Devi inserire qualcosa";
    public static final String INTERACTABLE_CHEST_ERROR = "Non puoi aprire questo oggetto";
    public static final String INTERACTABLE_WRONG= "Non c'e' nulla con cui interagire con questo nome";
    public static final String INTERACTABLE_USED = "Hai interagito con: ";
    public static final String INTERACTABLE_ALREADY_USED = "Hai gia' interagito con questo oggetto";
    public static final String INTERACTABLE_NEED_ITEM = "Per usare questo oggetto hai bisogno di usare prima qualche ";
    public static final String INTERACTABLE_NEED_ANSWER = "Per usare questo oggetto devi prima inserire una password"+
            "altro oggetto\n(digitare rispondi con la risposta)";
    public static final String INTERACTABLE_AFTER_USED = "Evento: ";

    public static final String GENERIC_ERROR = "Errore durante il loading del game', " +
            "controllare i file";

    public static final String SAVE_GAME = "Salvataggio effettuato";

    public static final String END_GAME_STRING = "Complimenti, hai finito il gioco. Grazie per aver giocato.";
    public static final String END_GAME_HELP_STRING = "Hai finito il gioco non puoi usare questo comando";

}
