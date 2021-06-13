package game.gameUtilities;

/**
 * Classe che tramite l'uso di thread consente di
 * tenere traccia del tempo trascorso a partire dal suo avvio (start/resume).
 * Essa consente anche di mettere in pausa e riprendere il conteggio.
 */
public class Stopwatch implements Runnable {

    private long lastTimeRead;

    private long timePassed;

    private boolean isStarted;

    private boolean isRunning;

    /**
     * Riprende l'esecuzione del cronometro.
     * @return restituisce true se il cronometro ha ripreso a registrare i valori correttamente, altrimenti false
     */
    public boolean resume() {
        boolean result = false;

        if (isStarted) {
            setLastTimeRead(System.currentTimeMillis());
            setIsRunning(true);
            result = true;
        } else {
            start(0);
        }

        return result;
    }

    /**
     * Mette in pausa il cronometro interrompendo l'acquisizione di nuovi valori.
     * @return restituisce true se il cronometro è stato messo in pausa correttamente
     */
    public boolean pause() {
        boolean result = false;

        if (getStarted()) {
            setIsRunning(false);
            result = true;
        }

        return result;
    }

    /**
     * Interrompe l'esecuzione del cronometro e reimposta i valori
     */
    public void stop() {
        setStarted(false);
        setIsRunning(false);
        setTimePassed(0);
    }

    /**
     * Inizializza e starta il cronometro.
     * @return true se il cronometro è stato avviato
     */
    public boolean start() {
        return start(0);
    }

    /**
     * Inizializza e starta il cronometro.
     * @param startTime tempo in millisecondi da cui si vuole iniziare
     * @return true se il cronometro è stato avviato
     */
    public boolean start(long startTime) {
        boolean result = false;

        if (!getStarted()) {
            setTimePassed(startTime);
            setLastTimeRead(System.currentTimeMillis());
            setStarted(true);
            setIsRunning(true);

            Thread thread = new Thread(this);
            thread.start();

            result = true;
        }

        return result;
    }


    /**
     * Override del metodo run di runnable.
     * Implementa la logica di acquisizione del tempo
     */
    @Override
    public void run() {
        final long awaitTime = 500;
        long temp;
        long newTempoTrascorso;

        while (getStarted()) {
            try {
                if (getIsPlaying()) {
                    temp = System.currentTimeMillis();
                    newTempoTrascorso = (temp - lastTimeRead) + getTempoTrascorsoMillis();
                    setTimePassed(newTempoTrascorso);
                    setLastTimeRead(temp);
                }

                Thread.sleep(awaitTime);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Restituisce il tempo trascorso in millisecondi.
     * @return il tempo trascorso in millisecondi
     */
    public synchronized long getTempoTrascorsoMillis() {
        return timePassed;
    }

    /**
     *
     * @return vero se è stato avviato il cronometro, falso altrimenti
     */
    private boolean getStarted() {
        return isStarted;
    }

    /**
     *
     * @return true se il cronometro e' in esecuzione, altrimenti false
     */
    private boolean getIsPlaying() {
        return isRunning;
    }

    /**
     *
     * @param value che verrà impostato alla variabile isRunning
     */
    private synchronized void setIsRunning(final boolean value) {
        isRunning = value;
    }

    /**
     *
     * @param value che verrà impostato alla variabile isStarted
     */
    private synchronized void setStarted(final boolean value) {
        isStarted = value;
    }

    /**
     *
     * @param value che verrà impostato alla variabile timePassed
     */
    public synchronized void setTimePassed(final long value) {
        timePassed = value;
    }

    /**
     *
     * @param value che verrà impostato alla variabile lastTimeRead
     */
    private synchronized void setLastTimeRead(final long value) {
        lastTimeRead = value;
    }
}