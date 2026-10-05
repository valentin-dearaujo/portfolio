/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vue2D.sprites;

import java.util.Collection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import labyrinthe.ISalle;
import personnages.IPersonnage;

/**
 *
 * @author vdearaujo
 */
public abstract class ASprite implements ISprite {

    public IPersonnage personnage;
    public Image image;
    private int x;
    private int y;

    private int cibleX;
    private int cibleY;
    private boolean initialise = false;
    private int vitesse = 4;

    /**
     * Crée un sprite pour un personnage
     *
     * @param personnage Le personnage à lier
     */
    public ASprite(IPersonnage personnage) {
        this.personnage = personnage;
    }

    /**
     * Change l'image du sprite
     *
     * @param image La nouvelle image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Donne l'image du sprite
     *
     * @return L'image actuelle
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Choisir la vitesse de déplacement
     *
     * @param vitesse a choisir
     */
    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    /**
     * Permet d'obtenir la vitesse
     *
     * @return la vitesse
     */
    public int getVitesse() {
        return this.vitesse;
    }

    /**
     * Change le personnage du sprite
     *
     * @param personnage Le nouveau personnage
     */
    public void setPersonnage(IPersonnage personnage) {
        this.personnage = personnage;
    }

    /**
     * Donne le personnage du sprite
     *
     * @return Le personnage actuel
     */
    public IPersonnage getPersonnage() {
        return this.personnage;
    }

    /**
     * Dessine l'image à l'écran
     *
     * @param g L'outil pour dessiner
     */
    @Override
    public void dessiner(GraphicsContext g) {

        g.drawImage(this.image, this.x, this.y);

    }

    /**
     * Place le sprite sur l'écran
     *
     * @param x La position X en pixels
     * @param y La position Y en pixels
     */
    @Override
    public void setCoordonnees(int cx, int cy) {
        this.cibleX = cx;
        this.cibleY = cy;
        if (!initialise) {
            initialiserCoordonnees(cx, cy);
        } else {
            animerMouvement();
        }
    }

    /**
     * Demande au personnage de choisir sa salle
     *
     * @param sallesAccessibles Les salles où il peut aller
     * @return La salle choisie
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles) {
        return this.personnage.faitSonChoix(sallesAccessibles);
    }

    /**
     * Donne la salle ou se trouve le personnage
     *
     * @return La position actuelle
     */
    @Override
    public ISalle getPosition() {
        return this.personnage.getPosition();
    }

    /**
     * Place le personnage dans une salle
     *
     * @param s La nouvelle salle
     */
    @Override
    public void setPosition(ISalle s) {
        this.personnage.setPosition(s);
    }

    /**
     * Place le sprite directement à sa position de départ
     *
     * @param cx La position X de départ
     * @param cy La position Y de départ
     */
    private void initialiserCoordonnees(int cx, int cy) {
        this.x = cx;
        this.y = cy;
        this.initialise = true;
    }

    /**
     * Fait avancer le sprite vers sa cible d'un nombre de pixels égal à sa
     * vitesse
     */
    private void animerMouvement() {
        this.x = calculerAvancement(this.x, this.cibleX, this.vitesse);
        this.y = calculerAvancement(this.y, this.cibleY, this.vitesse);
    }

    /**
     * Calcule la prochaine position sur un axe sans dépasser la cible
     *
     * @param positionActuelle La position de départ
     * @param positionCible La position à atteindre
     * @param vitesse Le nombre de pixels à parcourir
     * @return La nouvelle position calculée
     */
    private int calculerAvancement(int positionActuelle, int positionCible, int vitesse) {
        if (positionActuelle < positionCible) {
            if (positionActuelle + vitesse > positionCible) {
                return positionCible;
            } else {
                return positionActuelle + vitesse;
            }
        } else if (positionActuelle > positionCible) {
            if (positionActuelle - vitesse < positionCible) {
                return positionCible;
            } else {
                return positionActuelle - vitesse;
            }
        }
        return positionActuelle;
    }

    /**
     * Vérifie si le sprite n'a pas encore atteint sa cible
     *
     * @return Vrai si le sprite est en cours de déplacement, faux sinon
     */
    public boolean estEnMouvement() {
        return this.x != this.cibleX || this.y != this.cibleY;
    }
}
