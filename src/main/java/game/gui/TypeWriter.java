package game.gui;

import javax.swing.*;

/**
 * Simula l'effetto "macchina da scrivere".
 */
public class TypeWriter
{
    public JTextArea textArea;
    public int delay;

    public TypeWriter(JTextArea textArea, int delay)
    {
        this.textArea = textArea;
        this.delay = delay;
    }

    /**
     * Effettua l'append sulla text area della stringa passata.
     * @param string stringa da concatenare
     */
    public void append(String string)
    {
        try
        {
            int i;

            for (i = 0; i < string.length(); i++)
            {
                char c = string.charAt(i);
                SwingUtilities.invokeLater(() -> textArea.append(String.valueOf(c)));

                Thread.sleep(delay);
            }

            if (string.length() > 0 && string.charAt(i - 1) != '\n')
            {
                SwingUtilities.invokeLater(() -> textArea.append("\n"));

                Thread.sleep(delay);
            }

            SwingUtilities.invokeLater(() -> textArea.append("\n"));

            Thread.sleep(delay);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


}

