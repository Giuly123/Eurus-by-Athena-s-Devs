package game.gui;

import java.awt.event.*;
import javax.swing.border.*;
import game.entity.item.Item;
import game.gameUtilities.AudioPlayer;
import game.gameUtilities.Utilities;
import game.managers.database.GameDatabaseManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Si occupa della gestione dell'interfacciamento con l'utente.
 */
public class GameView
{
    private TypeWriter typeWriter;
    private DefaultListModel<Item> modelList = new DefaultListModel<>();
    private Map<String, Icon> imageCached = new HashMap<>();

    private static AudioPlayer audioPlayer;
    private String currentInventoryImage;

    public GameView()
    {
        initComponents();
        initView();

        setAudioSlider();

        frame.setVisible(true);
    }

    /**
     * Imposta lo Slider del volume.
     */
    private void setAudioSlider()
    {
        if (audioPlayer == null)
        {
            audioPlayer = new AudioPlayer(Utilities.MUSIC_PATH);
        }

        if (audioPlayer.status != AudioPlayer.AudioPlayerStatus.unLoaded)
        {
            audioPlayer.play();

            try
            {
                Float value = GameDatabaseManager.getInstance().getValueFromTable("volume", "CURRENTPLAYER");
                audioSlider.setValue(Math.round(value));

            } catch (Exception exception)
            {
                audioSlider.setValue((int)audioPlayer.gainControl.getValue());
            }

            setAudioVolume();
        }
        else
        {
            audioSlider.setVisible(false);
        }
    }

    /**
     * Si occupa del dispose del frame.
     */
    public void disposeFrame()
    {
        frame.dispose();
    }

    /**
     *
     * @return l'input field.
     */
    public JTextField getTextField()
    {
        return inputField;
    }

    /**
     * Reimposta il focus sull'ultima cosa scritta.
     * @param jComponent componente swing
     */
    public void requestFocusSafe(JComponent jComponent)
    {
        SwingUtilities.invokeLater(() -> jComponent.requestFocus());
    }

    /**
     * Setta la proprietà editable sui jComponent.
     * @param jComponent componente swing
     * @param enabled valore da assegnare alla proprietà
     */
    public void setEditableSafe(JComponent jComponent, boolean enabled)
    {
        SwingUtilities.invokeLater(() -> jComponent.setEnabled(enabled));
    }

    /**
     * Aggiunge un listener all'home button.
     * @param action azione da aggiungere quando l'home button viene cliccato
     */
    public void addActionHomeButton(ActionListener action)
    {
        this.homeButton.addActionListener(e-> {audioPlayer.pause();});
        this.homeButton.addActionListener(action);
    }

    /**
     * Riporta lo scroll della text area alla fine.
     */
    public void forceFocusOnAreaText()
    {
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Aggiunge un listener al save button.
     * @param action azione da aggiungere quando il save button viene cliccato
     */
    public void addActionSaveButton(ActionListener action)
    {
        this.saveButton.addActionListener(action);
    }

    /**
     * Aggiunge un listener al test field.
     * @param action azione da aggiungere quando viene premuto il tasto invio
     */
    public void addActionOnTextFiledEnter(ActionListener action)
    {
        this.inputField.addActionListener(action);
    }

    /**
     * Esegue l'append di una stringa alla text area con l'effetto "macchina da scrivere".
     * @param string stringa da concatenare
     */
    public void appendText(String string)
    {
        typeWriter.append(string);
    }

    /**
     * Esegue l'append di una stringa alla text area senza l'effetto "macchina da scrivere".
     * @param string stringa da concatenare
     */
    public void appendTextWithoutDelay(String string)
    {
        SwingUtilities.invokeLater(() -> {
            textArea.setText(string);
        });
    }

    /**
     *
     * @return il valore del volume
     */
    public int getVolumeValue()
    {
       return audioSlider.getValue();
    }

    /**
     * Imposta il testo della label time.
     * @param time tempo espresso in millisecondi
     */
    public void setTimeGame(long time)
    {
        setTimeGame("Time: " + Utilities.parseTime(time));
    }

    /**
     * Imposta il testo della label time.
     * @param string stringa da impostare
     */
    public void setTimeGame(String string)
    {
        SwingUtilities.invokeLater(() ->
        {
            stopwatchLabel.setText(string);
        });
    }

    /**
     * Imposta il titolo del Frame.
     * @param string stringa da impostare
     */
    public void setTitleFrame(String string)
    {
        frame.setTitle(string);
    }

    /**
     * Aggiunge un item alla lista dell'inventario.
     * @param item oggetto di tipo item da aggiungere
     */
    public void addItemToInventory(Item item)
    {
        SwingUtilities.invokeLater(() ->
        {
            modelList.addElement(item);
            inventoryList.updateUI();
        });
    }

    /**
     * Rimuove un item dalla lista dell'inventario.
     * @param item oggetto di tipo item da rimuovere
     */
    public void removeItemToInventory(Item item)
    {
        SwingUtilities.invokeLater(() ->
        {
            String tempImagePath = Utilities.texturesPath + item.getAssetName();
            if(tempImagePath.equalsIgnoreCase(currentInventoryImage))
            {
                setImageItemSelected("");
            }

            modelList.removeElement(item);
            inventoryList.updateUI();
        });
    }

    /**
     * Istanzia l'error panel.
     * @param errorMessage messaggio di errore stampato
     */
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

    /**
     * Imposta la proprietà enable dei pulsanti.
     * @param value valore da assegnare alla proprietà
     */
    public void enableButtons(boolean value)
    {
        saveButton.setEnabled(value);
        homeButton.setEnabled(value);
    }

    /**
     *
     * @return il contenuto della text area sotto forma di stringa
     */
    public String getTextAreaContent()
    {
        return textArea.getText();
    }

    public Point getLocationOnScreen()
    {
        return frame.getLocationOnScreen();
    }

    /**
     * Imposta la proprietà visible del frame.
     * @param value valore da assegnare alla proprietà visible
     */
    public void setVisible(boolean value)
    {
        frame.setVisible(value);
    }



    /**
     * Fa comparire l'asset dell'item selezionato nell'apposito pannello.
     * @param path path dell'item selezionato
     */
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
                currentInventoryImage = path;
            }

            labelImage.setIcon(icon);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Effettua il setup dell'inventory list.
     */
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


    /**
     * Inizializza la view.
     */
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
        GUIUtilities.setButton(homeButton, Utilities.ICON_HOME_PATH, new Dimension(35, 35));
        GUIUtilities.setButton(saveButton, Utilities.ICON_SAVE_PATH, new Dimension(35, 35));

        setInventoryList();

        frame.setVisible(false);
    }

    /**
     * Imposta il volume dell'applicativo.
     */
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


    /**
     * Azione effettuata al rilascio dello slider da parte del mouse.
     * @param e rilascio del mouse
     */
    private void audioSliderMouseReleased(MouseEvent e)
    {
        SwingUtilities.invokeLater(() -> {
            setAudioVolume();
        });
    }

    /**
     * Inizializza le componenti.
     */
    private void initComponents() {
        frame = new JFrame();
        topPanel = new JPanel();
        stopwatchLabel = new JLabel();
        hSpacer2 = new JPanel(null);
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
            frame.setMinimumSize(new Dimension(900, 900));
            frame.setPreferredSize(new Dimension(900, 900));
            frame.setBackground(new Color(51, 255, 102));
            Container frameContentPane = frame.getContentPane();
            frameContentPane.setLayout(new BorderLayout());

            //======== topPanel ========
            {
                topPanel.setMinimumSize(new Dimension(22, 45));
                topPanel.setPreferredSize(new Dimension(36, 45));
                topPanel.setBackground(new Color(31, 31, 31));
                topPanel.setAutoscrolls(true);
                topPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                topPanel.setForeground(new Color(255, 51, 204));

                topPanel.setLayout(new BorderLayout());

                //======== leftPanel ========
                {
                    leftPanel.setForeground(new Color(0, 204, 204));
                    leftPanel.setBackground(new Color(31, 31, 31));
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
                            new TitledBorder(null, "Audio volume", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, new Color(185, 255, 255))));
                    audioSlider.setBackground(new Color(31, 31, 31));
                    audioSlider.setForeground(new Color(193, 252, 253));

                    audioSlider.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            audioSliderMouseReleased(e);
                        }
                    });
                    leftPanel.add(audioSlider);

                    //---- hSpacer2 ----
                    hSpacer2.setMinimumSize(new Dimension(25, 12));
                    hSpacer2.setPreferredSize(new Dimension(25, 10));
                    hSpacer2.setBackground(new Color(31, 31, 31));
                    leftPanel.add(hSpacer2);

                }
                topPanel.add(leftPanel, BorderLayout.WEST);

                //======== rightPanel ========
                {
                    rightPanel.setForeground(new Color(153, 255, 51));
                    rightPanel.setBackground(new Color(31, 31, 31));

                    //---- saveButton ----
                    saveButton.setText("save");
                    saveButton.setMargin(new Insets(5, 14, 5, 14));
                    saveButton.setForeground(new Color(255, 51, 51));
                    saveButton.setBackground(new Color(51, 255, 51));
                    saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    //---- homeButton ----
                    homeButton.setText("home");
                    homeButton.setMargin(new Insets(5, 14, 5, 14));
                    homeButton.setForeground(new Color(153, 255, 51));
                    homeButton.setBackground(new Color(255, 51, 51));
                    homeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    //---- hSpacer1 ----
                    hSpacer1.setForeground(new Color(193, 19, 241, 0));
                    hSpacer1.setBackground(new Color(28, 39, 57, 0));;

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

                //---- stopwatchLabel ----
                stopwatchLabel.setText("Time: 00:00");
                stopwatchLabel.setHorizontalTextPosition(SwingConstants.LEFT);
                stopwatchLabel.setHorizontalAlignment(SwingConstants.LEFT);
                stopwatchLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
                stopwatchLabel.setForeground(new Color(193, 229, 255));

                topPanel.add(stopwatchLabel, BorderLayout.CENTER);

            }
            frameContentPane.add(topPanel, BorderLayout.NORTH);

            //======== midPanel ========
            {
                midPanel.setBackground(new Color(110, 109, 173));
                midPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
                midPanel.setLayout(new CardLayout());

                //======== mainPanel ========
                {
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

                    //======== textAreaPanel ========
                    {
                        textAreaPanel.setPreferredSize(new Dimension(350, 533));
                        textAreaPanel.setBackground(new Color(224, 104, 107));
                        textAreaPanel.setMinimumSize(new Dimension(49, 500));
                        textAreaPanel.setBorder(null);
                        textAreaPanel.setLayout(new BorderLayout(0, 8));

                        //======== textAreaScrollPane ========
                        {
                            textAreaScrollPane.setAutoscrolls(true);
                            textAreaScrollPane.setPreferredSize(new Dimension(35, 200));
                            textAreaScrollPane.setMaximumSize(new Dimension(32767, 100));
                            textAreaScrollPane.setBorder(new LineBorder(new Color(224, 104, 107), 5));

                            //---- textArea ----
                            textArea.setEditable(false);
                            textArea.setWrapStyleWord(true);
                            textArea.setMargin(new Insets(4, 4, 4, 4));
                            textArea.setBackground(new Color(41, 41, 41));
                            textArea.setForeground(new Color(235, 235, 235));
                            textArea.setFont(new Font("monospaced", Font.PLAIN, 14));
                            textArea.setBorder(null);
                            textAreaScrollPane.setViewportView(textArea);
                        }
                        textAreaPanel.add(textAreaScrollPane, BorderLayout.CENTER);

                        //---- inputField ----
                        inputField.setPreferredSize(new Dimension(5, 30));
                        inputField.setMargin(new Insets(10, 4, 4, 4));
                        inputField.setBackground(new Color(41, 41, 41));
                        inputField.setForeground(new Color(235, 235, 235));
                        inputField.setBorder(null);
                        inputField.requestFocusInWindow();
                        textAreaPanel.add(inputField, BorderLayout.SOUTH);
                    }
                    mainPanel.add(textAreaPanel);

                    //======== inventoryPanel ========
                    {
                        inventoryPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                        inventoryPanel.setPreferredSize(new Dimension(180, 500));
                        inventoryPanel.setMinimumSize(new Dimension(22, 500));
                        inventoryPanel.setBackground(new Color(224, 104, 107));
                        inventoryPanel.setForeground(new Color(235, 235, 235));
                        inventoryPanel.setBorder(null);
                        inventoryPanel.setLayout(new BorderLayout());

                        //======== inventoryScrollPane ========
                        {
                            inventoryList.setCellRenderer(new CustomCellRenderer());
                            inventoryList.setBackground(new Color(41, 41, 41));
                            inventoryList.setForeground(new Color(235, 235, 235));
                            inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            inventoryScrollPane.setViewportView(inventoryList);
                        }
                        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);

                        //======== panelImage ========
                        {
                            panelImage.setPreferredSize(new Dimension(220, 220));
                            panelImage.setBackground(new Color(240, 215, 211));
                            panelImage.setMinimumSize(new Dimension(220, 220));
                            panelImage.setMaximumSize(new Dimension(220, 220));
                            panelImage.setBorder(null);
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
                        inventoryLabel.setBackground(new Color(102, 12, 121));
                        inventoryLabel.setForeground(new Color(235, 235, 235));
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
    }

    private JFrame frame;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel hSpacer2;
    private JSlider audioSlider;
    private JPanel rightPanel;
    private JButton saveButton;
    private JButton homeButton;
    private JPanel hSpacer1;
    private JPanel midPanel;
    private JPanel mainPanel;
    private JPanel textAreaPanel;
    private JLabel stopwatchLabel;
    private JScrollPane textAreaScrollPane;
    private JTextArea textArea;
    private JTextField inputField;
    private JPanel inventoryPanel;
    private JScrollPane inventoryScrollPane;
    private JList inventoryList;
    private JPanel panelImage;
    private JLabel labelImage;
    private JLabel inventoryLabel;
}
