package game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gestisce la logica del main menu.
 */
public class MainMenuModel
{
    public MainMenuModel()
    {

    }

    /**
     *
     * @return l'azione da eseguire quando viene cliccato il tasto inizia
     */
    public ActionListener getOnStart()
    {
        return onStart;
    }

    /**
     *
     * @return l'azione da eeseguire quando viene cliccato il tasto continua
     */
    public ActionListener getOnContinue()
    {
        return onContinue;
    }

    /**
     * Azione associata al click del tasto inizia
     */
    final private ActionListener onStart = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!GUIManager.getInstance().isPlaying)
            {
                GUIManager.getInstance().isPlaying = true;
                GUIManager.getInstance().startNewGame(false);
            }
        }
    };

    /**
     * Azione associata al click del tasto continua
     */
    final private ActionListener onContinue = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!GUIManager.getInstance().isPlaying)
            {
                GUIManager.getInstance().isPlaying = true;
                GUIManager.getInstance().startNewGame(true);
            }
        }
    };
}
