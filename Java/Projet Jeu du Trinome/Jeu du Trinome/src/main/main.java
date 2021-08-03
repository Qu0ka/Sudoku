/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import listeners.mainActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import jeu_d_echec.echecBoard;
import jeu_du_trinome.trinomeBoard;
import jeu_du_trinome.Joueur;
import listeners.mainFrameActionListener;

/**
 *
 * @author hugop
 */
public class main {

    private static mainFrame mainFrame;
    private static trinomeBoard trinomeBoard;
    private static echecBoard echecBoard;
    private static Joueur joueur;
    private static mainActionListener mainActionListener;
    private static mainFrameActionListener mainFrameActionListener;

    private static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private static ImageIcon logo, classicLogo, varLittleLogo, loadLogo;
    private static Color defaultColor, boutonColor;
    private static Border leftBorder, sideBorder;
    private static Font littleText, bigText;

    private static JFrame startFrame;

    private static JPanel sidePanel, sideBottomPanel;
    private static JPanel leftPanel, leftTopPanel, leftCenterPanel;

    private static JButton classicSideButton, varSideButton, modeSombreButton, loadSideButton, startButton;
    private static JButton classicBoutton, varianteBoutton, loadBoutton;
    private static JButton regleButton;

    private static JLabel leftTopCenterLabelNorth, leftTopCenterLabelSouth;
    private static JLabel sideLogoLabel;

    private static final int startFrame_Width = 1000;
    private static final int startFrame_Heigth = 600;

    private static final int sidePanel_Width = 200;

    private static final int sideLogoLabel_Width = sidePanel_Width;
    private static final int sideLogoLabel_Heigth = 150;

    private static final int leftTopPanel_Width = 800;
    private static final int leftTopPanel_Heigth = 150;

    private static boolean variante = false;
    private static JButton jeuEchecButton;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long lStartTime = System.currentTimeMillis();
        try {
            logo = new ImageIcon("logo.png");
            classicLogo = new ImageIcon("classicGame.png");
            varLittleLogo = new ImageIcon("varianteIconLittle.png");
            loadLogo = new ImageIcon("loadingLittle.png");
        } catch (NullPointerException e) {
            System.out.println("Une image n'existe pas, fermeture du système");
        }
        defaultColor = new Color(109, 7, 26);
        boutonColor = new Color(151, 21, 57);
        
        mainActionListener = new mainActionListener();
        mainFrameActionListener = new mainFrameActionListener();
        
        leftBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        sideBorder = BorderFactory.createEmptyBorder(0, 20, 20, 20);
        littleText = new Font("Segoe UI", Font.BOLD, 14);
        bigText = new Font("Segoe UI", Font.BOLD, 36);

        startFrame = new JFrame("Jeu du Trinôme");
        startFrame.setPreferredSize(new Dimension(startFrame_Width, startFrame_Heigth));
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocation(dim.width / 2 - startFrame_Width / 2, dim.height / 2 - startFrame_Heigth / 2);
        startFrame.setIconImage(logo.getImage());

        sidePanel();
        leftPanel();

        startFrame.pack();
        startFrame.setVisible(true);

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds for the first frame: " + output);

    }

    public static void sidePanel() {

        sideLogoLabel = new JLabel("Jeu du Trinôme", logo, JLabel.CENTER);
        sideLogoLabel.setBackground(defaultColor);
        sideLogoLabel.setForeground(Color.WHITE);
        sideLogoLabel.setHorizontalTextPosition(JLabel.CENTER);
        sideLogoLabel.setVerticalTextPosition(JLabel.BOTTOM);
        sideLogoLabel.setFont(littleText);
        sideLogoLabel.setPreferredSize(new Dimension(sideLogoLabel_Width, sideLogoLabel_Heigth));
        sideLogoLabel.setOpaque(true);
//        sideLogoLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

        classicSideButton = new JButton("Trinôme classique");
        classicSideButton.setForeground(Color.WHITE);
        classicSideButton.addActionListener(mainActionListener);
        classicSideButton.setBackground(boutonColor);
        classicSideButton.setBorder(null);
        classicSideButton.setFocusPainted(false);

        varSideButton = new JButton("Trinôme avec variante");
        varSideButton.setForeground(Color.WHITE);
        varSideButton.setBackground(boutonColor);
        varSideButton.addActionListener(mainActionListener);
        varSideButton.setBorder(null);
        varSideButton.setFocusPainted(false);

        modeSombreButton = new JButton("Mode sombre");
        modeSombreButton.setForeground(Color.WHITE);
        modeSombreButton.setBackground(boutonColor);
        modeSombreButton.addActionListener(mainActionListener);
        modeSombreButton.setBorder(null);
        modeSombreButton.setFocusPainted(false);

        startButton = new JButton("Start");
        startButton.addActionListener(mainActionListener);
        startButton.setBackground(boutonColor);
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(null);
        startButton.setFocusPainted(false);
        startButton.setEnabled(false);

        loadSideButton = new JButton("Charger une partie");
        loadSideButton.addActionListener(mainActionListener);
        loadSideButton.setBackground(boutonColor);
        loadSideButton.setForeground(Color.WHITE);
        loadSideButton.setEnabled(false);
        loadSideButton.setBorder(null);
        loadSideButton.setFocusPainted(false);

        jeuEchecButton = new JButton("Jeu d'Echec");
        jeuEchecButton.addActionListener(mainActionListener);
        jeuEchecButton.setBackground(boutonColor);
        jeuEchecButton.setForeground(Color.WHITE);
        jeuEchecButton.setEnabled(true);
        jeuEchecButton.setBorder(null);
        jeuEchecButton.setFocusPainted(false);

        sideBottomPanel = new JPanel(new GridLayout(6, 1, 0, 20));
        sideBottomPanel.setBackground(defaultColor);
        sideBottomPanel.setBorder(sideBorder);
        sideBottomPanel.add(classicSideButton);
        sideBottomPanel.add(varSideButton);
        sideBottomPanel.add(jeuEchecButton);
        sideBottomPanel.add(modeSombreButton);
        sideBottomPanel.add(startButton);
        sideBottomPanel.add(loadSideButton);

        sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(sideLogoLabel, BorderLayout.NORTH);
        sidePanel.add(sideBottomPanel, BorderLayout.CENTER);

        startFrame.add(sidePanel, BorderLayout.WEST);
    }

    public static void leftPanel() {
        classicBoutton = new JButton("Partie classique", classicLogo);
        classicBoutton.addActionListener(mainActionListener);
        classicBoutton.setHorizontalTextPosition(JLabel.CENTER);
        classicBoutton.setVerticalTextPosition(JLabel.BOTTOM);
        classicBoutton.setContentAreaFilled(false);
        classicBoutton.setBorderPainted(false);
        classicBoutton.setFocusPainted(false);

        varianteBoutton = new JButton("Partie avec variante", varLittleLogo);
        varianteBoutton.addActionListener(mainActionListener);
        varianteBoutton.setHorizontalTextPosition(JLabel.CENTER);
        varianteBoutton.setVerticalTextPosition(JLabel.BOTTOM);
        varianteBoutton.setContentAreaFilled(false);
        varianteBoutton.setBorderPainted(false);
        varianteBoutton.setFocusPainted(false);

        loadBoutton = new JButton("Charger une partie", loadLogo);
        loadBoutton.addActionListener(mainActionListener);
        loadBoutton.setHorizontalTextPosition(JLabel.CENTER);
        loadBoutton.setVerticalTextPosition(JLabel.BOTTOM);
        loadBoutton.setContentAreaFilled(false);
        loadBoutton.setBorderPainted(false);
        loadBoutton.setFocusPainted(false);

        leftTopCenterLabelNorth = new JLabel("Bienvenue !", JLabel.CENTER);
        leftTopCenterLabelNorth.setFont(bigText);
        leftTopCenterLabelNorth.setForeground(Color.WHITE);

        leftTopCenterLabelSouth = new JLabel("Choissisez un mode de jeu dans le menu ou sur une des images puis cliquez\n sur le bouton pour le lancer", JLabel.CENTER);
        leftTopCenterLabelSouth.setFont(littleText);
        leftTopCenterLabelSouth.setForeground(Color.WHITE);

        regleButton = new JButton("Règles");
        regleButton.addActionListener(mainActionListener);
        regleButton.setBackground(defaultColor);
        regleButton.setForeground(Color.WHITE);
        regleButton.setEnabled(true);
        regleButton.setBorder(null);
        regleButton.setFocusPainted(false);

        leftTopPanel = new JPanel(new BorderLayout());
        leftTopPanel.setPreferredSize(new Dimension(leftTopPanel_Width, leftTopPanel_Heigth));
        leftTopPanel.setBorder(leftBorder);
        leftTopPanel.setBackground(defaultColor);
        leftTopPanel.add(leftTopCenterLabelNorth, BorderLayout.NORTH);
        leftTopPanel.add(leftTopCenterLabelSouth, BorderLayout.CENTER);
        leftTopPanel.add(regleButton, BorderLayout.SOUTH);

        leftCenterPanel = new JPanel(new GridLayout(1, 3));
        leftCenterPanel.setBackground(Color.WHITE);
        leftCenterPanel.setBorder(leftBorder);
        leftCenterPanel.add(classicBoutton);
        leftCenterPanel.add(varianteBoutton);
        leftCenterPanel.add(loadBoutton);

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
        leftPanel.add(leftCenterPanel, BorderLayout.CENTER);

        startFrame.add(leftPanel, BorderLayout.CENTER);
    }

    public static mainFrame getMainFrame() {
        return mainFrame;
    }

    public static JButton getClassicSideButton() {
        return classicSideButton;
    }

    public static JButton getVarSideButton() {
        return varSideButton;
    }

    public static JButton getModeSombreButton() {
        return modeSombreButton;
    }

    public static JPanel getLeftTopPanel() {
        return leftTopPanel;
    }

    public static JButton getClassicBoutton() {
        return classicBoutton;
    }

    public static JButton getVarianteBoutton() {
        return varianteBoutton;
    }

    public static JButton getStartButton() {
        return startButton;
    }

    public static void setMainFrame(mainFrame mainFrame) {
        main.mainFrame = mainFrame;
    }

    public static void setTrinomeBoard(trinomeBoard trinomeBoard) {
        main.trinomeBoard = trinomeBoard;
    }

    public static void setEchecBoard(echecBoard echecBoard) {
        main.echecBoard = echecBoard;
    }

    public static void setJoueur(Joueur joueur) {
        main.joueur = joueur;
    }

    public static Joueur getJoueur() {
        return joueur;
    }

    public static trinomeBoard getTrinomeBoard() {
        return trinomeBoard;
    }

    public static echecBoard getEchecBoard() {
        return echecBoard;
    }

    public static JFrame getStartFrame() {
        return startFrame;
    }

    public static void setVariante(boolean variante) {
        main.variante = variante;
    }

    public static boolean isVariante() {
        return variante;
    }

    public static JButton getLoadSideButton() {
        return loadSideButton;
    }

    public static JButton getLoadBoutton() {
        return loadBoutton;
    }

    public static JPanel getLeftPanel() {
        return leftPanel;
    }

    public static JPanel getLeftCenterPanel() {
        return leftCenterPanel;
    }

    public static mainActionListener getMainActionListener() {
        return mainActionListener;
    }

    public static Color getDefaultColor() {
        return defaultColor;
    }

    public static Color getBoutonColor() {
        return boutonColor;
    }

    public static JLabel getSideLogoLabel() {
        return sideLogoLabel;
    }

    public static JPanel getSidePanel() {
        return sidePanel;
    }

    public static JPanel getSideBottomPanel() {
        return sideBottomPanel;
    }

    public static JButton getRegleButton() {
        return regleButton;
    }
    public static JButton getJeuEchecButton() {
        return jeuEchecButton;
    }
    public static mainFrameActionListener getMainFrameActionListener() {
        return mainFrameActionListener;
    }
    
}
