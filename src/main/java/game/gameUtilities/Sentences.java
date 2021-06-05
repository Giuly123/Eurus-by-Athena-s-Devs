package game.gameUtilities;

public class Sentences
{
    public static final String ERROR_MESSAGE_GENERIC = "";
    public static final String ERROR_MESSAGE_PARSING = "";

    public static final String HELP_MESSAGE = "I comandi possibili sono : etc ...";

    public static final String START_STRING_PHRASE = ">> ";

    public static final String WRONG_COMMAND_ENTERED = "Questo comando non esiste";

    public static final String LOOK_ITEM_DESCRIPTION = "Descrizione: ";
    public static final String LOOK_ITEM_WRONG = "Non possiedi questo oggetto e non si trova nelle vicinanze";
    public static final String LOOK_ITEM_INCOMPLETE = "Devi aggiungere l'oggetto che vuoi gaurdare";
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
            "selezionato nel file json non corrissponde a nessuno dei tipi ammissibili";
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

    public static final String SAVE_GAME = "Salvataggio effettuato";

    public static final String END_GAME_STRING = "Complimenti, hai finito il gioco. Grazie per aver giocato.";

}
