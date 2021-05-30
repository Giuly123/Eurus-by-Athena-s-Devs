package game.gui;

import javax.swing.*;

public class TypeWriter
{
    public JTextArea textArea;
    public int delay;

    public TypeWriter(JTextArea textArea, int delay)
    {
        this.textArea = textArea;
        this.delay = delay;
    }

    public void Append(String string)
    {
        try
        {
            int i;

            for (i = 0; i < string.length(); i++)
            {
                SwingUtilities.invokeLater(new TextAppendTask(string.charAt(i)));

                Thread.sleep(delay);
            }

            if (string.length() > 0 && string.charAt(i - 1) != '\n')
            {
                SwingUtilities.invokeLater(new TextAppendTask("\n"));

                Thread.sleep(delay);
            }

            SwingUtilities.invokeLater(new TextAppendTask("\n"));

            Thread.sleep(delay);

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    private class TextAppendTask implements Runnable
    {
        String string;

        TextAppendTask(String string)
        {
            this.string = string;
        }

        TextAppendTask(char c)
        {
            this.string = String.valueOf(c);
        }

        @Override
        public void run()
        {
            textArea.append(string);
        }
    }

}

