package labyrinthe;

import java.util.ArrayList;
import java.util.Collection;
import personnages.IPersonnage;
import java.io.IOException;

/**
 *
 * @author professor team
 */
public class Labyrinthe extends ArrayList<ISalle> implements ILabyrinthe {

    private IEtage etageCourant = new Etage();

    /**
     * Constructeur pour charger les deux premiers étages et définir l'étage
     * courant
     */
    public Labyrinthe() {

        try {

            Etage etage1 = new Etage(1);
            etage1.charger("etages/etage1N.txt");
            this.addAll(etage1);

            Etage etage2 = new Etage(2);
            etage2.charger("etages/etage2N.txt");
            this.addAll(etage2);

            this.setEtageCourant(etage1);

        } catch (IOException e) {
            System.out.println("Erreur");
            e.printStackTrace();
        }
    }

    /**
     * Retourne la collection des salles accessibles (adjacente) depuis la
     * position courante du personnage
     *
     * @param heros le personnage avec ses déplacements
     * @return une collection contenant les salles adjacentes à la position du
     * personnage
     */
    @Override
    public Collection<ISalle> sallesAccessibles(IPersonnage heros) {
        Collection<ISalle> accessibles = new ArrayList<>();
        ISalle positionCourante = heros.getPosition();

        for (ISalle salle : this) {
            if (positionCourante.estAdjacente(salle)) {
                accessibles.add(salle);
            }
        }

        return accessibles;
    }

    /**
     * Permet d'obtenir la salle qui correspond a l'entrée de l'étage
     *
     * @return la salle entree
     */
    @Override
    public ISalle getEntree() {
        ISalle rep = null;
        for (ISalle salle : this) {
            if (salle.getType() == (ESalle.ENTREE)) {
                rep = salle;
            }
        }
        return rep;
    }

    /**
     * Permet d'obtenir la salle qui correspond a la sortie de l'étage
     *
     * @return la salle sortie
     */
    @Override
    public ISalle getSortie() {
        ISalle rep = null;
        for (ISalle salle : this) {
            if (salle.getType() == (ESalle.SORTIE)) {
                rep = salle;
            }
        }
        return rep;
    }

    /**
     * Permet d'obtenir sur quel étage le personnage se trouv sur la laby
     *
     * @return l'étage en cours
     */
    @Override
    public IEtage getEtageCourant() {
        return this.etageCourant;
    }

    /**
     * Permet de modifier sur quel étage on se trouve
     *
     * @param etage l'étage sur lequel on envoie le perso
     */
    @Override
    public void setEtageCourant(IEtage etage) {
        this.etageCourant = etage;
    }

    /**
     *
     * @param u
     * @param v
     * @return
     */
    @Override
    public Collection<ISalle> chemin(ISalle u, ISalle v) {
        return null;
    }

}
