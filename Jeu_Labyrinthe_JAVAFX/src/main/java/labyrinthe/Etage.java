package labyrinthe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author INFO Professors team
 */
public class Etage extends ArrayList<ISalle> implements IEtage {

    private int largeur;
    private int hauteur;
    private int num;

    public Etage() {
        largeur = 40;
        hauteur = 40;
        num = 1;
    }

    public Etage(int id) {
        this.num = id;
    }

    @Override
    public void charger(String file) throws IOException {
        this.clear();
        List<String> lignes = Files.readAllLines(Paths.get(file));
        // dimensions
        String entete = lignes.get(0);
        String[] mots = entete.split(" ");
        largeur = Integer.parseInt(mots[0]);
        hauteur = Integer.parseInt(mots[1]);
        lignes.remove(0);
        // salles
        for (String ligne : lignes) {
            mots = ligne.split(" ");
            ESalle type = convertStringInESalle(mots[2]);
            Salle salle = new Salle(Integer.parseInt(mots[0]), Integer.parseInt(mots[1]), type, this);
            this.add(salle);
        }
    }

    /**
     * Method to convert the type in string into the enum type
     *
     * @param mot string
     * @return enum type
     */
    private ESalle convertStringInESalle(String mot) {

        switch (mot) {

            case "N":
                return ESalle.NORMALE;
            case "E":
                return ESalle.ENTREE;
            case "S":
                return ESalle.SORTIE;
            case "M":
                return ESalle.ESCALIER_MONTANT;
            case "D":
                return ESalle.ESCALIER_DESCENDANT;

            default:
                throw new AssertionError();

        }

    }

    /**
     * Permet d'obtenir la largeur de l'étage
     *
     * @return la largeur int
     */
    @Override
    public int getLargeur() {
        return largeur;
    }

    /**
     * Permet d'obtenir la hauteur de l'étage
     *
     * @return la hauteur int
     */
    @Override
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Permet d'obtenir le numero de l'étage
     *
     * @return le numero de l'étage int
     */
    @Override
    public int getNum() {
        return num;
    }

}
