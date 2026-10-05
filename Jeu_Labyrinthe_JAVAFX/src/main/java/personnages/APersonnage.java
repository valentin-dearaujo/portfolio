/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personnages;

import java.util.Collection;
import labyrinthe.ISalle;

/**
 *
 * @author valen
 */
public abstract class APersonnage implements IPersonnage {

    private ISalle position;

    public APersonnage(ISalle positionInitiale) {
        this.position = positionInitiale;
    }

    /**
     * Permet d'obtenir la position du personnage
     *
     * @return la salle sur laquelle le personnage est
     */
    @Override
    public ISalle getPosition() {
        return this.position;
    }

    /**
     * Permet de définir la position du personnage
     *
     * @param s la salle a modifier
     */
    @Override
    public void setPosition(ISalle s) {
        this.position = s;
    }

    @Override
    public abstract ISalle faitSonChoix(Collection<ISalle> sallesAccessibles);
}
