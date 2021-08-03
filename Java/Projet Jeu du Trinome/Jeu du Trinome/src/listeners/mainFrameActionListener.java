/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import jeu_du_trinome.Piece;
import main.main;

/**
 *
 * @author hugop
 */
public class mainFrameActionListener implements ActionListener {

    private JButton boutonDepart, boutonMange, boutonArrive, boutonSelectionne;

    private String boutonDepartString, boutonMangeString, boutonArriveString;

    private int ligne_boutonDepart, colonne_boutonDepart;
    private int ligne_boutonArrive, colonne_boutonArrive;
    private int tour;
    private int pieceMangeeLigne, pieceMangeeColonne;

    private final Piece piece;
    private boolean moveDone;
    private boolean DemiRougeDansZoneRouge, PyrRougeDansZoneRouge, CarRougeDansZoneRouge;

    public mainFrameActionListener() {
        this.piece = new Piece();
        this.tour = 0;
        this.moveDone = false;

        this.DemiRougeDansZoneRouge = false;
        this.PyrRougeDansZoneRouge = false;
        this.CarRougeDansZoneRouge = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == main.getMainFrame().getChangerModeBouton()) {
            main.getJoueur().changerDeMode();
        }

        if (e.getSource() == main.getMainFrame().getPoubelleButton() && main.getMainFrame().getPoubelleButton().isEnabled()) {
            changerDePiece();
        }

        if (e.getSource() == main.getMainFrame().getSaveButton()) {
            main.getJoueur().sauvegarderPartie();
        }

        if (e.getSource() == main.getMainFrame().getAnnulButton() && main.getMainFrame().getAnnulButton().isEnabled()) {
            annulerDernierCoup();
        }

        for (int i = 0; i < main.getMainFrame().getEchiquierMatrixSize(); i++) {
            for (int j = 0; j < main.getMainFrame().getEchiquierMatrixSize(); j++) {

                boutonSelectionne = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                // On gère le calcul du déplacement de la pièce à l'aide de la classe Piece
                if (e.getSource() == boutonSelectionne && boutonSelectionne.getIcon() != null && moveDone == false) {

                    boutonDepart = main.getMainFrame().getEchiquierMatrix().get(i).get(j);
                    ligne_boutonDepart = (int) boutonDepart.getClientProperty("row");
                    colonne_boutonDepart = (int) boutonDepart.getClientProperty("column");
                    if (main.getMainActionListener().isJeuEchec() == false) {
                        boutonDepartString = main.getTrinomeBoard().getEchiquier().get(ligne_boutonDepart).get(colonne_boutonDepart);
                    } else if (main.getMainActionListener().isJeuEchec()) {
                        boutonDepartString = main.getEchecBoard().getEchiquier().get(ligne_boutonDepart).get(colonne_boutonDepart);
                    }

                    testAquiLeTour();
                }

                //Dans un deuxième temps, on gère le déplacement sur le plateau de la pièce
                if (moveDone == true) {
                    main.getMainFrame().getAnnulButton().setEnabled(false);
                    for (int k = 0; k < this.piece.getListeMovePossible().size(); k++) {

                        boutonSelectionne = main.getMainFrame().getEchiquierMatrix().get(i).get(j);
                        boutonArrive = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                        if (e.getSource() == boutonSelectionne && boutonSelectionne == piece.getListeMovePossible().get(k)) {
                            gestionDuDeplacement();
                        }
                    }
                }
            }
        }
    }

    public void gestionDuDeplacement() {

        // On récupère les informations nécessaire du bouton d'arrivée
        ligne_boutonArrive = (int) boutonArrive.getClientProperty("row");
        colonne_boutonArrive = (int) boutonArrive.getClientProperty("column");
        if (main.getMainActionListener().isJeuEchec() == false) {
            boutonArriveString = main.getTrinomeBoard().getEchiquier().get(ligne_boutonArrive).get(colonne_boutonArrive);
        } else if (main.getMainActionListener().isJeuEchec()) {
            boutonArriveString = main.getEchecBoard().getEchiquier().get(ligne_boutonArrive).get(colonne_boutonArrive);
        }

        //On déplace les pièces sur l'échiquier on s'occupe d'abord des pièces spéciales
        if (boutonDepartString.contains("PS")) {
            for (int n = 0; n < piece.getListeMovePossible().size(); n++) {
                if (piece.getListeMovePossible().get(n).getIcon() != null && abs(ligne_boutonDepart - ligne_boutonArrive) != 1 && abs(colonne_boutonDepart - colonne_boutonArrive) != 1) {

                    boutonArrive.setIcon(boutonDepart.getIcon());
                    main.getTrinomeBoard().getEchequier().get(ligne_boutonArrive).set(colonne_boutonArrive, boutonDepartString);

                    //Pertubé pq j'ai besoin de redéclarer 2 variables ici alors que "je pourrais utiliser ligne_boutonArrive et colonne_boutonArrive"
                    pieceMangeeLigne = (int) piece.getListeMovePossible().get(n).getClientProperty("row");
                    pieceMangeeColonne = (int) piece.getListeMovePossible().get(n).getClientProperty("column");

                    boutonMange = main.getMainFrame().getEchiquierMatrix().get(pieceMangeeLigne).get(pieceMangeeColonne);
                    boutonMangeString = main.getTrinomeBoard().getEchequier().get(pieceMangeeLigne).get(pieceMangeeColonne);

                    boutonArriveString = main.getTrinomeBoard().getEchequier().get(pieceMangeeLigne).get(pieceMangeeColonne);

                    main.getTrinomeBoard().getEchequier().get(pieceMangeeLigne).set(pieceMangeeColonne, " 0 ");
                    main.getTrinomeBoard().getEchequier().get(ligne_boutonDepart).set(colonne_boutonDepart, " 0 ");
                    boutonMange.setIcon(null);
                }
            }
        }
        //Test si la pièce à été déplacé dans la zone de l'adversaire
        if (main.getMainActionListener().isJeuEchec() == false) {
            testSiZoneRouge();
        }

        // on s'occupe de remove les pièces des tableaux internes
        testSiPieceMangee();

        //Application effective du déplacement
        applicationDuDéplacement();
        // On reset les paramètres nécessaires
        resetParametres();
        //Test si le joueur a gagné 
        if (main.getMainActionListener().isJeuEchec() == false) {
            main.getJoueur().haveWonTrinome(main.getTrinomeBoard());
        }
        if (main.getMainActionListener().isJeuEchec()) {
            main.getJoueur().haveWonEchec(main.getEchecBoard());
        }

        // on affiche un indicateur de changement de tours + on passe au tour suivant
        alternerTour();

//      System.out.println("Liste piece verte : " + main.getTrinomeBoard().getListe_PieceVerte());
//      System.out.println("Liste piece rouge : " + main.getTrinomeBoard().getListe_PieceRouge());
//      System.out.println("Tableau carre vert" + main.getTrinomeBoard().getCubeTabVert());
//      System.out.println("Tableau pyr vert" + main.getTrinomeBoard().getPyramideTabVert());
//      System.out.println("Tableau demi vert" + main.getTrinomeBoard().getdSphereTabVert());
//      System.out.println("Tableau carre rouge" + main.getTrinomeBoard().getCubeTabRouge());
//      System.out.println("Tableau pyr rouge" + main.getTrinomeBoard().getPyramideTabRouge());
//      System.out.println("Tableau demi rouge" + main.getTrinomeBoard().getdSphereTabRouge());
    }

    public void annulerDernierCoup() {
        //méthode partiellement fonctionnelle
        tour--;
        if (boutonDepartString.contains("PS")) {
            System.out.println("Coup annulé");
            boutonDepart.setIcon(boutonArrive.getIcon());

            //Bug je veux remettre l'icone de la piece mangée par capture en sautant au dessus par la PS mais j'y arrive pas
            // Impossible de recuperer l'icon correcté à partir de la version string de la piece mangée
            // plus override plus bas sur la gestion de l'annulation d'un croque (voir vers ligne 143 "Carré vert"
            System.out.println(boutonMangeString);
            boutonMange.setIcon(main.getMainFrame().getEchiquierMatrix().get(pieceMangeeLigne).get(pieceMangeeColonne).getIcon());

            boutonArrive.setIcon(null);
            moveDone = false;
            main.getMainFrame().getAnnulButton().setEnabled(false);

            main.getTrinomeBoard().getEchequier().get(this.ligne_boutonDepart).set(this.colonne_boutonDepart, this.boutonDepartString);
            main.getTrinomeBoard().getEchequier().get(this.ligne_boutonArrive).set(this.colonne_boutonArrive, " 0 ");
            main.getTrinomeBoard().getEchequier().get(this.pieceMangeeLigne).set(this.pieceMangeeColonne, boutonMangeString);

        } else {
            boutonDepart.setIcon(boutonArrive.getIcon());
            boutonArrive.setIcon(null);
            moveDone = false;
            main.getMainFrame().getAnnulButton().setEnabled(false);
            main.getTrinomeBoard().getEchequier().get(this.ligne_boutonDepart).set(this.colonne_boutonDepart, this.boutonDepartString);
            main.getTrinomeBoard().getEchequier().get(this.ligne_boutonArrive).set(this.colonne_boutonArrive, boutonArriveString);
        }

        if (tour % 2 == 0) {
            main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.RED);
            main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerREDIndicIcon());

        } else {
            main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.GREEN);
            main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerGREENIndicIcon());
        }

        // Si on annule et qu'une pièce a été mangée
        annulSiPieceMangee();

        // Affichage console
        main.getTrinomeBoard().affichage_echiquier();
    }

    public void changerDePiece() {
        long lStartTime = System.currentTimeMillis();
        System.out.println("Pièce mise à la poubelle, recommencez \n");
        boutonDepartString = null;
        moveDone = false;
        boutonDepart = null;
        piece.getListeMovePossible().clear();
        main.getMainFrame().getPoubelleButton().setBackground(main.getBoutonColor());
        piece.repaintBoard();

        long lEndTime = System.currentTimeMillis();
        long output = lEndTime - lStartTime;
        System.out.println("Elapsed time in milliseconds pour changer de pièce: " + output);

    }

    public void applicationDuDéplacement() {
        boutonArrive.setIcon(boutonDepart.getIcon());
        if (main.getMainActionListener().isJeuEchec() == false) {
            main.getTrinomeBoard().getEchequier().get(ligne_boutonArrive).set(colonne_boutonArrive, boutonDepartString);
            main.getTrinomeBoard().getEchequier().get(ligne_boutonDepart).set(colonne_boutonDepart, " 0 ");
        }
        if (main.getMainActionListener().isJeuEchec()) {
            main.getEchecBoard().getEchiquier().get(ligne_boutonArrive).set(colonne_boutonArrive, boutonDepartString);
            main.getEchecBoard().getEchiquier().get(ligne_boutonDepart).set(colonne_boutonDepart, " 0 ");
        }

    }

    public void alternerTour() {
        if (main.getMainActionListener().isJeuEchec() == false) {
            if (tour % 2 == 0) {
                main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.GREEN);
                main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerGREENIndicIcon());
                main.getMainFrame().getConsoleJTArea_Label().setForeground(Color.BLACK);
            } else {
                main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.RED);
                main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerREDIndicIcon());
                main.getMainFrame().getConsoleJTArea_Label().setForeground(Color.WHITE);
            }
        } else if (main.getMainActionListener().isJeuEchec()) {
            if (tour % 2 == 0) {
                main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.BLACK);
                main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerGREENIndicIcon());
                main.getMainFrame().getConsoleJTArea_Label().setForeground(Color.WHITE);
            } else {
                main.getMainFrame().getConsoleJTArea_Label().setBackground(Color.WHITE);
                main.getMainFrame().getConsoleJTArea_Label().setIcon(main.getMainFrame().getPlayerREDIndicIcon());
                main.getMainFrame().getConsoleJTArea_Label().setForeground(Color.BLACK);
            }
        }
        tour++;
    }

    public void resetParametres() {

        boutonDepart.setIcon(null);
        moveDone = false;
        main.getStartButton().setEnabled(false);
        piece.getListeMovePossible().clear();
        piece.getListeFull().clear();
        piece.repaintBoard();
        if (main.getMainActionListener().isJeuEchec() == false) {
            main.getTrinomeBoard().affichage_echiquier();
        }
        if (main.getMainActionListener().isJeuEchec()) {
            main.getEchecBoard().affichage_echiquier();
        }
        main.getMainFrame().getAnnulButton().setEnabled(true);
        main.getMainFrame().getPoubelleButton().setEnabled(false);
        DemiRougeDansZoneRouge = false;
        PyrRougeDansZoneRouge = false;
        CarRougeDansZoneRouge = false;

    }

// On teste si c'est la pièce sélectionnée appartient au joueur auquel c'est au tour de jouer
    public void testAquiLeTour() {
        if (tour % 2 == 0 && this.boutonDepartString.contains("V") || (tour % 2 == 1 && this.boutonDepartString.contains("R")) || tour % 2 == 0 && this.boutonDepartString.contains("N") || (tour % 2 == 1 && this.boutonDepartString.contains("B"))) {
            JOptionPane.showMessageDialog(main.getMainFrame(), "Ce n'est pas à vous de jouer !", "WARNING", JOptionPane.PLAIN_MESSAGE);
            main.getMainFrame().getAnnulButton().setEnabled(false);
            moveDone = false;
            changerDePiece();
        } else if (main.getMainActionListener().isJeuEchec() == false) {    // La piece selectionnées correspond bien au joueur qui doit actuellement jouer

            if (tour % 2 == 0 && this.boutonDepartString.contains("R")) {
                piece.déplacement(1, this.boutonDepartString, main.getMainFrame().isDarkMode(), boutonDepart);
            }
            if (tour % 2 == 1 && this.boutonDepartString.contains("V")) {
                piece.déplacement(2, this.boutonDepartString, main.getMainFrame().isDarkMode(), boutonDepart);
            }
            moveDone = true;
            main.getMainFrame().getPoubelleButton().setEnabled(true);
        } else if (main.getMainActionListener().isJeuEchec()) {    // La piece selectionnées correspond bien au joueur qui doit actuellement jouer
            if (tour % 2 == 0 && this.boutonDepartString.contains("B")) {
                System.out.println("Je lance le calcul d'un déplacement");
                piece.déplacement(1, this.boutonDepartString, main.getMainFrame().isDarkMode(), boutonDepart);
            }
            if (tour % 2 == 1 && this.boutonDepartString.contains("N")) {
                piece.déplacement(2, this.boutonDepartString, main.getMainFrame().isDarkMode(), boutonDepart);
            }
            moveDone = true;
            main.getMainFrame().getPoubelleButton().setEnabled(true);
        }

        if (piece.getListeMovePossible().isEmpty() == false) {
            boutonDepart.setBackground(Color.MAGENTA);
        }
    }

    public void annulSiPieceMangee() {
        if (boutonArriveString.contains("R")) {
            main.getTrinomeBoard().getListe_PieceRouge().add(boutonArriveString);

            if (boutonArriveString.contains("C")) {
                main.getTrinomeBoard().getCubeTabRouge().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getRedCubeIcon());

            } else if (boutonArriveString.contains("P")) {
                main.getTrinomeBoard().getPyramideTabRouge().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getRedPyrIcon());

            } else if (boutonArriveString.contains("D")) {
                main.getTrinomeBoard().getdSphereTabRouge().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getRedSphereIcon());

            }
        } else if (boutonArriveString.contains("V")) {
            main.getTrinomeBoard().getListe_PieceVerte().add(boutonArriveString);

            if (boutonArriveString.contains("C")) {
                main.getTrinomeBoard().getCubeTabVert().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getGreenCubeIcon());

            } else if (boutonArriveString.contains("P")) {
                main.getTrinomeBoard().getPyramideTabVert().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getGreenPyrIcon());

            } else if (boutonArriveString.contains("D")) {
                main.getTrinomeBoard().getdSphereTabVert().add(boutonArriveString);
                boutonArrive.setIcon(main.getTrinomeBoard().getGreenSphereIcon());
            }
        }
    }

    public void testSiPieceMangee() {
        if (main.getMainActionListener().isJeuEchec() == false) {
            if (boutonArriveString.contains("R")) {
                main.getTrinomeBoard().getListe_PieceRouge().remove(boutonArriveString);
                if (boutonArriveString.contains("C")) {
                    main.getTrinomeBoard().getCubeTabRouge().remove(boutonArriveString);
                } else if (boutonArriveString.contains("P")) {
                    main.getTrinomeBoard().getPyramideTabRouge().remove(boutonArriveString);
                } else if (boutonArriveString.contains("D")) {
                    main.getTrinomeBoard().getdSphereTabRouge().remove(boutonArriveString);
                }
            } else if (boutonArriveString.contains("V")) {
                main.getTrinomeBoard().getListe_PieceVerte().remove(boutonArriveString);

                if (boutonArriveString.contains("C")) {
                    main.getTrinomeBoard().getCubeTabVert().remove(boutonArriveString);
                } else if (boutonArriveString.contains("P")) {
                    main.getTrinomeBoard().getPyramideTabVert().remove(boutonArriveString);
                } else if (boutonArriveString.contains("D")) {
                    main.getTrinomeBoard().getdSphereTabVert().remove(boutonArriveString);
                }
            }
        } else if (main.getMainActionListener().isJeuEchec()) {
            if (boutonArriveString.contains("B")) {
                main.getEchecBoard().getListe_PieceBlanche().remove(boutonArriveString);
                if (boutonArriveString.contains("T")) {
                    main.getEchecBoard().getTourBlancheTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("C")) {
                    main.getEchecBoard().getCavaBlancTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("F")) {
                    main.getEchecBoard().getFouBlancTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("P")) {
                    main.getEchecBoard().getPionBlancTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("R")) {
                    main.getEchecBoard().getReineBlancheTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("ROI")) {
                    main.getEchecBoard().getRoiBlancTab().remove(boutonArriveString);
                }
            } else if (boutonArriveString.contains("N")) {
                main.getEchecBoard().getListe_PieceNoire().remove(boutonArriveString);
                if (boutonArriveString.contains("T")) {
                    main.getEchecBoard().getTourNoireTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("C")) {
                    main.getEchecBoard().getCavaNoirTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("F")) {
                    main.getEchecBoard().getFouNoirTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("P")) {
                    main.getEchecBoard().getPionNoirTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("R")) {
                    main.getEchecBoard().getReineNoireTab().remove(boutonArriveString);
                } else if (boutonArriveString.contains("ROI")) {
                    main.getEchecBoard().getRoiNoirTab().remove(boutonArriveString);
                }
            }
        }

    }

    public void testSiZoneRouge() {

        if ((boutonArrive == main.getMainFrame().getEchiquierMatrix().get(0).get(4) || boutonArrive == main.getMainFrame().getEchiquierMatrix().get(0).get(5) || boutonArrive == main.getMainFrame().getEchiquierMatrix().get(0).get(6))
                || (boutonArrive == main.getMainFrame().getEchiquierMatrix().get(10).get(4) || boutonArrive == main.getMainFrame().getEchiquierMatrix().get(10).get(5) || boutonArrive == main.getMainFrame().getEchiquierMatrix().get(10).get(6))) {

            if ((boutonDepartString.contains("CSR2") && piece.isJokerCarreCSR2()) || (boutonDepartString.contains("CSR3") && piece.isJokerCarreCSR3())
                    || (boutonDepartString.contains("CSV2") && piece.isJokerCarreCSV2()) || (boutonDepartString.contains("CSV3") && piece.isJokerCarreCSV3())) {
                // Ne peux pas être immobilisé car joker
                System.out.println("Hello je suis un carré spécial avec un joker, je ne peux pas être immobilisé");
            } else if (boutonDepartString.contains("D") && DemiRougeDansZoneRouge == false) {
                System.out.println("Je suis une demi sphère je suis immobilisé");
                boutonArrive.removeActionListener(main.getMainFrame().getActionListener());
                boutonArrive.setEnabled(false);
                DemiRougeDansZoneRouge = true;
            } else if (boutonDepartString.contains("P") && PyrRougeDansZoneRouge == false) {
                System.out.println("Je suis une demi sphère je suis immobilisé");
                boutonArrive.removeActionListener(main.getMainFrame().getActionListener());
                boutonArrive.setEnabled(false);
                PyrRougeDansZoneRouge = true;
            } else if (boutonDepartString.contains("C") && CarRougeDansZoneRouge == false) {
                System.out.println("Je suis une demi sphère je suis immobilisé");
                boutonArrive.removeActionListener(main.getMainFrame().getActionListener());
                boutonArrive.setEnabled(false);
                CarRougeDansZoneRouge = true;
            }
        }
    }

    public String getBoutonDepartString() {
        return boutonDepartString;
    }

}
