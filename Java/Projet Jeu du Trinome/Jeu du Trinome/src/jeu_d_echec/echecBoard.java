/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_d_echec;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.main;

/**
 *
 * @author hugop
 */
public class echecBoard {

    private final ArrayList<String> liste_PieceNoire;
    private final ArrayList<Object> liste_PieceBlanche;

    private final ArrayList<ArrayList<String>> echiquier;

    private final ArrayList<String> tourNoireTab;
    private final ArrayList<String> cavaNoirTab;
    private final ArrayList<String> fouNoirTab;
    private final ArrayList<String> pionNoirTab;
    private final ArrayList<String> roiNoirTab;
    private final ArrayList<String> reineNoireTab;

    private final ArrayList<String> tourBlancheTab;
    private final ArrayList<String> cavaBlancTab;
    private final ArrayList<String> fouBlancTab;
    private final ArrayList<String> pionBlancTab;
    private final ArrayList<String> roiBlancTab;
    private final ArrayList<String> reineBlancheTab;

    private JButton boutonGenere;
    private ImageIcon blackPionIcon;
    private ImageIcon blackCavaIcon;
    private ImageIcon blackTour;
    private ImageIcon blackReine;
    private ImageIcon blackFouIcon;
    private ImageIcon blackRoi;
    private ImageIcon whitePionIcon;
    private ImageIcon whiteCavaIcon;
    private ImageIcon whiteTour;
    private ImageIcon whiteReine;
    private ImageIcon whiteFouIcon;
    private ImageIcon whiteRoi;

//----------------------------------------------
//              Constructeur       
    public echecBoard() {

        this.liste_PieceBlanche = new ArrayList<>(16);
        this.liste_PieceNoire = new ArrayList<>(16);

        this.tourNoireTab = new ArrayList<>(2);
        this.cavaNoirTab = new ArrayList<>(2);
        this.fouNoirTab = new ArrayList<>(2);
        this.pionNoirTab = new ArrayList<>(8);
        this.reineNoireTab = new ArrayList<>(1);
        this.roiNoirTab = new ArrayList<>(1);

        this.tourBlancheTab = new ArrayList<>(2);
        this.cavaBlancTab = new ArrayList<>(2);
        this.fouBlancTab = new ArrayList<>(2);
        this.pionBlancTab = new ArrayList<>(8);
        this.reineBlancheTab = new ArrayList<>(1);
        this.roiBlancTab = new ArrayList<>(1);

        this.echiquier = new ArrayList<>(main.getMainFrame().getEchiquierMatrixSize());

    }

//----------------------------------------------
//              Méthodes       
    public void create_Echequier() {
        try {
            for (int i = 0; i < main.getMainFrame().getEchiquierMatrixSize(); i++) {
                ArrayList<String> list = new ArrayList<>();
                echiquier.add(list);
                for (int j = 0; j < main.getMainFrame().getEchiquierMatrixSize(); j++) {
                    list.add(" 0 ");
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {

        }
    }

    public void affichage_echiquier() {
        try {
            for (int i = 0; i < main.getMainFrame().getEchiquierMatrixSize(); i++) {
                System.out.println(echiquier.get(i).toString().replace("[", "").replace("]", "").replace(",", ""));
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }

    public void createPieceBlanche() {
        int compteurPionB = 0;
        int compteurCavaB = 0;
        int compteurFouB = 0;
        int compteurTourB = 0;

        for (int tn = 0; tn < 2; tn++) {
            this.tourBlancheTab.add(tn, "TB" + tn);
            this.cavaBlancTab.add(tn, "CB" + tn);
            this.fouBlancTab.add(tn, "FB" + tn);
        }
        for (int pn = 0; pn < 8; pn++) {
            this.pionBlancTab.add(pn, "PB" + pn);
        }
        this.roiBlancTab.add(0, "KB" + 1);
        this.reineBlancheTab.add(0, "QB" + 1);

        try {
            whitePionIcon = new ImageIcon("white_pion.png");
            whiteCavaIcon = new ImageIcon("white_cava.png");
            whiteFouIcon = new ImageIcon("white_fou.png");
            whiteTour = new ImageIcon("white_tour.png");
            whiteReine = new ImageIcon("white_reine.png");
            whiteRoi = new ImageIcon("white_roi.png");
        } catch (NullPointerException ex) {
            System.out.println("Une des icones des pièces n'existe pas");
        }
        for (int i = 0; i < echiquier.size(); i++) {
            for (int j = 0; j < echiquier.size(); j++) {

                boutonGenere = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                if ((j == 0 || j == 7) && i == 7) {
                    echiquier.get(i).set(j, tourBlancheTab.get(compteurTourB));
                    boutonGenere.setIcon(whiteTour);
                    compteurTourB = compteurTourB + 1;
                }
                if ((j == 1 || j == 6) && i == 7) {

                    echiquier.get(i).set(j, cavaBlancTab.get(compteurCavaB));
                    boutonGenere.setIcon(whiteCavaIcon);

                    compteurCavaB = compteurCavaB + 1;
                }
                if ((j == 2 || j == 5) && i == 7) {
                    echiquier.get(i).set(j, fouBlancTab.get(compteurFouB));
                    boutonGenere.setIcon(whiteFouIcon);

                    compteurFouB = compteurFouB + 1;
                }
                if (j == 3 && i == 7) {
                    echiquier.get(i).set(j, reineBlancheTab.get(0));
                    boutonGenere.setIcon(whiteReine);
                }
                if (j == 4 && i == 7) {
                    echiquier.get(i).set(j, roiBlancTab.get(0));
                    boutonGenere.setIcon(whiteRoi);

                }

// Application des pièces sur la deuxième ligne
                if (j < 8 && i == 6) {
                    echiquier.get(i).set(j, pionBlancTab.get(compteurPionB));
                    boutonGenere.setIcon(whitePionIcon);
                    compteurPionB = compteurPionB + 1;
                }
            }
        }

        // Applications des pieces dans un tableau général d'équipe
        this.liste_PieceBlanche.addAll(tourBlancheTab);
        this.liste_PieceBlanche.addAll(cavaBlancTab);
        this.liste_PieceBlanche.addAll(fouBlancTab);
        this.liste_PieceBlanche.addAll(pionBlancTab);
        this.liste_PieceBlanche.addAll(reineBlancheTab);
        this.liste_PieceBlanche.addAll(roiBlancTab);

    }

    public void createPieceNoire() {
        int compteurPionN = 0;
        int compteurCavaN = 0;
        int compteurFouN = 0;
        int compteurTourN = 0;

        for (int tn = 0; tn < 2; tn++) {
            this.tourNoireTab.add(tn, "TN" + tn);
            this.cavaNoirTab.add(tn, "CN" + tn);
            this.fouNoirTab.add(tn, "FN" + tn);
        }
        for (int pn = 0; pn < 8; pn++) {
            this.pionNoirTab.add(pn, "PN" + pn);
        }
        this.roiNoirTab.add(0, "KN" + 1);
        this.reineNoireTab.add(0, "QN" + 1);

        try {
            blackPionIcon = new ImageIcon("black_pion.png");
            blackCavaIcon = new ImageIcon("black_cava.png");
            blackFouIcon = new ImageIcon("black_fou.png");
            blackTour = new ImageIcon("black_tour.png");
            blackReine = new ImageIcon("black_reine.png");
            blackRoi = new ImageIcon("black_roi.png");
        } catch (NullPointerException ex) {
            System.out.println("Une des icones des pièces n'existe pas");
        }
        for (int i = 0; i < echiquier.size(); i++) {
            for (int j = 0; j < echiquier.size(); j++) {

                boutonGenere = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                if ((j == 0 || j == 7) && i == 0) {
                    echiquier.get(i).set(j, tourNoireTab.get(compteurTourN));
                    boutonGenere.setIcon(blackTour);
                    compteurTourN = compteurTourN + 1;
                }
                if ((j == 1 || j == 6) && i == 0) {

                    echiquier.get(i).set(j, cavaNoirTab.get(compteurCavaN));
                    boutonGenere.setIcon(blackCavaIcon);

                    compteurCavaN = compteurCavaN + 1;
                }
                if ((j == 2 || j == 5) && i == 0) {
                    echiquier.get(i).set(j, fouNoirTab.get(compteurFouN));
                    boutonGenere.setIcon(blackFouIcon);

                    compteurFouN = compteurFouN + 1;
                }
                if (j == 3 && i == 0) {
                    echiquier.get(i).set(j, reineNoireTab.get(0));
                    boutonGenere.setIcon(blackReine);

                }
                if (j == 4 && i == 0) {
                    echiquier.get(i).set(j, roiNoirTab.get(0));
                    boutonGenere.setIcon(blackRoi);

                }
// Application des pièces sur la deuxième ligne
                if (j < 8 && i == 1) {
                    echiquier.get(i).set(j, pionNoirTab.get(compteurPionN));
                    boutonGenere.setIcon(blackPionIcon);
                    compteurPionN = compteurPionN + 1;
                }

            }
        }

        // Applications des pieces dans un tableau général d'équipe
        this.liste_PieceNoire.addAll(tourNoireTab);
        this.liste_PieceNoire.addAll(cavaNoirTab);
        this.liste_PieceNoire.addAll(fouNoirTab);
        this.liste_PieceNoire.addAll(pionNoirTab);
        this.liste_PieceNoire.addAll(reineNoireTab);
        this.liste_PieceNoire.addAll(roiNoirTab);

    }

    public ArrayList<ArrayList<String>> getEchiquier() {
        return echiquier;
    }

    public ArrayList<String> getListe_PieceNoire() {
        return liste_PieceNoire;
    }

    public ArrayList<Object> getListe_PieceBlanche() {
        return liste_PieceBlanche;
    }
    public ArrayList<String> getTourNoireTab() {
        return tourNoireTab;
    }

    public ArrayList<String> getCavaNoirTab() {
        return cavaNoirTab;
    }

    public ArrayList<String> getFouNoirTab() {
        return fouNoirTab;
    }

    public ArrayList<String> getPionNoirTab() {
        return pionNoirTab;
    }

    public ArrayList<String> getRoiNoirTab() {
        return roiNoirTab;
    }

    public ArrayList<String> getReineNoireTab() {
        return reineNoireTab;
    }

    public ArrayList<String> getTourBlancheTab() {
        return tourBlancheTab;
    }

    public ArrayList<String> getCavaBlancTab() {
        return cavaBlancTab;
    }

    public ArrayList<String> getFouBlancTab() {
        return fouBlancTab;
    }

    public ArrayList<String> getPionBlancTab() {
        return pionBlancTab;
    }

    public ArrayList<String> getRoiBlancTab() {
        return roiBlancTab;
    }

    public ArrayList<String> getReineBlancheTab() {
        return reineBlancheTab;
    }
    
    
    
}
