/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_du_trinome;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jeu_d_echec.echecBoard;
import main.main;

/**
 *
 * @author hugop
 */
public class Joueur {

    private boolean haveWon;
    private Timer timer;

    public Joueur() {
        haveWon = false;
    }
//----------------------------------------------
//              Méthodes   

    public void haveWonTrinome(trinomeBoard echiquier) {
        // Test si le joueur rouge gagne
        if (echiquier.getEchequier().get(0).get(4).contains("CR") && echiquier.getEchequier().get(0).get(5).contains("PR") && echiquier.getEchequier().get(0).get(6).contains("DR")) {
            showJoueurRougeVictoire();
        }
        if (echiquier.getEchequier().get(0).get(5).contains("CR") && echiquier.getEchequier().get(0).get(6).contains("PR") && echiquier.getEchequier().get(0).get(4).contains("DR")) {
            showJoueurRougeVictoire();
        }
        if (echiquier.getEchequier().get(0).get(6).contains("CR") && echiquier.getEchequier().get(0).get(4).contains("PR") && echiquier.getEchequier().get(0).get(5).contains("DR")) {
            showJoueurRougeVictoire();
        } // Test si le joueur vert gagne
        if (echiquier.getEchequier().get(10).get(4).contains("CV") && echiquier.getEchequier().get(10).get(5).contains("PV") && echiquier.getEchequier().get(10).get(6).contains("DV")) {
            showJoueurVertVictoire();
        }
        if (echiquier.getEchequier().get(10).get(5).contains("CV") && echiquier.getEchequier().get(10).get(6).contains("PV") && echiquier.getEchequier().get(10).get(4).contains("DV")) {
            showJoueurVertVictoire();
        }
        if (echiquier.getEchequier().get(10).get(6).contains("CV") && echiquier.getEchequier().get(10).get(4).contains("PV") && echiquier.getEchequier().get(10).get(5).contains("DV")) {
            showJoueurVertVictoire();
        }

        if (echiquier.getCubeTabVert().isEmpty() == true) {
            showJoueurRougeVictoire();
        }
        if (echiquier.getCubeTabRouge().isEmpty() == true) {
            showJoueurVertVictoire();
        }
        if (echiquier.getPyramideTabVert().isEmpty() == true) {
            showJoueurRougeVictoire();
        }
        if (echiquier.getPyramideTabRouge().isEmpty() == true) {
            showJoueurVertVictoire();
        }
        if (echiquier.getdSphereTabVert().isEmpty() == true) {
            showJoueurRougeVictoire();
        }
        if (echiquier.getdSphereTabRouge().isEmpty() == true) {
            showJoueurVertVictoire();
        }
        // truc rigolo
        if (haveWon == true) {
            timer = new Timer(800, (ActionEvent e1) -> {
                for (int i = 0; i < main.getMainFrame().getEchiquierMatrixSize(); i++) {
                    for (int j = 0; j < main.getMainFrame().getEchiquierMatrixSize(); j++) {
                        main.getMainFrame().getEchiquierMatrix().get(i).get(j).setBackground(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                    }
                }
            });
            timer.setRepeats(true);
            timer.start();
        }
    }

    public void haveWonEchec(echecBoard echiquier){
        
    }
    public void sauvegarderPartie() {

        try {
            main.getTrinomeBoard().sauvegarderPieceTab();
            main.getTrinomeBoard().sauvegardeEchequier();
            System.out.println("Sauvegarde effectuée ! \n");
            JOptionPane.showMessageDialog(main.getMainFrame(), "Sauvegarde effectuée !", "WARNING", JOptionPane.PLAIN_MESSAGE);
        } catch (HeadlessException ex) {
            System.out.println("Une erreur a eu lieu lors de la sauvegarde");
        }

    }

    public void changerDeMode() {

        if (JOptionPane.showConfirmDialog(null, "Are you sure? Cela ne sauvegarder pas votre partie", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            long lStartTime = System.currentTimeMillis();
            
            main.getMainFrame().getBoardPanel().removeAll();
            main.getMainFrame().getSideMenuPanel().removeAll();
            main.leftPanel();
            main.sidePanel();
            
            main.getStartFrame().revalidate();

            main.getModeSombreButton().setBackground(main.getBoutonColor());
            main.getStartButton().setEnabled(true);
            main.getClassicSideButton().setEnabled(true);
            main.getVarSideButton().setEnabled(true);
            main.getModeSombreButton().setEnabled(true);
            main.getStartButton().setEnabled(false);
            
            main.getMainActionListener().setDarkMode(false);
            main.getMainActionListener().setJeuEchec(false);
            
            long lEndTime = System.currentTimeMillis();
            long output = lEndTime - lStartTime;
            System.out.println("Elapsed time in milliseconds pour revenir au menu d'accueil: " + output);
        }
    }

    public void showJoueurVertVictoire() {
        System.out.println("Le joueur vert a gagné !");
        haveWon = true;
        JOptionPane.showMessageDialog(main.getMainFrame(), "Le joueur vert a gagné !", "WARNING", JOptionPane.PLAIN_MESSAGE);
    }

    public void showJoueurRougeVictoire() {
        System.out.println("Le joueur rouge a gagné !");
        haveWon = true;
        JOptionPane.showMessageDialog(main.getMainFrame(), "Le joueur rouge a gagné !", "WARNING", JOptionPane.PLAIN_MESSAGE);
    }

    public void setHaveWon(boolean haveWon) {
        this.haveWon = haveWon;
    }

}
