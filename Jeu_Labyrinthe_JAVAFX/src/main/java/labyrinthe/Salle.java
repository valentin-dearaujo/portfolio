/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

/**
 *
 * @author vdearaujo
 */
public class Salle implements ISalle {

    private int x;
    private int y;
    private ESalle type;
    private IEtage etage;

    /**
     * Constructor
     *
     * @param x Coordonnées du x
     * @param y Coordonnées du y
     * @param type Type de la Salle
     * @param etage Type de l'etage
     */
    public Salle(int x, int y, ESalle type, IEtage etage) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.etage = etage;
    }

    /**
     * Obtenir le x
     *
     * @return x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Obtenir le y
     *
     * @return y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Obtenir le type de la salle
     *
     * @return type
     */
    @Override
    public ESalle getType() {
        return type;
    }

    /**
     * Obtenir l'étage
     *
     * @return etage
     */
    @Override
    public IEtage getEtage() {
        return etage;
    }

    /**
     * Verifie si une salle est adjacente a une autre
     *
     * @param autre l'autre salle
     * @return vrai si c'est le cas faux sinon
     */
    @Override
    public boolean estAdjacente(ISalle autre) {

        if (this.etage != null && autre.getEtage() != null && this.etage.getNum() == autre.getEtage().getNum()) {
            Salle haut = new Salle(this.x, this.y - 1, this.type, this.etage);
            Salle bas = new Salle(this.x, this.y + 1, this.type, this.etage);
            Salle gauche = new Salle(this.x - 1, this.y, this.type, this.etage);
            Salle droite = new Salle(this.x + 1, this.y, this.type, this.etage);

            if (autre.equals(haut) || autre.equals(bas) || autre.equals(gauche) || autre.equals(droite)) {
                return true;
            }
        }

        if (this.type == ESalle.ESCALIER_MONTANT && autre.getType() == ESalle.ESCALIER_DESCENDANT) {
            Etage etageSup = new Etage(this.etage.getNum() + 1);
            Salle auDessus = new Salle(this.x, this.y, ESalle.ESCALIER_DESCENDANT, etageSup);
            if (autre.equals(auDessus)) {
                return true;
            }
        }

        if (this.type == ESalle.ESCALIER_DESCENDANT && autre.getType() == ESalle.ESCALIER_MONTANT) {
            Etage etageInf = new Etage(this.etage.getNum() - 1);
            Salle enDessous = new Salle(this.x, this.y, ESalle.ESCALIER_MONTANT, etageInf);
            if (autre.equals(enDessous)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salle other = (Salle) obj;
        if (this.x != other.getX()) {
            return false;
        }
        if (this.y != other.getY()) {
            return false;
        }
        if (this.etage == null || other.getEtage() == null) {
            return false;
        }
        return this.etage.getNum() == other.getEtage().getNum();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        hash = 31 * hash + (this.etage != null ? this.etage.getNum() : 0);
        return hash;
    }
}
