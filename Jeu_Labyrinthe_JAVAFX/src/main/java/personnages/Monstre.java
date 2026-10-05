/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import labyrinthe.ISalle;
import java.util.Random;

/**
 *
 * @author vdearaujo
 */
public class Monstre extends APersonnage {

    private static final Random Rand = new Random();

    public Monstre(ISalle positionInitiale) {
        super(positionInitiale);
    }

    /**
     * Permet de faire en sorte que le monstre apparaisse de facon aléatoire sur
     * l'étage
     *
     * @param sallesAccessibles toutes les salles accessibles au monstre
     * @return la Salle aleatoire choisi
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        if (sallesAccessibles == null || sallesAccessibles.isEmpty()) {
            return this.getPosition();
        }

        List<ISalle> accessibles = new ArrayList<>(sallesAccessibles);
        int index = Rand.nextInt(accessibles.size());
        return accessibles.get(index);
    }

}
