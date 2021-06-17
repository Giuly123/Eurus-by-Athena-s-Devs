package game.gui;

import game.gameUtilities.Utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

class GUIUtilities
{
    /**
     * Effettua il setup di un pulsante.
     * @param button pulsante da impostare
     * @param pathIcon path dell'icona
     * @param dimension dimensione del pulsante
     */

    public static void setButton(JButton button, String pathIcon, Dimension dimension)
    {
        try {
            if (Utilities.fileExist(pathIcon))
            {
                Image image = ImageIO.read(new File(pathIcon));
                Image scaledImage = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
                button.setText("");
                button.setIcon(new ImageIcon(scaledImage));
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setBorder(null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
