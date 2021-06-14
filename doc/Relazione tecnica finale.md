# Relazione tecnica finale

 ### Indice

<br/>

1. [Introduzione](#introduzione)
  * [Trama](#trama)
  * [Mappa](#mappa)
2. [Requisiti specifici](#requisiti-specifici)
  * [Requisiti funzionali](#requisiti-funzionali)
  * [Requisiti non funzionali](#requisiti-non-funzionali)
3. [System Design](#system-Design)
  * [Pattern e stile architetturale utilizzati](#pattern-e-stile-architetturale-utilizzati)
4. [Dettagli implementativi e tecnologie utilizzate](#dettagli-implementativi-e-tecnologie-utilizzate)
5. [Specifica algebrica di una struttura dati utilizzata](#specifica-algebrica-di-una-struttura-dati-utilizzata)
6. [Soluzione completa del gioco](#soluzione-completa-del-gioco)
7. [Dettegli sulla compilazione](#dettagli-sulla-compilazione)
8. [Organizzazione del lavoro e strumenti utilizzati](#organizzazione-del-lavoro-e-strumenti-utilizzati)
  * [Piattaforme di comunicazione](#piattaforme-di-comunicazione)
9. [Conclusione](#Conclusione)

<br/><br/>


## **Introduzione**

Questo documento è la relazione tecnica finale per il progetto che implementa il gioco **EURUS**.

L'obiettivo di questo progetto è quello di dimostrare le competenze acquisiste durante il corso di Metodi Avanzati di Programmazione A.A. 2020/2021, tenuto dal Professor **[Pierpaolo Basile](http://www.di.uniba.it/~swap/index.php?n=Membri.Basile)**.

Lo scopo di questo progetto è quello di creare un'avventura testuale, scritta in Java, che potrà essere giocata da tutti coloro i quali hanno una conoscenza almeno dilettantistica del suo funzionamento.

L'interfaccia utente è stata implementata sia in versione grafica (Graphical User Interface - GUI) sia a riga di comando (Command Line Interface - CLI).

L'applicativo software, oggetto del documento, è stato sviluppato dal gruppo **[Athena's devs]()**, composto da:

* **[Riccardo Ranieri](https://github.com/RickNewere)**
* **[Giuliano Picilli](https://github.com/Giuly123)**
* **[Fabio Spaccavento](https://github.com/fabiospaccavento)**

All'interno, è stata anche generata la Javadoc.

## **Trama**

Eurus è un avventura testuale ispirata alla serie BBC ```Sherlock```. Riuscirai a risolvere il problema finale? Scoprilo accompagnando i protagonisti nel Gioco! (due finali disponibili).

## **Mappa**

<center><img src = "../assets/relazione/mappa.PNG"></center>

[Torna all'indice](#indice)
<br/>

## **Requisiti specifici**

Di seguito l'elenco dei requisiti specifici (funzionali e non funzionali):

## **Requisiti funzionali**

Avviata l'applicazione ci troveremo nella home menù del gioco.

<center><img src = "../assets/relazione/inizioGioco.PNG"></center>

Abbiamo tre pulsanti:

* ```Inizia```: comincia una nuova partita.
* ```Continua```: riprende l'ultima partita salvata. Altrimenti comincia una nuova partita.
* ```Esci```: chiude l'applicazione.

Iniziando o continuando una partita ci ritroveremo nella vera e propria schermata di gioco.

Il gioco sarà predisposto a ricevere comandi del tipo:

* ```help```: per mostrare la lista dei comandi disponibili.
* ```nord```/```su```/```n```: per far muovere il protagonista verso nord;
* ```sud```/```giu```/```s```: per far muovere il protagonista verso sud;
* ```est```/```sinistra```/```e```/```sx```: per far muovere il protagonista verso est;
* ```ovest```/```destra```/```o```/```dx```: per far muovere il protagonista verso ovest;
* ```interagisci[con][con il] + nome oggetto```: per aprire eventuali oggetti di tipo chest / chest dove è richiesto un pin / interagire con cadaveri o altri oggetti;
* ```leggi + nome oggetto```: per leggere oggetti di tipo documento;
* ```guarda + nome oggetto``` / ```osserva + nome oggetto```: per osservare l'oggetto. Mostra la descrizione dell'oggetto;
* ```osserva``` : per osservare la stanza in cui ti trovi. Questo comando ti darà più informazioni rispetto alla normale descrizione che apparirà una volta entrati in una determinata stanza;
* ```usa + nome oggetto```: per usare un oggetto presente nell'inventario;
* ```rispondi```: per rispondere ad indovinalli presenti nel gioco;
* ```prendi + nome oggetto```: per raccogliere un oggetto.

Di tutti i comandi valgono anche i corrispettivi inglesi!

<center><img src = "../assets/relazione/schermataIniziale.PNG"></center>


## **Requisiti non funzionali**

**Robustezza**

Il software prevede che l'utente conosca le regole generali di una avventura testuale. Il livello di protezione dagli errori dell'utente consiste nella rigorosa analisi dell'input con opportune notifiche di comandi o mosse errate.
Inoltre, l'applicazione gestisce tutte le eventuali eccezioni.

**Estendibilità**

Il software è stato realizzato utilizzando le best practices del linguaggio **O.O. Java** ed è strutturato secondo il pattern architetturale **MVC**.
Il software è progettato in modo da poter consentire, con semplicitá, l'implementazione di altri comandi in futuro.

**Riusabilità**

Il software può essere reimpiegato per lo sviluppo di applicazioni. Infatti, basterà avere creatività e modificare solo la storia.

**Portabilità**

La JDK utilizzata per sviluppare l'avventura è stata la 8.0.

[Torna all'indice](#indice)
<br/>

## **System Design**

L'applicazione è interamente sviluppata in **[Java](https://www.java.com/it/)**. Questo è un linguaggio orientato agli oggetti, che permette l'esecuzione dei suoi applicativi su tutti i sistemi per cui è stata sviluppata una java virtual machine. L'intero progetto è contenuto all'interno di una repository privato su **[GitHub](https://github.com/)**. Inoltre, abbiamo utilizzato **[Maven](https://maven.apache.org/)** come sistema per l'automazione dello sviluppo e **[SpotBugs](https://spotbugs.github.io/)**, strumento utile per scovare bug nascosti nel codice. Per quest'ultimo, si è scelto di lasciar alcuni che ritenevamo non essere veri e propri bug.

## **Pattern e stile architetturale utilizzati**

Il programma è stato progettato secondo il pattern architetturale **[MVC](https://it.wikipedia.org/wiki/Model-view-controller)** (Model - View - Controller). Abbiamo deciso di utilizzare questo pattern per disaccopiare la logica e i dati del gioco dalla logica d'interfacciamento con l'utente. 

Pertanto:

* **GameModel**

  Gestisce la logica e i dati dell'applicativo.

* **GameView**

  Gestisce l'interfacciamento con l'utente.

* **GameController**

  Si interfaccia con le due classi.

Inoltre, abbiamo affiancato al pattern MVC il Pattern
**[Observer](https://it.wikipedia.org/wiki/Observer_pattern)**, per mantenere un alto livello di consistenza fra classi correlate, senza produrre situazioni di forte dipendenza e di accoppiamento elevato.

Il pattern Observer permette di definire una dipendenza uno a molti fra oggetti, in modo tale che se un oggetto cambia il suo stato interno, ciascuno degli oggetti dipendenti da esso viene notificato e aggiornato automaticamente.

Il pattern Observer trova applicazione nei casi in cui diversi oggetti (Observer) devono conoscere lo stato di un oggetto (Subject).
In poche parole  abbiamo un oggetto che viene “osservato” (il subject) e tanti oggetti che “osservano” i cambiamenti di quest’ultimo (gli observers).

Abbiamo creato quindi:

* Un'interfaccia generica, chiamata ```Observer```;
* Una classe soggetto da osservare, chiamata ```Subject```;

L'applicazione di questo pattern è particolarmente utilizzata nelle classi:

* ```Player```;

* ```GameEventsHandler```.

Ogni qual volta lo stato interno della classe ```Player``` (la quale implementa la logica utilizzata dal ```GameModel```) subisce un cambiamento, il subject relativo all'azione notifica il cambiamento agli observer ad esso registrati. Questi ultimi,(contenuti nella classe ```GameEventsHandler```) ricevuta la notifica dal subject, triggerano l'aggiornamento della GUI tramite la classe ```GameView```. In questo modo si garantisce la corretta forma del pattern MVC.  

In aggiunta, abbiamo anche utilizzato il pattern **[Singleton](https://it.wikipedia.org/wiki/Singleton_(informatica))**, nei diversi Handler/Manager che compongono l'applicativo.

[Torna all'indice](#indice)
<br/>

## **Dettagli implementativi e tecnologie utilizzate**

## **JSON**

Abbiamo utilizzato la libreria esterna **[Gson](https://github.com/google/gson)** che ci permette di serializzare e deserializzare oggetti direttamente in **[Json](https://it.wikipedia.org/wiki/JavaScript_Object_Notation)**.

L'applicativo software è stato sviluppato in modo tale che, cambiando opportunamente i file di gioco (.json), ognuno può scrivere la propria avventura testuale. Difatti il nostro applicativo non è un gioco bensì è un parser di avventure testuali che seguono uno standard da noi definito.

Per popolare i nostri file .json, abbiamo realizzato un editor in java (per uso personale) in modo tale da rendere questa operazione human readeble.


Di seguito alcune foto significative ed esplicative dell'editor realizzato per uso personale:

Pagina iniziale dell'editor dove puoi impostare il titolo del gioco, il prologo e il numero di stanze (che può essere variabile):

<center><img src = "../assets/relazione/editorPaginaIniziale.PNG"></center>

Pagina in cui, in ogni stanza, se la stanza in questione è il punto di spawn o di fine gioco, puoi inserire le direzioni permesse, c'è sia una descrizione breve della stanza, sia una descrizione lunga e puoi inserire gli UUID degli interactable con cui interagire e degli item da raccogliere, etc...:

<center><img src = "../assets/relazione/tileEditor.PNG"></center>

Pagina relativa agl'item dove poter inserire il tipo di item, una descrizione, i suoi alias, se serve per interagire con un interactable, se è consumabile, etc...:

<center><img src = "../assets/relazione/itemEditor.PNG"></center>

L'editor, infine, esporta direttamente tutti i file json necessari per il funzionamento del gioco tramite il pulsante, che si può vedere nella seconda foto, "Save File".

## **Database**

Inoltre, abbiamo sfruttato il **[database H2](https://www.h2database.com/html/main.html)** per il salvataggio delle informazioni relative al tempo di gioco e quelle relative al livello del volume della musica inserita all'interno del gioco.

[Torna all'indice](#indice)
<br/>

## **Specifica algebrica di una struttura dati utilizzata**

<center><img src = "../assets/relazione/specifica1.PNG"></center>

<center><img src = "../assets/relazione/specifica2.PNG"></center>

<center><img src = "../assets/relazione/specifica3.PNG"></center>

[Torna all'indice](#indice)
<br/>

## **Diagramma delle classi**



## **Soluzione completa del gioco**

Dopo aver premuto il bottone ```Inizia```, inserire i comandi nell'ordine citato:

* ```interagisci con comodino```;
* ```usa pass```;
* ```nord```;
* ```interagisci con cadavere```;
* ```nord```;
* ```ovest```;
* ```usa torcia```;
* ```interagisci con armadio```;
* ```est```;
* ```nord```;
* ```usa grimaldello```;
* ```est```;
* ```usa torcia```;
* ```ovest```;
* ```sud```;
* ```sud```;
* ```usa pass```;
* ```ovest```;
* ```interagisci con cassaforte```;
* ```rispondi 3826```;
* ```interagisci con cassaforte```;
* ```est```;
* ```nord```;
* ```nord```;
* ```ovest```;
* ```rispondi vento```;
* ```ovest```;
* ```interagisci con borsello```;
* ```est```;
* ```est```;
* ```usa cerbottana```;
* ```interagisci con custodia```;
* ```usa pezzo```;
* ```est```;
* ```rispondi pippo```;
* ```sud```;
* ```ovest```;
* ```usa torica```;
* ```apri baule```;
* ```est```;
* ```sud```;
* ```apri valigetta```;
* ```ovest```;
* ```sud```;
* ```rispondi 9988```;
* ```sud```;
* ```prendi veleno```;
* ```usa veleno```. --> Finale 1
* ```usa pistola```. --> Finale 2

Inserendoli in questo ordine, si arriva alla fine del gioco. Per poter avanzare, leggere le note è fondamentale dato che queste contengono suggerimenti per le soluzioni dei vari enigmi. Inoltre tutti gli item sono dotati di alias quindi possono esser chiamati con altri nomi.

Questa fornita è la soluzione più completa, si può arrivare comunque alla fine del gioco eseguendo meno comandi perdendo, però, i lgusto del gioco.

**Occhio a scovare l'Easter Egg!**

[Torna all'indice](#indice)
<br/>

## **Dettagli sulla compilazione**

Qualora l'IDE lo consigli, premere il pulstante **"Trust Project"**. Infatti, scaricando solo il progetto in **"Safe Mode"** questo non importerà le librerie utili a **Json**.

Inoltre, se si vuole runnare direttamente il file .jar, mettere nella stessa cartella del .jar anche la cartella 'assets'.

La risoluzione consigliata è la seguente: 

<center><img src = "../assets/relazione/risoluzioneConsigliata.PNG"></center>


[Torna all'indice](#indice)
<br/>

## **Organizzazione del lavoro e strumenti utilizzati**

Il lavoro è stato suddiviso in diverse riunioni. Si è cercato di assegnare lo stesso numero di compiti da svolgere ad ogni singolo partecipante in modo da coinvolgere tutti ed essere quanto più equi possibile. Inoltre tutti i partecipanti hanno utilizzato l'ambiente di sviluppo **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**.

## **Piattaforme di comunicazione**

Per la comunicazione, il nostro gruppo, ha adottato due piattaforme:

* **[Discord](https://discord.com/brand-new)**
* **[Whatsapp](https://www.whatsapp.com/?lang=it)**

La prima è stata scelta poichè una piattaforma a tutti i membri del gruppo familiare, la quale permetteva di fare videoconferenze e di condividere lo schermo. Ciò è stato molto utile in quanto la piattaforma permette di condividere più schermi contemporaneamente.

<center><img src = "../assets/relazione/discord.PNG"></center>

La seconda è stata scelta poichè, essendo anche questa familiare, era il mezzo di comunicazione più veloce in ogni momento. Tramite essa ci si confrontava durante le lezioni, si decidevano i giorni e gli orari per i **meeting** e ci si teneva in contatto.

<center><img src = "../assets/relazione/whatsapp.PNG"></center>

[Torna all'indice](#indice)
<br/>

## **Conclusione**

Riteniamo che questo progetto sia stato un importante banco di prova. Nonostante le difficoltà siamo riusciti comunque a centrare gli obiettivi stabiliti e a trarre il meglio da questa esperienza formativa.

<center>

Grazie per l'attenzione.
Lo staff, **[Athena's devs](https://github.com/Giuly123/Eurus)**.

</center>









