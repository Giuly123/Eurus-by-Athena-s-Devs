package game.gui;


import game.gameUtilities.Utilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author unknown
 */
public class MainMenuController {

    private MainMenuModel model;
    private ImageIcon iconLogo = new ImageIcon(Utilities.PATH_LOGO);

    private JFrame frame = new JFrame();
    private JPanel mainPanel;
    private JButton startButton;
    private JButton continueButton;
    private JButton exitButton;

    public MainMenuController(boolean isFirstTime, MainMenuModel model) {
        this.model = model;
        frame.setTitle(Utilities.GAME_NAME);
        initComponents();
        initController(isFirstTime);
        frame.setVisible(true);
    }


    private void initController(boolean isFirstTime)
    {
        frame.setIconImage(iconLogo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(model.getOnStart());
        continueButton.addActionListener(model.getOnContinue());

        updateMenu(isFirstTime);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    private void initComponents() {
        startButton = new JButton();
        continueButton = new JButton();
        exitButton = new JButton();

        //======== this ========

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        Image img = new ImageIcon(Utilities.BACKGROUND_PATH).getImage();
        mainPanel = new ImagePanel(img);

        contentPane.add(mainPanel, BorderLayout.CENTER);

        frame.setResizable(false);

        //======== ButtonPanelBello ========
        {
            mainPanel.setLayout(new FlowLayout());

            //---- Inizia ----
            startButton.setText("Inizia");
            startButton.setPreferredSize(new Dimension(150, 50));
            mainPanel.add(startButton);

            //---- Continua ----
            continueButton.setText("Continua");
            continueButton.setPreferredSize(new Dimension(150, 50));
            mainPanel.add(continueButton);

            //---- Esci ----
            exitButton.setText("Esci");
            exitButton.setPreferredSize(new Dimension(150, 50));
            mainPanel.add(exitButton);
        }

        frame.pack();
        frame.setLocationRelativeTo(frame.getOwner());
    }

    public void updateMenu(boolean isFirstTime)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(isFirstTime)
                {
                    continueButton.setEnabled(false);
                }
                else
                {
                    continueButton.setEnabled(true);
                }
            }
        });

    }

    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }

    public void setLocation(Point point)
    {
        frame.setLocation(point);
    }

    private class ImagePanel extends JPanel
    {
        private Image img;

        public ImagePanel(Image img)
        {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g)
        {
            g.drawImage(img, 0, 0, null);
        }
    }
}
