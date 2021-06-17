package game.gameUtilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Si occupa di riprodurre file audio.
 */
public class AudioPlayer
{
    Clip clip;

    /**
     * Stati dell'audioplayer.
     */
    public enum AudioPlayerStatus {play, paused, stopped, loaded, unLoaded}

    public AudioPlayerStatus status = AudioPlayerStatus.unLoaded;

    public FloatControl gainControl;

    AudioInputStream audioInputStream;

    /**
     * Costruttore audioplayer.
     * @param filePath file da riprodurre
     */
    public AudioPlayer(String filePath)
    {
        try
        {
            if (Utilities.fileExist(filePath))
            {
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-28.0f);

                status = AudioPlayerStatus.loaded;
            }
            else
            {
                status = AudioPlayerStatus.unLoaded;
            }
        }
        catch (Exception e)
        {
            status = AudioPlayerStatus.unLoaded;
            e.printStackTrace();
        }
    }

    /**
     * Starta la riproduzione del file.
     */
    public void play()
    {
        if (status != AudioPlayerStatus.unLoaded)
        {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            status = AudioPlayerStatus.play;
        }
    }

    /**
     * Mette in pausa la riproduzione del file.
     */
    public void pause()
    {
        if (status != AudioPlayerStatus.unLoaded)
        {
            clip.stop();
            status = AudioPlayerStatus.paused;
        }
    }

    /**
     * Setta il volume dell'applicativo.
     * @param value valore del gain da settare.
     */
    public void setVolume(float value)
    {
        try
        {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(value);
        }
        catch (Exception e)
        {
            System.out.println("Volume fuori range");
        }
    }

}
