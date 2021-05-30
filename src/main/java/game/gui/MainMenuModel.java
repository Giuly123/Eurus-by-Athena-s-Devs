package game.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuModel
{
    public MainMenuModel()
    {

    }

    public ActionListener getOnStart()
    {
        return onStart;
    }

    public ActionListener getOnContinue()
    {
        return onContinue;
    }

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
