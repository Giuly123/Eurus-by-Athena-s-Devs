package game.gui;


import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;
import game.entity.item.Item;
import game.gameUtilities.AudioPlayer;
import game.gameUtilities.Utilities;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author unknown
 */
public class GameView
{
    private TypeWriter typeWriter;
    private DefaultListModel<Item> modelList = new DefaultListModel<>();
    private Map<String, Icon> imageCached = new HashMap<>();

    private static AudioPlayer audioPlayer;

    public GameView()
    {
        initComponents();
        initView();

        if (audioPlayer == null)
        {
            audioPlayer = new AudioPlayer(Utilities.MUSIC_PATH);
        }
        audioPlayer.play();
        audioSlider.setValue((int)audioPlayer.gainControl.getValue());

        frame.setVisible(true);
    }

    public JTextField getTextField()
    {
        return inputField;
    }

    public void requestFocusSafe(JComponent jComponent)
    {
        SwingUtilities.invokeLater(() -> jComponent.requestFocus());
    }

    public void setEditableSafe(JComponent jComponent, boolean enabled)
    {
        SwingUtilities.invokeLater(() -> jComponent.setEnabled(enabled));
    }

    public void addActionHomeButton(ActionListener action)
    {
        this.homeButton.addActionListener(e-> {audioPlayer.pause();});
        this.homeButton.addActionListener(action);
    }


    public void addActionSaveButton(ActionListener action)
    {
        this.saveButton.addActionListener(action);
    }

    public void addActionOnTextFiledEnter(ActionListener action)
    {
        this.inputField.addActionListener(action);
    }


    public void appendText(String string)
    {
        typeWriter.Append(string);
    }


    public void addItemToInventory(Item item)
    {
        SwingUtilities.invokeLater(() ->
        {
            modelList.addElement(item);
            inventoryList.updateUI();
        });
    }

    public void removeItemToInventory(Item item)
    {
        SwingUtilities.invokeLater(() ->
        {
            modelList.removeElement(item);
            inventoryList.updateUI();
        });
    }

    public void showFatalError(String errorMessage)
    {
        SwingUtilities.invokeLater(() -> {
            frame.setFocusableWindowState(false);
            JErrorPanel errorPanel = new JErrorPanel(errorMessage);
            errorPanel.setLocation(frame.getLocation());
            errorPanel.setFocusable(true);
            errorPanel.toFront();
        });
    }

    public Point getLocationOnScreen()
    {
        return frame.getLocationOnScreen();
    }

    public void setVisible(boolean value)
    {
        frame.setVisible(value);
    }

    private void setButton(JButton button, String pathIcon, int size)
    {
        try {
            if (Utilities.fileExist(pathIcon))
            {
                Image image = ImageIO.read(new File(pathIcon));
                Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
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


    private void setImageItemSelected(String path)
    {
        try
        {
            if (!Utilities.fileExist(path))
            {
                path = Utilities.texturesPath + "default.png";
            }

            Icon icon = imageCached.get(path);

            if (icon == null)
            {
                Image image = ImageIO.read(new File(path));
                Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
                imageCached.put(path, icon);
            }

            labelImage.setIcon(icon);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setInventoryList()
    {
        inventoryList.setModel(modelList);

        inventoryList.addListSelectionListener(e ->
        {
            Item item = ((Item) inventoryList.getSelectedValue());
            if (item != null)
            {
                setImageItemSelected(Utilities.texturesPath + item.getAssetName());
            }
        });

        setImageItemSelected(Utilities.texturesPath + "default.png");
    }


    private void initView()
    {
        //System.out.println(SwingUtilities.isEventDispatchThread());
        ImageIcon iconLogo = new ImageIcon(Utilities.PATH_LOGO);

        frame.setIconImage(iconLogo.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set TextArea
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        typeWriter = new TypeWriter(textArea, Utilities.timeDelayTyperWrite);

        //Set Home button
        setButton(homeButton, Utilities.ICON_HOME_PATH, 35);
        setButton(saveButton, Utilities.ICON_SAVE_PATH, 35);

        setInventoryList();

        frame.setVisible(false);
    }

    private void setAudioVolume()
    {
        if (audioPlayer != null)
        {
            if (audioSlider.getValue() != audioSlider.getMinimum())
            {
                if (audioPlayer.status == AudioPlayer.AudioPlayerStatus.paused)
                {
                    audioPlayer.play();
                }

                audioPlayer.setVolume(audioSlider.getValue() * 1.0f);
            }
            else
            {
                audioPlayer.pause();
            }
        }
    }


    private void audioSliderMouseReleased(MouseEvent e)
    {
        setAudioVolume();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        frame = new JFrame();
        topPanel = new JPanel();
        leftPanel = new JPanel();
        audioSlider = new JSlider();
        rightPanel = new JPanel();
        saveButton = new JButton();
        homeButton = new JButton();
        hSpacer1 = new JPanel(null);
        midPanel = new JPanel();
        mainPanel = new JPanel();
        textAreaPanel = new JPanel();
        textAreaScrollPane = new JScrollPane();
        textArea = new JTextArea();
        inputField = new JTextField();
        inventoryPanel = new JPanel();
        inventoryScrollPane = new JScrollPane();
        inventoryList = new JList();
        panelImage = new JPanel();
        labelImage = new JLabel();
        inventoryLabel = new JLabel();

        //======== frame ========
        {
            frame.setMinimumSize(new Dimension(50, 100));
            frame.setBackground(new Color(51, 255, 102));
            Container frameContentPane = frame.getContentPane();
            frameContentPane.setLayout(new BorderLayout());

            //======== topPanel ========
            {
                topPanel.setMinimumSize(new Dimension(22, 45));
                topPanel.setPreferredSize(new Dimension(36, 45));
                topPanel.setBackground(new Color(255, 102, 51));
                topPanel.setAutoscrolls(true);
                topPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                topPanel.setForeground(new Color(255, 51, 204));
//                topPanel.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
//                EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax . swing
//                . border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,
//                java . awt. Color .red ) ,topPanel. getBorder () ) ); topPanel. addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
//                { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )
//                throw new RuntimeException( ) ;} } );
                topPanel.setLayout(new BorderLayout());

                //======== leftPanel ========
                {
                    leftPanel.setForeground(new Color(0, 204, 204));
                    leftPanel.setBackground(new Color(255, 102, 255));
                    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));

                    //---- audioSlider ----
                    audioSlider.setMaximum(6);
                    audioSlider.setValue(-20);
                    audioSlider.setMinimum(-40);
                    audioSlider.setMinorTickSpacing(1);
                    audioSlider.setToolTipText("Audio");
                    audioSlider.setMajorTickSpacing(1);
                    audioSlider.setBorder(new CompoundBorder(
                            new BevelBorder(BevelBorder.LOWERED),
                            new TitledBorder("Audio volume")));

                    audioSlider.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            audioSliderMouseReleased(e);
                        }
                    });
                    leftPanel.add(audioSlider);
                }
                topPanel.add(leftPanel, BorderLayout.WEST);

                //======== rightPanel ========
                {
                    rightPanel.setForeground(new Color(153, 255, 51));
                    rightPanel.setBackground(new Color(251, 153, 255));

                    //---- saveButton ----
                    saveButton.setText("save");
                    saveButton.setMargin(new Insets(5, 14, 5, 14));
                    saveButton.setForeground(new Color(255, 51, 51));
                    saveButton.setBackground(new Color(51, 255, 51));

                    //---- homeButton ----
                    homeButton.setText("home");
                    homeButton.setMargin(new Insets(5, 14, 5, 14));
                    homeButton.setForeground(Color.red);
                    homeButton.setBackground(new Color(255, 51, 51));

                    GroupLayout rightPanelLayout = new GroupLayout(rightPanel);
                    rightPanel.setLayout(rightPanelLayout);
                    rightPanelLayout.setHorizontalGroup(
                            rightPanelLayout.createParallelGroup()
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                            .addComponent(saveButton)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(hSpacer1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(homeButton))
                    );
                    rightPanelLayout.setVerticalGroup(
                            rightPanelLayout.createParallelGroup()
                                    .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(hSpacer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap())
                    );
                }
                topPanel.add(rightPanel, BorderLayout.EAST);
            }
            frameContentPane.add(topPanel, BorderLayout.NORTH);

            //======== midPanel ========
            {
                midPanel.setBackground(new Color(153, 153, 255));
                midPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
                midPanel.setLayout(new CardLayout());

                //======== mainPanel ========
                {
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

                    //======== textAreaPanel ========
                    {
                        textAreaPanel.setPreferredSize(new Dimension(350, 533));
                        textAreaPanel.setBackground(new Color(153, 0, 153));
                        textAreaPanel.setMinimumSize(new Dimension(49, 500));
                        textAreaPanel.setLayout(new BorderLayout(0, 8));

                        //======== textAreaScrollPane ========
                        {
                            textAreaScrollPane.setAutoscrolls(true);
                            textAreaScrollPane.setPreferredSize(new Dimension(35, 200));
                            textAreaScrollPane.setMaximumSize(new Dimension(32767, 100));

                            //---- textArea ----
                            textArea.setEditable(false);
                            textArea.setWrapStyleWord(true);
                            textArea.setMargin(new Insets(4, 4, 4, 4));
                            textAreaScrollPane.setViewportView(textArea);
                        }
                        textAreaPanel.add(textAreaScrollPane, BorderLayout.CENTER);

                        //---- inputField ----
                        inputField.setPreferredSize(new Dimension(5, 30));
                        inputField.setMargin(new Insets(10, 4, 4, 4));
                        textAreaPanel.add(inputField, BorderLayout.SOUTH);
                    }
                    mainPanel.add(textAreaPanel);

                    //======== inventoryPanel ========
                    {
                        inventoryPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                        inventoryPanel.setPreferredSize(new Dimension(180, 500));
                        inventoryPanel.setMinimumSize(new Dimension(22, 500));
                        inventoryPanel.setBackground(new Color(102, 255, 102));
                        inventoryPanel.setLayout(new BorderLayout());

                        //======== inventoryScrollPane ========
                        {
                            inventoryScrollPane.setBackground(new Color(255, 153, 153));
                            inventoryScrollPane.setViewportView(inventoryList);
                        }
                        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);

                        //======== panelImage ========
                        {
                            panelImage.setPreferredSize(new Dimension(220, 220));
                            panelImage.setBackground(new Color(255, 255, 153));
                            panelImage.setMinimumSize(new Dimension(220, 220));
                            panelImage.setMaximumSize(new Dimension(220, 220));
                            panelImage.setLayout(new CardLayout());

                            //---- labelImage ----
                            labelImage.setHorizontalAlignment(SwingConstants.CENTER);
                            panelImage.add(labelImage, "card1");
                        }
                        inventoryPanel.add(panelImage, BorderLayout.PAGE_END);

                        //---- inventoryLabel ----
                        inventoryLabel.setText("INVENTORY");
                        inventoryLabel.setMinimumSize(new Dimension(65, 25));
                        inventoryLabel.setPreferredSize(new Dimension(65, 25));
                        inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        inventoryLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
                        inventoryLabel.setBackground(Color.black);
                        inventoryLabel.setForeground(Color.black);
                        inventoryPanel.add(inventoryLabel, BorderLayout.PAGE_START);
                    }
                    mainPanel.add(inventoryPanel);
                }
                midPanel.add(mainPanel, "card1");
            }
            frameContentPane.add(midPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JFrame frame;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JSlider audioSlider;
    private JPanel rightPanel;
    private JButton saveButton;
    private JButton homeButton;
    private JPanel hSpacer1;
    private JPanel midPanel;
    private JPanel mainPanel;
    private JPanel textAreaPanel;
    private JScrollPane textAreaScrollPane;
    private JTextArea textArea;
    private JTextField inputField;
    private JPanel inventoryPanel;
    private JScrollPane inventoryScrollPane;
    private JList inventoryList;
    private JPanel panelImage;
    private JLabel labelImage;
    private JLabel inventoryLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
