/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_du_trinome;

import java.awt.Color;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import main.main;

/**
 *
 * @author hugop
 */
public class Piece {

//----------------------------------------------
//              Attributs   
    private int ligne;
    private int colone;
    private final ArrayList<JButton> listeMovePossible;
    private final String piece_mangee;
    private JButton landingButton;
    private ArrayList<JButton> listeFull;
//----------------------------------------------
//              Constructeur   
    private String carreSpecial;
    private boolean jokerCarreCSR2;
    private boolean jokerCarreCSR3;
    private boolean jokerCarreCSV2;
    private boolean jokerCarreCSV3;
    private boolean isShowed;

    public Piece() {
        this.listeMovePossible = new ArrayList<>();
        this.listeFull = new ArrayList<>();
        this.piece_mangee = "";
        this.listeFull = new ArrayList<>();
        jokerCarreCSR2 = false;
        jokerCarreCSR3 = false;
        jokerCarreCSV2 = false;
        jokerCarreCSV3 = false;
        isShowed = false;

    }
//----------------------------------------------
//              Méthodes       

    public void déplacement(int equipe, String piece_wanted, boolean darkMode, JButton boutonDepart) {

// on recup les coords de la piece que l'on souhaite déplacer
        for (int local_ligne = 0; local_ligne < main.getMainFrame().getEchiquierMatrixSize(); local_ligne++) {
            for (int local_colonne = 0; local_colonne < main.getMainFrame().getEchiquierMatrixSize(); local_colonne++) {
                if ((main.getMainActionListener().isJeuEchec() && piece_wanted.equals(main.getEchecBoard().getEchiquier().get(local_ligne).get(local_colonne)))
                        || (main.getMainActionListener().isJeuEchec() == false && piece_wanted.equals(main.getTrinomeBoard().getEchiquier().get(local_ligne).get(local_colonne)))) {
                    this.ligne = local_ligne;
                    this.colone = local_colonne;
                }
            }
        }
        System.out.println("Les coordonnées de " + piece_wanted + " sont : (" + ligne + "," + colone + ")");
// On calcule tous les déplacements possibles pour une pice donnée
        for (int row = 0; row < main.getMainFrame().getEchiquierMatrixSize(); row++) {
            for (int column = 0; column < main.getMainFrame().getEchiquierMatrixSize(); column++) {
                int potentialLine = (int) main.getMainFrame().getEchiquierMatrix().get(row).get(column).getClientProperty("row");
                int potentialColumn = (int) main.getMainFrame().getEchiquierMatrix().get(row).get(column).getClientProperty("column");
                // On teste si le bouton du double for est dans l'équipe rouge, si elle n'est y est pas, alors le déplacement vers cette case est considéré comme possible (les pièces d'une meme equipe ne se mangent
                // pas entre elles
                if (main.getMainActionListener().isJeuEchec() == false) {
                    if (equipe == 1 && main.getTrinomeBoard().getEchiquier().get(potentialLine).get(potentialColumn).contains("R") == false) {
                        calculDeplacementTrinome(potentialLine, potentialColumn, piece_wanted, equipe, boutonDepart);
                    } else if (equipe == 2 && main.getTrinomeBoard().getEchiquier().get(potentialLine).get(potentialColumn).contains("V") == false) {
                        calculDeplacementTrinome(potentialLine, potentialColumn, piece_wanted, equipe, boutonDepart);
                    }
                }
                if (main.getMainActionListener().isJeuEchec()) {
                    if (equipe == 1 && main.getEchecBoard().getEchiquier().get(potentialLine).get(potentialColumn).contains("B") == false) {
                        calculDeplacementEchec(potentialLine, potentialColumn, piece_wanted, equipe, boutonDepart);
                    } else if (equipe == 2 && main.getEchecBoard().getEchiquier().get(potentialLine).get(potentialColumn).contains("N") == false) {
                        calculDeplacementEchec(potentialLine, potentialColumn, piece_wanted, equipe, boutonDepart);
                    }
                }
            }
        }
        if (piece_wanted.contains("S") && (jokerCarreCSR2 == true || jokerCarreCSR3 == true || jokerCarreCSV2 == true || jokerCarreCSV3 == true) && isShowed == false) {
            JOptionPane.showMessageDialog(main.getMainFrame(), "Le joker de votre cube" + carreSpecial + "spécial est activé", "WARNING", JOptionPane.PLAIN_MESSAGE);
            isShowed = true;
        }
        if (this.listeMovePossible.isEmpty()) {
            if (piece_wanted.contains("DS")) {
                this.listeMovePossible.addAll(listeFull);
            } else {
                // Pas de move possible après vérification si variante, alors on indique que la pièce ne peut pas se déplacer
                main.getMainFrame().getPoubelleButton().setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(main.getMainFrame(), "Aucun déplacement possible pour cette pièce, cliquez sur le bouton approprié", "WARNING", JOptionPane.PLAIN_MESSAGE);
            }
        }

        for (int k = 0; k < this.listeMovePossible.size(); k++) {
            main.getMainFrame().getEchiquierMatrix().get((int) this.listeMovePossible.get(k).getClientProperty("row")).get((int) this.listeMovePossible.get(k).getClientProperty("column")).setBackground(Color.YELLOW);
            System.out.println("Ligne du move possible : " + listeMovePossible.get(k).getClientProperty("row") + " et Colonne du move possible : " + listeMovePossible.get(k).getClientProperty("column") + "\n");
        }
    }

    public void calculDeplacementTrinome(int potentialLine, int potentialColumn, String piece_wanted, int equipe, JButton boutonDepart) {
        int resultat_ligne = abs(potentialLine - ligne);
        int resultat_colone = abs(potentialColumn - colone);

        if (piece_wanted.contains("C") && ((resultat_ligne == 1 && resultat_colone == 0) || (resultat_ligne == 0 && resultat_colone == 1))) {
            // Calcul du déplacement d'un carré
            carreSpecial = calculCarre(boutonDepart, potentialLine, potentialColumn, piece_wanted);
        }
        if (piece_wanted.contains("P") && (resultat_ligne == 1 && resultat_colone == 1)) {
            // Calcul du déplacement d'une pyramide
            calculPyr(potentialLine, potentialColumn, piece_wanted);
        }
        if (piece_wanted.contains("D") && main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).getIcon() == null) {
            if ((resultat_ligne == 2 && resultat_colone == 2) || (resultat_ligne == 2 && resultat_colone == 0) || (resultat_ligne == 0 && resultat_colone == 2)) {
                this.listeFull.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
                this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            }
            calculDemiSphere(piece_wanted);
        }
    }

    public void calculDeplacementEchec(int potentialLine, int potentialColumn, String piece_wanted, int equipe, JButton boutonDepart) {
        int resultat_ligne = abs(potentialLine - ligne);
        int resultat_colone = abs(potentialColumn - colone);

        if (piece_wanted.contains("P") && ((ligne != 1 && equipe == 2) || (ligne != 6 && equipe == 1)) && (resultat_ligne == 1 && resultat_colone == 0) && ((equipe == 1 && potentialLine < ligne) || (equipe == 2 && potentialLine > ligne))) {
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            System.out.println("Hello pion");
        } else if (piece_wanted.contains("P") && ((ligne == 1 && equipe == 2) || (ligne == 6 && equipe == 1)) && ((resultat_ligne == 2 && resultat_colone == 0) || (resultat_ligne == 1 && resultat_colone == 0))) {
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            System.out.println("Hello pion de départ");
        }
        if (piece_wanted.contains("F") && (resultat_ligne == resultat_colone)) {
            
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            System.out.println("Hello fou");
            
            if (main.getEchecBoard().getEchiquier().get(potentialLine).get(potentialColumn).contains("N") && piece_wanted.contains("B")) {
                System.out.println("Je suis une piece blanche qui rencontre une piece noir dans le calcul des moves");
                try{
                    this.listeMovePossible.remove(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
                }catch(IndexOutOfBoundsException e){
                    
                }
            }

        }
        if (piece_wanted.contains("C") && (resultat_ligne == 2 && resultat_colone == 1 || (resultat_ligne == 1 && resultat_colone == 2))) {
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            System.out.println("Hello cavaz");
        }
        if (piece_wanted.contains("K") && (resultat_ligne == 1 && resultat_colone == 1 || (resultat_ligne == 1 && resultat_colone == 0) || (resultat_ligne == 0 && resultat_colone == 1))) {
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            System.out.println("Hello roi");
        }
        if (piece_wanted.contains("Q") && (resultat_ligne == resultat_colone || (resultat_ligne < 8 && resultat_colone == 0) || (resultat_ligne == 0 && resultat_colone < 8))) {
            System.out.println("Hello reine");
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            /// Pas complet, gérer le cas ou la reine rencontre une pièce de son équipe à une case de lui ou plus...
        }
        if (piece_wanted.contains("T") && ((resultat_ligne < 8 && resultat_colone == 0) || (resultat_ligne == 0 && resultat_colone < 8))) {
            System.out.println("Hello tour");
            this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
            main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            /// Pas complet, gérer le cas ou la tour rencontre une pièce de son équipe à une case de lui ou plus...
        }

    }

    public void calculDemiSphere(String piece_wanted) {

        if (!this.listeMovePossible.isEmpty() && piece_wanted.contains("S")) {

            for (int k = 0; k < this.listeMovePossible.size(); k++) {
                if (piece_wanted.contains("SR") && (int) this.listeMovePossible.get(k).getClientProperty("row") > ligne) {
                    this.listeFull.add(this.listeMovePossible.get(k));
                    this.listeMovePossible.remove(k);
                }
                if (piece_wanted.contains("SV") && (int) this.listeMovePossible.get(k).getClientProperty("row") < ligne) {
                    this.listeFull.add(this.listeMovePossible.get(k));
                    this.listeMovePossible.remove(k);
                }
            }
        }
    }

    public String calculCarre(JButton boutonDepart, int potentialLine, int potentialColumn, String piece_wanted) {

        // Tous les carrés se déplacent comme ca
        this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
        main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);
        // Déplacement des carrés spéciaux rouges
        try {
            if (piece_wanted.contains("S")) {
                if (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") - 1).get((int) boutonDepart.getClientProperty("column") - 1).contains("R") && ((main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") - 2).get((int) boutonDepart.getClientProperty("column") - 2).contains("R")))) {
                    jokerCarreCSR3 = true;
                    isShowed = false;
                    return (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row")).get((int) boutonDepart.getClientProperty("column")));
                }
                if (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") - 1).get((int) boutonDepart.getClientProperty("column") + 1).contains("R") && ((main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") - 2).get((int) boutonDepart.getClientProperty("column") + 2).contains("R")))) {
                    jokerCarreCSR2 = true;
                    isShowed = false;
                    return (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row")).get((int) boutonDepart.getClientProperty("column")));
                }

                // Déplacement des carrés spéciaux vert
                if (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") + 1).get((int) boutonDepart.getClientProperty("column") + 1).contains("V") && ((main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") + 2).get((int) boutonDepart.getClientProperty("column") + 2).contains("V")))) {
                    jokerCarreCSV2 = true;
                    isShowed = false;
                    return (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row")).get((int) boutonDepart.getClientProperty("column")));
                }
                if (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") + 1).get((int) boutonDepart.getClientProperty("column") - 1).contains("V") && ((main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row") + 2).get((int) boutonDepart.getClientProperty("row") - 2).contains("V")))) {
                    jokerCarreCSV3 = true;
                    isShowed = false;
                    return (main.getTrinomeBoard().getEchequier().get((int) boutonDepart.getClientProperty("row")).get((int) boutonDepart.getClientProperty("column")));
                }
            }
        } catch (IndexOutOfBoundsException e) {
        }
        return ("");
    }

    public void calculPyr(int potentialLine, int potentialColumn, String piece_wanted) {
        // Calcul du déplacement d'une pyramide
        this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn));
        main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).setBackground(Color.YELLOW);

        // + ajout du déplacement spécial d'une pyramide si elle est spéciale
        if (piece_wanted.contains("S") && main.getMainFrame().getEchiquierMatrix().get(potentialLine).get(potentialColumn).getIcon() != null) {

            if (potentialLine - ligne == -1 && potentialColumn - colone == - 1 && (piece_wanted.contains("R") && main.getTrinomeBoard().getEchequier().get(potentialLine - 1).get(potentialColumn - 1).contains("R") == false || piece_wanted.contains("V") && main.getTrinomeBoard().getEchequier().get(potentialLine - 1).get(potentialColumn - 1).contains("V") == false)) {
                this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine - 1).get(potentialColumn - 1));
            }
            if (potentialLine - ligne == +1 && potentialColumn - colone == +1 && (piece_wanted.contains("R") && main.getTrinomeBoard().getEchequier().get(potentialLine + 1).get(potentialColumn + 1).contains("R") == false || piece_wanted.contains("V") && main.getTrinomeBoard().getEchequier().get(potentialLine + 1).get(potentialColumn + 1).contains("V") == false)) {
                this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine + 1).get(potentialColumn + 1));
            }
            if (potentialLine - ligne == -1 && potentialColumn - colone == +1 && (piece_wanted.contains("R") && main.getTrinomeBoard().getEchequier().get(potentialLine - 1).get(potentialColumn + 1).contains("R") == false || piece_wanted.contains("V") && main.getTrinomeBoard().getEchequier().get(potentialLine - 1).get(potentialColumn + 1).contains("V") == false)) {
                this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine - 1).get(potentialColumn + 1));
            }
            if (potentialLine - ligne == +1 && potentialColumn - colone == - 1 && (piece_wanted.contains("R") && main.getTrinomeBoard().getEchequier().get(potentialLine + 1).get(potentialColumn - 1).contains("R") == false || piece_wanted.contains("V") && main.getTrinomeBoard().getEchequier().get(potentialLine + 1).get(potentialColumn - 1).contains("V") == false)) {
                this.listeMovePossible.add(main.getMainFrame().getEchiquierMatrix().get(potentialLine + 1).get(potentialColumn - 1));
            }
        }
    }

    public void repaintBoard() {
        for (int c = 0; c < main.getMainFrame().getEchiquierMatrixSize(); c++) {
            for (int d = 0; d < main.getMainFrame().getEchiquierMatrixSize(); d++) {
                if ((int) main.getMainFrame().getEchiquierMatrix().get(c).get(d).getClientProperty("row") % 2 == (int) main.getMainFrame().getEchiquierMatrix().get(c).get(d).getClientProperty("column") % 2) {
                    main.getMainFrame().getEchiquierMatrix().get(c).get(d).setBackground(main.getMainFrame().getEchiquierMatrixSize_PairColor());
                } else {
                    main.getMainFrame().getEchiquierMatrix().get(c).get(d).setBackground(main.getMainFrame().getEchiquierMatrixSize_ImpairColor());
                }
                if (main.getMainActionListener().isJeuEchec()) {
                    main.getMainFrame().getEchiquierMatrix().get(c).get(d).setBorder(null);
                }
            }
        }
        if (main.getMainActionListener().isJeuEchec() == false) {
            main.getMainFrame().colorZoneRouge();
        }
    }

    public ArrayList<JButton> getListeMovePossible() {
        return listeMovePossible;
    }

    public String getPiece_mangee() {
        return piece_mangee;
    }

    public JButton getLandingButton() {
        return landingButton;
    }

    public ArrayList<JButton> getListeFull() {
        return listeFull;
    }

    public boolean isJokerCarreCSR3() {
        return jokerCarreCSR3;
    }

    public boolean isJokerCarreCSR2() {
        return jokerCarreCSR2;
    }

    public boolean isJokerCarreCSV2() {
        return jokerCarreCSV2;
    }

    public boolean isJokerCarreCSV3() {
        return jokerCarreCSV3;
    }

    public void setCarreSpecial(String carreSpecial) {
        this.carreSpecial = carreSpecial;
    }

}
