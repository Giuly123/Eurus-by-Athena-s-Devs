package game.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Versione custom del cell renderer della jList.
 */
public class CustomCellRenderer extends DefaultListCellRenderer
{
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        label.setPreferredSize(new Dimension(60,40));

        Color color =  isSelected ? new Color(240, 215, 211) : new Color(0, 0, 0, 50);

        label.setBackground(color);
        
        label.setHorizontalAlignment(CENTER);
        label.setText(label.getText());

        return label;
    }
}