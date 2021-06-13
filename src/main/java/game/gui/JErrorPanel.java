package game.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello dialog custom che mostra una stringa
 * e forza la chiusura dell'applicativo.
 */
class JErrorPanel extends JFrame
{
    private JButton closeAppButton;
    private JLabel errorMessageTextLabel;

    public JErrorPanel(String errorMessage) {
        initComponents(errorMessage);
        this.setVisible(true);
    }

    /**
     * Inizializza le componenti.
     * @param errorMsg messaggio da mostrare
     */
    private void initComponents(String errorMsg) {

        this.setTitle("Fatal Error");
        closeAppButton = new JButton();
        errorMessageTextLabel = new JLabel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //======== this ========
        setMinimumSize(new Dimension(300, 200));
        this.setPreferredSize(new Dimension(300, 200));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- quitButton ----
        closeAppButton.setText("quit");
        this.closeAppButton.addActionListener(e -> Runtime.getRuntime().exit(-1));
        contentPane.add(closeAppButton, BorderLayout.SOUTH);

        //---- errorMsg ----
        errorMessageTextLabel.setText(errorMsg);
        errorMessageTextLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        errorMessageTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(errorMessageTextLabel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
