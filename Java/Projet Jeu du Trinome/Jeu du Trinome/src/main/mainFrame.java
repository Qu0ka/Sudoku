/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import listeners.mainFrameActionListener;

/**
 *
 * @author hugop
 */
public final class mainFrame extends JFrame {

    private JPanel contentPanel, boardPanel;
    private final JPanel sideMenuPanel;

    private JButton saveButton, annulButton, poubelleButton, changerModeBouton;

    private JLabel consoleJTArea_Label;

    private ArrayList<ArrayList<JButton>> echiquierMatrix;

    // mainFrameGUI constants
    private final int echiquierMatrixSize;

    private final Color echiquierMatrix_PairColor, echiquierMatrix_ImpairColor, poubelle_Color;
    private Color zoneRougeColor;
    private ImageIcon saveButtonIcon, annulButtonIcon, poubelleButtonIcon, playerREDIndicIcon, playerGREENIndicIcon;
    private BufferedImage playerGREENIndiciImage, playerREDIndicImage, poubelleButtonImage, annulButtonImage, saveButtonImage;

    private Font buttonFont;
    private final boolean darkMode;
    private mainFrameActionListener actionListener;

    // Constructeurs
    public mainFrame(int echiquierMatrixSize, Color echiquierMatrix_PairColor, Color echiquierMatrix_ImpairColor) {

        this.echiquierMatrixSize = echiquierMatrixSize;

        this.echiquierMatrix_PairColor = echiquierMatrix_PairColor;
        this.echiquierMatrix_ImpairColor = echiquierMatrix_ImpairColor;
        this.poubelle_Color = Color.WHITE;
        this.darkMode = false;
        this.sideMenuPanel = new JPanel(new GridLayout(5, 1, 0, 50));
        sideMenuPanel.setBackground(main.getDefaultColor());
        initiateMainFrame();
    }

    // Dark mode constructeur
    public mainFrame(String darkMode, int echiquierMatrixSize, Color echiquierMatrix_PairColor, Color echiquierMatrix_ImpairColor) {

        this.echiquierMatrixSize = echiquierMatrixSize;

        this.echiquierMatrix_PairColor = echiquierMatrix_PairColor;
        this.echiquierMatrix_ImpairColor = echiquierMatrix_ImpairColor;
        this.poubelle_Color = Color.LIGHT_GRAY;
        this.darkMode = true;
        this.sideMenuPanel = new JPanel(new GridLayout(5, 1, 0, 50));
        sideMenuPanel.setBackground(Color.GRAY);
        initiateMainFrame();

    }

    public void initiateMainFrame() {
        long lStartTime = System.currentTimeMillis();

        try {
            playerGREENIndiciImage = ImageIO.read(new File("player-green.png"));
            playerREDIndicImage = ImageIO.read(new File("player-red.png"));
            poubelleButtonImage = ImageIO.read(new File("binIcon.png"));
            annulButtonImage = ImageIO.read(new File("go-back-arrow.png"));
            saveButtonImage = ImageIO.read(new File("save-game.png"));

        } catch (IOException ex) {
            System.out.println("Des icones de boutons n'ont pas pu être chargées");
        }

        this.saveButtonIcon = new ImageIcon(saveButtonImage);
        this.annulButtonIcon = new ImageIcon(annulButtonImage);
        this.poubelleButtonIcon = new ImageIcon(poubelleButtonImage);
        this.playerREDIndicIcon = new ImageIcon(playerREDIndicImage);
        this.playerGREENIndicIcon = new ImageIcon(playerGREENIndiciImage);

        this.boardPanel = new JPanel(new GridLayout(echiquierMatrixSize, echiquierMatrixSize));
        this.contentPanel = new JPanel(new BorderLayout());

        this.saveButton = new JButton("Sauvegarder", saveButtonIcon);
        this.annulButton = new JButton("Annuler le dernier coup", annulButtonIcon);
        this.poubelleButton = new JButton("Poubelle", poubelleButtonIcon);
        this.changerModeBouton = new JButton("Changer de mode de jeu");

        this.consoleJTArea_Label = new JLabel("Au tour du joueur", playerREDIndicIcon, JLabel.CENTER);

        this.zoneRougeColor = new Color(255, 114, 118);
        this.buttonFont = new Font("Arial", Font.BOLD, 11);
        this.echiquierMatrix = new ArrayList<>(echiquierMatrixSize);
        this.actionListener = new mainFrameActionListener();

        sideMenuPanel();   // consommation de ressources négligeable ~3ms
        boardPanel();           // Le plus consomateur ~26 ms
        if (main.getMainActionListener().isJeuEchec() == false) {
            colorZoneRouge();
        }

        // Total moyen : 130 ms
        main.getSidePanel().add(sideMenuPanel, BorderLayout.CENTER);
        main.getLeftPanel().add(boardPanel, BorderLayout.CENTER);

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds pour générer le jeu: " + output);

    }

    public void boardPanel() {
        try {

            for (int i = 0; i < echiquierMatrixSize; i++) {
                ArrayList<JButton> list = new ArrayList<>();
                echiquierMatrix.add(list);
                for (int j = 0; j < echiquierMatrixSize; j++) {
                    list.add(new JButton());
                    echiquierMatrix.get(i).get(j).putClientProperty("row", i);
                    echiquierMatrix.get(i).get(j).putClientProperty("column", j);
                    if (i % 2 == j % 2) {
                        echiquierMatrix.get(i).get(j).setBackground(echiquierMatrix_PairColor);
                    } else {
                        echiquierMatrix.get(i).get(j).setBackground(echiquierMatrix_ImpairColor);
                    }
                    echiquierMatrix.get(i).get(j).addActionListener(actionListener);
                    echiquierMatrix.get(i).get(j).setBorder(null);
                    echiquierMatrix.get(i).get(j).setFocusPainted(false);

                    boardPanel.add(echiquierMatrix.get(i).get(j));
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Le panel de l'échiquier n'a pas pu être généré : Array Out of Bounds");
        } catch (NullPointerException e) {
            System.out.println("BrickMatrix doesnt exist or is null / boardPanel doesnt exist or is null");
        }
    }

    public void sideMenuPanel() {

        saveButton.addActionListener(actionListener);
        saveButton.setFont(buttonFont);
        saveButton.setBackground(main.getBoutonColor());
        saveButton.setHorizontalAlignment(JLabel.CENTER);
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(null);
        saveButton.setFocusPainted(false);
        saveButton.setEnabled(true);

        annulButton.addActionListener(actionListener);
        annulButton.setBackground(main.getBoutonColor());
        annulButton.setFont(buttonFont);
        annulButton.setHorizontalAlignment(JLabel.CENTER);
        annulButton.setForeground(Color.WHITE);
        annulButton.setBorder(null);
        annulButton.setFocusPainted(false);
        annulButton.setEnabled(false);

        poubelleButton.addActionListener(actionListener);
        poubelleButton.setBackground(main.getBoutonColor());
        poubelleButton.setFont(buttonFont);
        poubelleButton.setHorizontalAlignment(JLabel.CENTER);
        poubelleButton.setForeground(Color.WHITE);
        poubelleButton.setBorder(null);
        poubelleButton.setFocusPainted(false);
        poubelleButton.setEnabled(false);

        changerModeBouton.addActionListener(actionListener);
        changerModeBouton.setForeground(Color.WHITE);
        changerModeBouton.setBackground(main.getBoutonColor());
        changerModeBouton.setFont(buttonFont);
        changerModeBouton.setBorder(null);
        changerModeBouton.setHorizontalAlignment(JLabel.CENTER);
        changerModeBouton.setFocusPainted(false);
        changerModeBouton.setEnabled(true);

        consoleJTArea_Label.setLayout(new BorderLayout());
        consoleJTArea_Label.setHorizontalAlignment(SwingConstants.CENTER);
        if (main.getMainActionListener().isJeuEchec() == false) {
            consoleJTArea_Label.setBackground(Color.RED);
            consoleJTArea_Label.setForeground(Color.WHITE);
        }
        else if (main.getMainActionListener().isJeuEchec()) {
            consoleJTArea_Label.setBackground(Color.WHITE);
            consoleJTArea_Label.setForeground(Color.BLACK);
        }
        consoleJTArea_Label.setFont(buttonFont);
        consoleJTArea_Label.setOpaque(true);

        sideMenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sideMenuPanel.add(consoleJTArea_Label);
        sideMenuPanel.add(annulButton);
        sideMenuPanel.add(poubelleButton);
        sideMenuPanel.add(saveButton);
        sideMenuPanel.add(changerModeBouton);

    }

    public void colorZoneRouge() {
        echiquierMatrix.get(0).get(4).setBackground(zoneRougeColor);
        echiquierMatrix.get(0).get(5).setBackground(zoneRougeColor);
        echiquierMatrix.get(0).get(6).setBackground(zoneRougeColor);
        echiquierMatrix.get(10).get(4).setBackground(zoneRougeColor);
        echiquierMatrix.get(10).get(5).setBackground(zoneRougeColor);
        echiquierMatrix.get(10).get(6).setBackground(zoneRougeColor);
    }

    public ArrayList<ArrayList<JButton>> getEchiquierMatrix() {
        return echiquierMatrix;
    }

    public JButton getPoubelleButton() {
        return poubelleButton;
    }

    public JLabel getConsoleJTArea_Label() {
        return consoleJTArea_Label;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getAnnulButton() {
        return annulButton;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public Color getEchiquierMatrixSize_PairColor() {
        return echiquierMatrix_PairColor;
    }

    public Color getEchiquierMatrixSize_ImpairColor() {
        return echiquierMatrix_ImpairColor;
    }

    public Color getPoubelle_Color() {
        return poubelle_Color;
    }

    public int getEchiquierMatrixSize() {
        return echiquierMatrixSize;
    }

    public ImageIcon getPlayerREDIndicIcon() {
        return playerREDIndicIcon;
    }

    public ImageIcon getPlayerGREENIndicIcon() {
        return playerGREENIndicIcon;
    }

    public mainFrameActionListener getActionListener() {
        return actionListener;
    }

    public JButton getChangerModeBouton() {
        return changerModeBouton;
    }

    public JPanel getSideMenuPanel() {
        return sideMenuPanel;
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public Color getZoneRougeColor() {
        return zoneRougeColor;
    }

}
