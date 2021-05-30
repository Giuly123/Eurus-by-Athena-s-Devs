package game.gui;

import javax.swing.*;
import java.awt.*;

public class JErrorPanel extends JFrame
{
    private JButton closeAppButton;
    private JLabel errorMessageTextLabel;
    private JPanel panel;

    public JErrorPanel(String errorMessage)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Fatal Error");
        this.setContentPane(panel);
        this.setPreferredSize(new Dimension(300, 200));
        this.setResizable(false);

        this.errorMessageTextLabel.setText(errorMessage);

        this.closeAppButton.addActionListener(e -> Runtime.getRuntime().exit(-1));

        this.pack();
        this.setVisible(true);
    }
}
