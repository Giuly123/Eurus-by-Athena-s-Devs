package game.gameUtilities;

/**
 * Vi sono memorizzate tutte le stringhe costanti utilizzate nell'app.
 */
public class Sentences
{
    public static final String ERROR_MESSAGE_GENERIC = "";
    public static final String ERROR_MESSAGE_PARSING = "";

    public static final String HELP_COMMAND = " - Digita 'help' per mostrare la lista dei comandi possibili;\n";
    public static final String NORTH_COMMAND = " - Digita 'nord'/'n'/'su' per far muovere il protagonista\n" +
        "   verso nord;\n";
    public static final String SOUTH_COMMAND = " - Digita 'sud'/'s'/'giu' per far muovere il protagonista\n   verso " +
        "sud;\n";
    public static final String WEST_COMMAND = " - Digita 'ovest'/'o'/'sinistra'/'sx' per far muovere il\n   " +
        "protagonista verso ovest;\n";
    public static final String EAST_COMMAND = " - Digita 'est'/'e'/'destra'/'dx' per far muovere il\n   protagonista " +
        "verso est\n";
    public static final String OPEN_COMMAND = " - Digita 'interagisci' [con][con il] + nome oggetto\n   per aprire " +
        "eventuali oggetti di tipo chest / chest dove e'\n   richiesto un pin o altri oggetti (non utilizzabile con " +
        "le\n   porte)\n";
    public static final String READ_COMMAND = " - Digita 'leggi'/'read' + nome oggetto + numero documento\n   per " +
        "leggere oggetti di tipo documento\n";
    public static final String LOOK_COMMAND = " - Digita 'guarda' + nome oggetto / 'osserva' + nome oggetto\n   per " +
        "osservare l'oggetto. Mostra la descrizione\n   dell'oggetto\n";
    public static final String SEE_TILE_COMMAND = " - Digita 'osserva' per avere piu' informazioni nella stanza\n   in" +
        " cui ti trovi\n";
    public static final String USE_COMMAND = " - Digita 'usa'/'use' + nome oggetto per usare un oggetto\n   presente " +
        "nell'inventario\n";
    public static final String TO_ANSWER_COMMAND = " - Digita 'rispondi' per rispondere agli indovinelli presenti\n";
    public static final String TAKE_COMMAND = " - Digita 'prendi'/'take'/'raccogli' + nome oggetto per\n   " +
        "raccogliere un oggetto\n";
    public static final String SAVE_COMMAND = " - Digita 'salva'/'save' per salvare lo stato della partita\n";

    public static final String HELP_MESSAGE =
        "I comandi possibili sono:\n\n" + HELP_COMMAND + NORTH_COMMAND + SOUTH_COMMAND + WEST_COMMAND + EAST_COMMAND +
            OPEN_COMMAND + READ_COMMAND + LOOK_COMMAND + SEE_TILE_COMMAND + USE_COMMAND + TO_ANSWER_COMMAND +
            TAKE_COMMAND + SAVE_COMMAND + "\n";

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
    public static final String GIVE_ANSWER_SOLVED = "Complimenti, hai risposto correttamente all'indovinello!";

    public static final String MOVE_MOVED = "Ti sei spostato a : ";
    public static final String MOVE_NEED_ITEMS = "Per passare da questa parte devi prima usare un oggetto!";
    public static final String MOVE_NEED_ANSWER_1 = "Per passare da questa parte devi prima rispondere a una " +
            "domanda!\n(digitare rispondi con la risposta)";
    public static final String MOVE_NEED_ANSWER_2 = "Domanda: ";
    public static final String MOVE_ERROR = "Errore spostamento";



    public static final String INTERACTABLE_ERROR = "Devi inserire qualcosa";
    public static final String INTERACTABLE_CHEST_ERROR = "Non puoi aprire questo oggetto poichè non è una chest!";
    public static final String INTERACTABLE_WRONG= "Non c'e' nulla con cui interagire con questo nome!";
    public static final String INTERACTABLE_USED = "Hai interagito con: ";
    public static final String INTERACTABLE_ALREADY_USED = "Hai gia' interagito con questo oggetto!";
    public static final String INTERACTABLE_NEED_ITEM = "Prima devi fare qualcosa...";
    public static final String INTERACTABLE_NEED_ANSWER = "Per usare questo oggetto devi prima inserire una " +
        "password!\n(digitare rispondi con la risposta)";


    public static final String GENERIC_ERROR = "Errore durante il loading del game', " +
            "controllare i file";

    public static final String SAVE_GAME = "Salvataggio effettuato";

    public static final String END_GAME_STRING = "Complimenti, hai finito il gioco. Grazie per aver giocato.\n\n" +
    "MMMMMMMMMMMMMMMMMMMMMmsosyhmmdhysyhdmNNMMMMMMMMMMMMMMM\n" +
    "MMMMMMMMMMMMMMMMMMMd-`++/smmdhyso+:-`-+yydNMMMMMMMMMMM\n" +
    "MMMMMMMMMMMMMMMMMMh. hy:/:----:/osoo/-` :o:sNNMMMMMMMM\n" +
    "MMMMMMMMMMMMMNho+++ys:.:+++//-` /mmhyyy+.`o--hNMMMMMMM\n" +
    "MMMMMMMMMMmd/:/+++y/`sh/.:sso-s- -s///:/+-.s .mMMMMMMM\n" +
    "MMMMMMMMMhhssoo/:o/ +N- yh. ://s  y-..`  ./y/ +MMMMMMM\n" +
    "MMMMMMmNmyssd/:::+oosN/ oh-.-+d: `h---:/++``+-.MMMMMMM\n" +
    "MMmhhsm+//..:mNdo.`/o+s+``-/+:` -h-:hmmd--d+ soNymMMMM\n" +
    "Ns-y:md/-:oo:+oo+/o+/ys+oosoosss-y.d/ .hh`hM.+MMm:sMMM\n" +
    "MMN//m+dms+oyo//y+:-/+-/+y+yo..h:y`h+  .-+NN.osNMd`+NM\n" +
    "MMNo+hs:://oo+/+hyyo+yy+--s`:+s+h+/.oso+oyd+o/ -mN+ +M\n" +
    "MMMMhNMNy-////o+.---oysoo+yh+--o--+s/:/+oso/:/  :Nm``h\n" +
    "MMMMMMMN-:os+/--++od:-..:syoso+sy---syoy+.o. o   yM. /\n" +
    "MMMMMMMm++.::.::- `++ys+h-.:://ooso/.o.-:oo: s`  /M- -\n" +
    "MMMMMMMy` ommdo:`    :s-`:yoo+:+so//odo:.:hs+h:  :M` :\n" +
    "MMMMMMh`  dNmy/.`     .//.`+oo//.--oy++s/::-.-+: oh  o\n" +
    "MMMMNo                  `-//-+-:dss-:+o//+yy.: o+y. .N\n" +
    "MMMN/                      -+`o+do+/-.--/o/sy::`h: `hM\n" +
    "MMM/   ``                 `o:oys-/:`:ooo+sys/o/.s. yMM\n" +
    "MMMh+:  .                  .::os  +-  y:.-::o//so`yMMM\n" +
    "MMMMMd.`                   /s::s  `o` :so-`--/h+hdMMMM\n" +
    "MMMMMy.-:::.               os/+s:  :+-/s-y/:s/`yMMMMMM\n" +
    "MMMMMm.`-:.               `o`.:-/: :o::s./:`o  +MMMMMM\n" +
    "MMMMMMo.                 -+      .+:s..s `so`  hMMMMMM\n" +
    "MMMMMN/            .-::/:.    `::::y+ .o  s.  oMMMMMMM\n" +
    "MMMMMMmo////+oss//:-``       //`      /: +s`.hMMMMMMMM\n" +
    "MMMMMMMMMMMMMMMNo           .o      `///s.`:oNMMMMMMMM\n" +
    "MMMMMMMMMMMMMMMMN:          `s     `o`-//:`  :NMMMMMMM\n" +
    "MMMMMMMMMMMMMMMMMd.          +.    :: o. ./+ `hMNMMMMM\n" +
    "MMMMMMMMMMMMMMMMMMd-`            :/./+/dMMMMMMMMMMMMMM\n" +
    "MMMMMMMMMMMMMMMMMMMMNddhhso+///osyyhmNMMMMMMMMMMMMMMMM";
    public static final String END_GAME_HELP_STRING = "Hai finito il gioco non puoi usare questo comando";

}
