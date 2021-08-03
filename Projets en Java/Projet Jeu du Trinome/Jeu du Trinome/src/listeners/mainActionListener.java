/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import jeu_d_echec.echecBoard;
import jeu_du_trinome.trinomeBoard;
import jeu_du_trinome.Joueur;
import main.main;
import main.mainFrame;

/**
 *
 * @author hugop
 */
public class mainActionListener implements ActionListener {

    private boolean darkMode;
    private final ImageIcon classicGameIcon, classicGameLittleIcon, varianteGameIcon, varianteGameLittleIcon, loadGameIcon, loadGameLittleIcon;
    private boolean jeuEchec;

    public mainActionListener() {

        this.darkMode = false;
        this.jeuEchec = false;

        this.classicGameIcon = new ImageIcon("classicGame.png");
        this.classicGameLittleIcon = new ImageIcon("classicGameLittle.png");
        this.varianteGameIcon = new ImageIcon("varianteIcon.png");
        this.varianteGameLittleIcon = new ImageIcon("varianteIconLittle.png");
        this.loadGameIcon = new ImageIcon("loading.png");
        this.loadGameLittleIcon = new ImageIcon("loadingLittle.png");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == main.getRegleButton()) {
            JOptionPane.showMessageDialog(main.getMainFrame(), "Le premier joueur qui parvient à placer 3 différentes pièces (une demi-sphère + un cube + une pyramide) sur\n"
                    + "les 3 cases rouges de l'adversaire remporte la partie.\n"
                    + "Une fois arrivée dans la zone rouge de l’adversaire, une pièce est immobilisée et imprenable.\n"
                    + "Le premier joueur qui parvient à capturer tous les pions d'une sorte de son adversaire (exemple : les 6 cubes\n"
                    + "verts) remporte la partie.", "WARNING", JOptionPane.PLAIN_MESSAGE);
        }
        if ((e.getSource() == main.getClassicSideButton() || e.getSource() == main.getClassicBoutton())) {
            main.setVariante(false);
            main.getClassicSideButton().setBackground(Color.RED);
            main.getVarSideButton().setBackground(main.getBoutonColor());
            main.getLoadSideButton().setBackground(main.getBoutonColor());
            main.getJeuEchecButton().setBackground(main.getBoutonColor());
            main.getStartButton().setBackground(Color.RED);

            main.getLoadSideButton().setEnabled(false);
            main.getStartButton().setEnabled(true);

            main.getClassicBoutton().setIcon(classicGameIcon);
            main.getVarianteBoutton().setIcon(varianteGameLittleIcon);
            main.getLoadBoutton().setIcon(loadGameLittleIcon);
            
            this.jeuEchec = false;

        } else if ((e.getSource() == main.getVarSideButton() || e.getSource() == main.getVarianteBoutton())) {
            main.setVariante(true);
            main.getClassicSideButton().setBackground(main.getBoutonColor());
            main.getVarSideButton().setBackground(Color.RED);
            main.getLoadSideButton().setBackground(main.getBoutonColor());
            main.getJeuEchecButton().setBackground(main.getBoutonColor());
            main.getStartButton().setBackground(Color.RED);

            main.getLoadSideButton().setEnabled(false);
            main.getStartButton().setEnabled(true);

            main.getClassicBoutton().setIcon(classicGameLittleIcon);
            main.getVarianteBoutton().setIcon(varianteGameIcon);
            main.getLoadBoutton().setIcon(loadGameLittleIcon);
            
            this.jeuEchec = false;

        } else if (e.getSource() == main.getLoadBoutton()) {

            main.setVariante(false);
            main.getClassicSideButton().setBackground(main.getBoutonColor());
            main.getVarSideButton().setBackground(main.getBoutonColor());
            main.getStartButton().setBackground(main.getBoutonColor());
            main.getJeuEchecButton().setBackground(main.getBoutonColor());
            main.getLoadSideButton().setBackground(Color.RED);

            main.getLoadSideButton().setEnabled(true);
            main.getStartButton().setEnabled(false);

            main.getClassicBoutton().setIcon(classicGameLittleIcon);
            main.getVarianteBoutton().setIcon(varianteGameLittleIcon);
            main.getLoadBoutton().setIcon(loadGameIcon);
            
            this.jeuEchec = false;

        } else if (e.getSource() == main.getModeSombreButton() && darkMode == false) {
            // On active le dark Mode

            main.getLeftCenterPanel().setBackground(Color.DARK_GRAY);
            main.getModeSombreButton().setBackground(Color.RED);
            main.getRegleButton().setBackground(Color.GRAY);

            main.getClassicBoutton().setForeground(Color.WHITE);
            main.getVarianteBoutton().setForeground(Color.WHITE);
            main.getLoadBoutton().setForeground(Color.WHITE);

            main.getLeftTopPanel().setBackground(Color.GRAY);
            main.getSideBottomPanel().setBackground(Color.GRAY);
            main.getSideLogoLabel().setBackground(Color.GRAY);

            this.darkMode = true;
            this.jeuEchec = false;

        } else if (e.getSource() == main.getModeSombreButton() && darkMode) {
            // On désactive le darkMode
            main.getLeftCenterPanel().setBackground(Color.WHITE);
            main.getModeSombreButton().setBackground(main.getBoutonColor());
            main.getRegleButton().setBackground(main.getDefaultColor());

            main.getClassicBoutton().setForeground(Color.BLACK);
            main.getVarianteBoutton().setForeground(Color.BLACK);
            main.getLoadBoutton().setForeground(Color.BLACK);

            main.getLeftTopPanel().setBackground(main.getDefaultColor());
            main.getSideBottomPanel().setBackground(main.getDefaultColor());
            main.getSideLogoLabel().setBackground(main.getDefaultColor());

            this.darkMode = false;
            this.jeuEchec = false;

        } else if (e.getSource() == main.getJeuEchecButton()) {
            
            main.getJeuEchecButton().setBackground(Color.RED);
            main.getVarSideButton().setBackground(main.getBoutonColor());
            main.getLoadSideButton().setBackground(main.getBoutonColor());
            main.getClassicSideButton().setBackground(main.getBoutonColor());
            main.getStartButton().setBackground(Color.RED);

            main.getLoadSideButton().setEnabled(false);
            main.getStartButton().setEnabled(true);

            this.jeuEchec = true;
        }
        if (e.getSource() == main.getStartButton() && darkMode && main.getStartButton().isEnabled() && jeuEchec == false) {
            classicStartTrinome("DARK");
        }
        if (e.getSource() == main.getStartButton() && darkMode == false && main.getStartButton().isEnabled() && jeuEchec == false) {
            classicStartTrinome("");
        }
        if (e.getSource() == main.getStartButton() && darkMode && main.getStartButton().isEnabled() && jeuEchec == true) {
            classicStartEchec("DARK");
        }
        if (e.getSource() == main.getStartButton() && darkMode == false && main.getStartButton().isEnabled() && jeuEchec == true) {
            classicStartEchec("");
        }
        if (e.getSource() == main.getLoadSideButton() && darkMode && main.getLoadSideButton().isEnabled() && jeuEchec == false) {
            loadStartTrinome("DARK");
        }
        if (e.getSource() == main.getLoadSideButton() && darkMode == false && main.getLoadSideButton().isEnabled() && jeuEchec == false) {
            loadStartTrinome("");
        }
    }

    public void classicStartTrinome(String mode) {
        long lStartTime = System.currentTimeMillis();

        if ("DARK".equals(mode)) {
            removeMainMenu();
            main.setMainFrame(new mainFrame("DARK", 11, new Color(76, 76, 76), new Color(147, 148, 147)));

        } else {
            removeMainMenu();
            main.setMainFrame(new mainFrame(11, Color.LIGHT_GRAY, Color.DARK_GRAY));
        }
        main.setTrinomeBoard(new trinomeBoard());
        main.setJoueur(new Joueur());
        main.getTrinomeBoard().create_Echequier();
        main.getTrinomeBoard().create_PiecesVerte();
        main.getTrinomeBoard().create_PiecesRouge();
        main.getTrinomeBoard().affichage_echiquier();

        main.getMainFrame().revalidate();
        main.getMainFrame().repaint();

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds pour lancer les méthodes internes: " + output);
    }

    public void loadStartTrinome(String mode) {
        long lStartTime = System.currentTimeMillis();

        if ("DARK".equals(mode)) {
            removeMainMenu();
            main.setMainFrame(new mainFrame("DARK", 11, new Color(76, 76, 76), new Color(147, 148, 147)));
        } else {
            removeMainMenu();
            main.setMainFrame(new mainFrame(11, Color.LIGHT_GRAY, Color.DARK_GRAY));
        }
        main.setTrinomeBoard(new trinomeBoard());
        main.setJoueur(new Joueur());
        main.getTrinomeBoard().chargerEchequier();
        main.getTrinomeBoard().chargerPieceTab();

        main.getMainFrame().revalidate();
        main.getMainFrame().repaint();

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds pour charger les méthodes internes de chargement de partie: " + output);
    }

    public void classicStartEchec(String mode) {
        long lStartTime = System.currentTimeMillis();

        if ("DARK".equals(mode)) {
            removeMainMenu();
            main.setMainFrame(new mainFrame("DARK", 8, new Color(76, 76, 76), new Color(147, 148, 147)));

        } else {
            removeMainMenu();
            main.setMainFrame(new mainFrame(8, new Color(168,77,38), new Color(255,233,182)));
        }
        main.setEchecBoard(new echecBoard());
        main.setJoueur(new Joueur());
        main.getEchecBoard().create_Echequier();
        main.getEchecBoard().createPieceNoire();
        main.getEchecBoard().createPieceBlanche();
        main.getEchecBoard().affichage_echiquier();

        main.getMainFrame().revalidate();
        main.getMainFrame().repaint();

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds pour lancer les méthodes internes version echec: " + output);
    }

    public void removeMainMenu() {
        main.getLeftTopPanel().removeAll();
        main.getLeftCenterPanel().removeAll();
        main.getLeftPanel().removeAll();
        main.getSideBottomPanel().removeAll();
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public void setJeuEchec(boolean jeuEchec) {
        this.jeuEchec = jeuEchec;
    }

    public boolean isJeuEchec() {
        return jeuEchec;
    }

}
