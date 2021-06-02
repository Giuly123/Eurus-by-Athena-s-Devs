package game.gameUtilities;


public class Stopwatch implements Runnable {

    private long lastTimeRead;

    private long timePassed;

    private boolean isStarted;

    private boolean isRunning;

    public boolean resume() {
        boolean result = false;

        if (isStarted) {
            setLastTimeRead(System.currentTimeMillis());
            setIsRunning(true);
            result = true;
        } else {
            start();
        }

        return result;
    }


    public boolean pause() {
        boolean result = false;

        if (getStarted()) {
            setIsRunning(false);
            result = true;
        }

        return result;
    }


    public void stop() {
        setStarted(false);
        setIsRunning(false);
        setTimePassed(0);
    }


    public boolean start() {
        boolean result = false;

        if (!getStarted()) {
            setTimePassed(0);
            setLastTimeRead(System.currentTimeMillis());
            setStarted(true);
            setIsRunning(true);

            Thread thread = new Thread(this);
            thread.start();

            result = true;
        }

        return result;
    }


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



    public synchronized long getTempoTrascorsoMillis() {
        return timePassed;
    }

    private boolean getStarted() {
        return isStarted;
    }

    private boolean getIsPlaying() {
        return isRunning;
    }

    private synchronized void setIsRunning(final boolean value) {
        isRunning = value;
    }

    private synchronized void setStarted(final boolean value) {
        isStarted = value;
    }

    private synchronized void setTimePassed(final long value) {
        timePassed = value;
    }

    private synchronized void setLastTimeRead(final long value) {
        lastTimeRead = value;
    }
}