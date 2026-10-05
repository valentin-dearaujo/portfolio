/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.Collection;
import labyrinthe.ISalle;

/**
 *
 * @author vdearaujo
 */
public class Heros extends APersonnage {

    public ISalle salleChoisie;

    public Heros(ISalle positionInitiale) {
        super(positionInitiale);
        this.salleChoisie = positionInitiale;
    }

    /**
     * Methode permettant de vérifier si la salle viser par le heros est
     * accessible ou pas et le déplacer si c'est le cas
     *
     * @param sallesAccessibles toutes les salles accessibles
     * @return la salle choisi
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        if (sallesAccessibles.contains(this.salleChoisie)) {
            return this.salleChoisie;
        }
        return this.getPosition();
    }
}
