/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import java.util.ArrayList;
import java.util.Collection;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import labyrinthe.ESalle;
import labyrinthe.IEtage;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import labyrinthe.Labyrinthe;
import labyrinthe.Salle;
import personnages.Heros;
import personnages.IPersonnage;

/**
 *
 * @author vdearaujo
 */
public class HerosSprite extends ASprite implements EventHandler<KeyEvent> {

    private ILabyrinthe laby;

    /**
     * Constructeur pour initializer le heros avec son image et le positionner
     * sur l'entrée de l'étage en cours
     *
     * @param personnage heros
     */
    public HerosSprite(IPersonnage personnage, ILabyrinthe laby) {
        super(personnage);
        this.laby = laby;
        Image image = new Image("file:icons/link/LinkRunShieldD8.gif");
        this.setImage(image);
        this.personnage.setPosition(laby.getEntree());
    }

    /**
     * Methode pour gérer les déplacements en fonction de la touche appuyé UP
     * DOWN RIGTH LEFT
     *
     * @param t
     */
    @Override
    public void handle(KeyEvent t) {
        Heros heros = (Heros) getPersonnage();
        ISalle salle = heros.getPosition();
        IEtage etage = salle.getEtage();
        int x = salle.getX();
        int y = salle.getY();

        ISalle cible = null;
        if (!this.estEnMouvement()) {
            switch (t.getCode()) {
                case DOWN:
                    cible = actionSalle(x, y + 1, etage);

                    break;
                case UP:
                    cible = actionSalle(x, y - 1, etage);

                    break;
                case LEFT:
                    cible = actionSalle(x - 1, y, etage);

                    break;
                case RIGHT:
                    cible = actionSalle(x + 1, y, etage);

                    break;
                case M:
                    if (salle.getType() == ESalle.ESCALIER_MONTANT) {
                        cible = salleParEscalier(salle);
                    }
                    break;
                case D:
                    if (salle.getType() == ESalle.ESCALIER_DESCENDANT) {
                        cible = salleParEscalier(salle);
                    }
                    break;
            }
            if (cible != null) {
                heros.salleChoisie = cible;
            }
        }
    }

    /**
     * Méthode qui permet de vérifier la salle existe pour pouvoir faire le
     * déplacement sur celle ci
     *
     * @param x modif ou pas en fonction de la touche appuyé
     * @param y modif ou pas en fonction de la touche appuyé
     * @param etage en cours
     * @return la salle si elle existe , null sinon
     */
    private ISalle actionSalle(int x, int y, IEtage etage) {

        for (ISalle s : etage) {

            if (s.getX() == x && s.getY() == y) {

                return s;
            }

        }
        return null;
    }

    /**
     * Cherche la salle accessible par un escalier sur un autre etage
     *
     * @param salle la salle de départ avec l'escalier
     * @return la salle sur l'autre etage ou null
     */
    private ISalle salleParEscalier(ISalle salle) {
        for (ISalle candidate : laby) {
            if (candidate.getEtage().getNum() != salle.getEtage().getNum()
                    && salle.estAdjacente(candidate)) {
                return candidate;
            }
        }
        return null;
    }
}
