/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu_du_trinome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.main;

/**
 *
 * @author hugop
 */
public class trinomeBoard {

    private final ArrayList<String> liste_PieceRouge;
    private final ArrayList<String> liste_PieceVerte;
    private final ArrayList<String> cubeTabVert;
    private final ArrayList<String> pyramideTabVert;
    private final ArrayList<String> dSphereTabVert;

    private final ArrayList<String> cubeTabRouge;
    private final ArrayList<String> pyramideTabRouge;
    private final ArrayList<String> dSphereTabRouge;

    private final ArrayList<ArrayList<String>> echiquier;

    private final ImageIcon redCubeIcon, redPyrIcon, redSphereIcon;
    private final ImageIcon greenCubeIcon, greenPyrIcon, greenSphereIcon;
    private final ImageIcon greenCubeSpecial, greenPyrSpecial, greenSphereSpecial;
    private final ImageIcon redCubeSpecial, redPyrSpecial, redSphereSpecial;
//----------------------------------------------
//              Constructeur       
    private JButton boutonGenere;

    public trinomeBoard() {

        this.liste_PieceRouge = new ArrayList<>(15);
        this.liste_PieceVerte = new ArrayList<>(15);

        this.cubeTabVert = new ArrayList<>(6);
        this.pyramideTabVert = new ArrayList<>(6);
        this.dSphereTabVert = new ArrayList<>(6);

        this.cubeTabRouge = new ArrayList<>(6);
        this.pyramideTabRouge = new ArrayList<>(6);
        this.dSphereTabRouge = new ArrayList<>(6);

        this.echiquier = new ArrayList<>(11);

        this.redCubeIcon = new ImageIcon("red_cube.png");
        this.redPyrIcon = new ImageIcon("red_pyramid.png");
        this.redSphereIcon = new ImageIcon("red_half_sphere.png");
        this.redSphereSpecial = new ImageIcon("red_half_sphere_special.png");
        this.redPyrSpecial = new ImageIcon("red_pyramid_special.png");
        this.redCubeSpecial = new ImageIcon("red_cube_special.png");
        this.greenCubeIcon = new ImageIcon("green_cube.png");
        this.greenPyrIcon = new ImageIcon("green_pyramid.png");
        this.greenSphereIcon = new ImageIcon("green_half_sphere.png");
        this.greenSphereSpecial = new ImageIcon("green_half_sphere_special.png");
        this.greenPyrSpecial = new ImageIcon("green_pyramid_special.png");
        this.greenCubeSpecial = new ImageIcon("green_cube_special.png");

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

    public void create_PiecesVerte() {
        try {
            int compteur = 0;
            int compteurPyra = 0;
            int compteurCube = 0;
            int compteurDemiSphère = 0;

            for (int create = 0; create < 6; create++) {
                // A chaque boucle for, je génère 2 pièces, un carré et une pyramide
                compteur = compteur + 2;

                // On crée également les pièces à un niveau global de la classe
                this.cubeTabVert.add(create, "CV" + create);
                this.pyramideTabVert.add(create, "PV" + create);
            }

//            System.out.println("Cube List: " + cubeTabVert);
//            System.out.println("Pyramide List: " + pyramideTabVert);
            for (int create = 0; create < 3; create++) {
                // A chaque for, je génére 1 pièce, une demie-sphère
                compteur = compteur + 1;
                this.dSphereTabVert.add(create, "DV" + create);
            }

//            System.out.println("DS List: " + dSphereTabVert);
            //Application des pièces sur la première ligne
            int cpt = 0;
            for (int i = 0; i < echiquier.size(); i++) {
                for (int j = 0; j < echiquier.size(); j++) {

                    boutonGenere = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                    if ((j < 2 || j == 9 || j == 10) && i == 0) {

                        echiquier.get(i).set(j, pyramideTabVert.get(compteurPyra));
                        boutonGenere.setIcon(greenPyrIcon);

                        if (main.isVariante() == true && (j == 0 || j == 10)) {
                            pyramideTabVert.set(cpt, "PSV" + cpt);
                            cpt = 3;
                            boutonGenere.setIcon(greenPyrSpecial);
                            echiquier.get(i).set(j, pyramideTabVert.get(compteurPyra));
                        }
                        compteurPyra = compteurPyra + 1;
                    } else if ((j == 2 || j == 8) && i == 0) {

                        echiquier.get(i).set(j, cubeTabVert.get(compteurCube));
                        boutonGenere.setIcon(greenCubeIcon);

                        compteurCube = compteurCube + 1;
                    } else if ((j == 4 || j == 5 || j == 6) && i == 0) {

                        echiquier.get(i).set(j, dSphereTabVert.get(compteurDemiSphère));
                        boutonGenere.setIcon(greenSphereIcon);
                        // remplacement par une speciale si nécessaire
                        if (main.isVariante() == true && j == 5) {
                            dSphereTabVert.set(1, "DSV1");
                            boutonGenere.setIcon(greenSphereSpecial);
                            echiquier.get(i).set(j, dSphereTabVert.get(compteurDemiSphère));

                        }

                        compteurDemiSphère = compteurDemiSphère + 1;

                    } // Application des pièces sur la deuxième ligne
                    else if ((j == 0 || j == 10) && i == 1) {

                        echiquier.get(i).set(j, pyramideTabVert.get(compteurPyra));
                        boutonGenere.setIcon(greenPyrIcon);
                        compteurPyra = compteurPyra + 1;
                    } else if ((j == 1 || j == 9) && i == 1) {

                        echiquier.get(i).set(j, cubeTabVert.get(compteurCube));
                        boutonGenere.setIcon(greenCubeIcon);
                        if (main.isVariante() == true && (j == 1 || j == 9)) {
                            cubeTabVert.set(cpt - 1, "CSV" + (cpt - 1));
                            cpt = 4;
                            boutonGenere.setIcon(greenCubeSpecial);
                            echiquier.get(i).set(j, cubeTabVert.get(compteurCube));
                        }
                        compteurCube = compteurCube + 1;
                    } // Application des pièces sur la troisème ligne
                    else if ((j == 0 || j == 10) && i == 2) {

                        echiquier.get(i).set(j, cubeTabVert.get(compteurCube));
                        boutonGenere.setIcon(greenCubeIcon);
                        compteurCube = compteurCube + 1;
                    }
                }
            }

//            Test si le joueur rouge gagne dans la zone verte de l'adversaire
//            echiquier.get(10).set(4, "CV1");
//            echiquier.get(10).set(5, "PV1");
//            echiquier.get(10).set(6, "DV1");
            // Applications des pieces dans un tableau général d'équipe
            this.liste_PieceVerte.addAll(cubeTabVert);
            this.liste_PieceVerte.addAll(pyramideTabVert);
            this.liste_PieceVerte.addAll(dSphereTabVert);

//            Vérifier si le joueur mange tous les pions d'un même type
//            this.cubeTabVert.clear();
//            System.out.println("liste_PieceVerte List: " + liste_PieceVerte + "\n");
        } catch (NullPointerException ex) {
            System.out.println("Une des icones des pièces n'existe pas");
        }
    }

    public void create_PiecesRouge() {
        try {
            int compteur = 0;
            int compteurPyra = 0;
            int compteurCube = 0;
            int compteurDemiSphère = 0;

            for (int create = 0; create < 6; create++) {

                // A chaque boucle for, je génère 2 pièces, un carré et une pyramide
                compteur = compteur + 2;
                this.cubeTabRouge.add(create, "CR" + create);
                this.pyramideTabRouge.add(create, "PR" + create);
            }

//            System.out.println("Cube List: " + cubeTabRouge);
//            System.out.println("Pyramide List: " + pyramideTabRouge);
            for (int create = 0; create < 3; create++) {
                // A chaque for, je génére 1 pièce, une demie-sphère
                compteur = compteur + 1;
                this.dSphereTabRouge.add(create, "DR" + create);
            }

//            System.out.println("DS List: " + dSphereTabRouge);
            int cpt = 2;
            for (int i = 0; i < echiquier.size(); i++) {
                for (int j = 0; j < echiquier.size(); j++) {

                    boutonGenere = main.getMainFrame().getEchiquierMatrix().get(i).get(j);

                    if ((j < 2 || j == 9 || j == 10) && i == 10) {
                        echiquier.get(i).set(j, pyramideTabRouge.get(compteurPyra));
                        boutonGenere.setIcon(redPyrIcon);
                        if (main.isVariante() == true && (j == 0 || j == 10)) {
                            pyramideTabRouge.set(cpt - 1, "PSR" + (cpt - 1));
                            cpt = 6;
                            boutonGenere.setIcon(redPyrSpecial);
                            echiquier.get(i).set(j, pyramideTabRouge.get(compteurPyra));
                        }
                        compteurPyra = compteurPyra + 1;
                    } else if ((j == 2 || j == 8) && i == 10) {

                        echiquier.get(i).set(j, cubeTabRouge.get(compteurCube));
                        boutonGenere.setIcon(redCubeIcon);

                        compteurCube = compteurCube + 1;
                    } else if ((j == 4 || j == 5 || j == 6) && i == 10) {
                        echiquier.get(i).set(j, dSphereTabRouge.get(compteurDemiSphère));
                        boutonGenere.setIcon(redSphereIcon);

                        //Ajout d'une speciale si necessaire
                        if (main.isVariante() == true && j == 5) {
                            dSphereTabRouge.set(1, "DSR1");
                            boutonGenere.setIcon(redSphereSpecial);
                            echiquier.get(i).set(j, dSphereTabRouge.get(compteurDemiSphère));
                        }
                        compteurDemiSphère = compteurDemiSphère + 1;
                    } // Application des pièces sur la deuxième ligne
                    else if ((j == 0 || j == 10) && i == 9) {
                        echiquier.get(i).set(j, pyramideTabRouge.get(compteurPyra));
                        boutonGenere.setIcon(redPyrIcon);
                        compteurPyra = compteurPyra + 1;
                    } else if ((j == 1 || j == 9) && i == 9) {
                        echiquier.get(i).set(j, cubeTabRouge.get(compteurCube));
                        boutonGenere.setIcon(redCubeIcon);

                        if (main.isVariante() == true && (j == 1 || j == 9)) {
                            cubeTabRouge.set(cpt, "CSR" + cpt);
                            cpt = 3;
                            boutonGenere.setIcon(redCubeSpecial);
                            echiquier.get(i).set(j, cubeTabRouge.get(compteurCube));

                        }
                        compteurCube = compteurCube + 1;
                    } // Application des pièces sur la troisème ligne
                    else if ((j == 0 || j == 10) && i == 8) {
                        echiquier.get(i).set(j, cubeTabRouge.get(compteurCube));
                        boutonGenere.setIcon(redCubeIcon);
                        compteurCube = compteurCube + 1;
                    }
                }
            }

//            Test si le joueur rouge gagne dans la zone verte de l'adversaire
//            echiquier.get(0).set(4, "CR1");
//            echiquier.get(0).set(5, "PR1");
//            echiquier.get(0).set(6, "DR1");
            // Applications des pieces dans un tableau général d'équipe
            this.liste_PieceRouge.addAll(cubeTabRouge);
            this.liste_PieceRouge.addAll(pyramideTabRouge);
            this.liste_PieceRouge.addAll(dSphereTabRouge);

//            Vérifier si le joueur mange tous les pions d'un même type
//            this.cubeTabRouge.clear();
        } catch (NullPointerException ex) {
            System.out.println("Une des icones des pièces n'existe pas");
        }
    }

    public void sauvegarderPieceTab() {
        try ( FileWriter fich = new FileWriter("piecesTabs.txt")) {
            fich.write(this.cubeTabVert + "\n");
            fich.write(this.cubeTabRouge + "\n");
            fich.write(this.pyramideTabVert + "\n");
            fich.write(this.pyramideTabRouge + "\n");
            fich.write(this.dSphereTabVert + "\n");
            fich.write(this.dSphereTabRouge + "\n");

            fich.write(this.liste_PieceVerte + "\n");
            fich.write(this.liste_PieceRouge + "\n");

            System.out.println("Sauvegarde des pièces effectuée avec succès !");

        } catch (IOException ex) {
            throw new UnsupportedOperationException("Les pièces n'ont pas pu être sauvegardées");
        }
    }

    public void sauvegardeEchequier() {
        //Look for StringBuilder class to avoid the += which are highly ignificient according to https://www.comparitech.com/net-admin/improve-java-application-performance/
        try ( FileWriter fich = new FileWriter("echiquier.txt")) {
            String spacing = "";
            for (int i = 0; i < this.echiquier.size(); i++) {
                for (int j = 0; j < this.echiquier.size(); j++) {
                    spacing += this.echiquier.get(i).get(j) + "\n";
                }
                spacing += ":\n";
            }
            fich.write(spacing + "\n");

            System.out.println("Sauvegarde de l'échiquier effectuée avec succès !");
        } catch (IOException ex) {
            throw new UnsupportedOperationException("L'échiquier n’a pas pu être sauvegardé");

        }
    }

    public void chargerPieceTab() {
        ArrayList<ArrayList<String>> allList = new ArrayList<>();
        allList.add(this.cubeTabVert);
        allList.add(this.cubeTabRouge);
        allList.add(this.pyramideTabVert);
        allList.add(this.pyramideTabRouge);
        allList.add(this.dSphereTabVert);
        allList.add(this.dSphereTabRouge);
        allList.add(this.liste_PieceVerte);
        allList.add(this.liste_PieceRouge);
        try ( FileReader fich = new FileReader("piecesTabs.txt")) {
            BufferedReader br = new BufferedReader(fich);
            String line;
            line = br.readLine();
            int cpt = 0;

            while (line != null) {
                String[] element;
                line = line.replace("[", "").replace("]", "").replace(" ", "");
                element = line.split(",");
                allList.get(cpt).clear();

                allList.get(cpt).addAll(Arrays.asList(element));

                line = br.readLine();
                cpt++;
            }
        } catch (IOException ex) {
            System.out.println("Le chargement des pièces n’a pas pu être effectué");
        }
        System.out.println("Chargement des pièces effectué !");
    }

    public void chargerEchequier() {
        try ( FileReader fich = new FileReader("echiquier.txt")) {
            int lineCpt = 0;
            int columnCpt = 0;
            BufferedReader br = new BufferedReader(fich);
            String line;
            line = br.readLine();
            while (!line.equals("")) {
                ArrayList<String> temp = new ArrayList<>(11);
                while (!line.equals(":")) {
                    temp.add(line);
                    if (line.contains("CR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redCubeIcon);
                    } else if (line.contains("PR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redPyrIcon);
                    } else if (line.contains("DR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redSphereIcon);
                    } else if (line.contains("DSR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redSphereSpecial);
                    } else if (line.contains("CV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenCubeIcon);
                    } else if (line.contains("PV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenPyrIcon);
                    } else if (line.contains("DV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenSphereIcon);
                    } else if (line.contains("DSV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenSphereSpecial);
                    } else if (line.contains("PSR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redPyrSpecial);
                    } else if (line.contains("PSV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenPyrSpecial);
                    } else if (line.contains("CSR")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(redCubeSpecial);
                    } else if (line.contains("CSV")) {
                        main.getMainFrame().getEchiquierMatrix().get(lineCpt).get(columnCpt).setIcon(greenCubeSpecial);
                    }
                    line = br.readLine();
                    columnCpt = columnCpt + 1;
                }
                lineCpt = lineCpt + 1;
                columnCpt = 0;

                line = br.readLine();
                this.echiquier.add(temp);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Le fichier n'a pas été trouvé");
        } catch (IOException ex) {
            System.out.println("L'échiquier n'a pas pu être chargé");
        } catch (NullPointerException ex) {
            System.out.println("Une des icones des pièces n'existe pas");
        }
        System.out.println("Chargement échiquier effectué !");
    }

    //Getters
    public ArrayList<String> getListe_PieceVerte() {
        return liste_PieceVerte;
    }

    public ArrayList<String> getListe_PieceRouge() {
        return liste_PieceRouge;
    }

    public ArrayList<ArrayList<String>> getEchequier() {
        return echiquier;
    }

    public ArrayList<String> getCubeTabVert() {
        return cubeTabVert;
    }

    public ArrayList<String> getPyramideTabVert() {
        return pyramideTabVert;
    }

    public ArrayList<String> getdSphereTabVert() {
        return dSphereTabVert;
    }

    public ArrayList<String> getCubeTabRouge() {
        return cubeTabRouge;
    }

    public ArrayList<String> getPyramideTabRouge() {
        return pyramideTabRouge;
    }

    public ArrayList<String> getdSphereTabRouge() {
        return dSphereTabRouge;
    }

    public ArrayList<ArrayList<String>> getEchiquier() {
        return echiquier;
    }

    public ImageIcon getRedCubeIcon() {
        return redCubeIcon;
    }

    public ImageIcon getRedPyrIcon() {
        return redPyrIcon;
    }

    public ImageIcon getRedSphereIcon() {
        return redSphereIcon;
    }

    public ImageIcon getGreenCubeIcon() {
        return greenCubeIcon;
    }

    public ImageIcon getGreenPyrIcon() {
        return greenPyrIcon;
    }

    public ImageIcon getGreenSphereIcon() {
        return greenSphereIcon;
    }

    public ImageIcon getGreenSphereSpecial() {
        return greenSphereSpecial;
    }

    public ImageIcon getRedSphereSpecial() {
        return redSphereSpecial;
    }

    public ImageIcon getGreenCubeSpecial() {
        return greenCubeSpecial;
    }

    public ImageIcon getRedCubeSpecial() {
        return redCubeSpecial;
    }

    public ImageIcon getGreenPyrSpecial() {
        return greenPyrSpecial;
    }

    public ImageIcon getRedPyrSpecial() {
        return redPyrSpecial;
    }

}
