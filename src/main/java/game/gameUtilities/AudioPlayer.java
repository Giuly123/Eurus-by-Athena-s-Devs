package game.gameUtilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class AudioPlayer
{
    Long currentFrame;
    Clip clip;

    public enum AudioPlayerStatus {play, paused, stopped, loaded, unLoaded}

    public AudioPlayerStatus status = AudioPlayerStatus.unLoaded;

    public FloatControl gainControl;

    AudioInputStream audioInputStream;

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
                gainControl.setValue(-38.0f);

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

    public void play()
    {
        if (status != AudioPlayerStatus.unLoaded && status != AudioPlayerStatus.play)
        {
            clip.start();
            if (this.status == AudioPlayerStatus.paused)
            {
                clip.setMicrosecondPosition(this.currentFrame);
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            status = AudioPlayerStatus.play;
        }
    }

    public void pause()
    {
        if (status != AudioPlayerStatus.unLoaded && status != AudioPlayerStatus.paused)
        {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();

            status = AudioPlayerStatus.paused;
        }
    }

    public void setVolume(float value)
    {
        try
        {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(value); // Reduce volume by 10 decibels.
        }
        catch (Exception e)
        {
            System.out.println("Volume fuori range");
        }
    }

}
