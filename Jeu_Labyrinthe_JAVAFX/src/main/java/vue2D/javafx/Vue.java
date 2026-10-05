package vue2D.javafx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import vue2D.IVue;
import vue2D.AVue;
import vue2D.sprites.ISprite;

/**
 *
 * @author INFO Professors team
 */
public class Vue extends AVue implements IVue {

    Dessin dessin;
    ILabyrinthe labyrinthe;
    public Scene scene;

    public Vue(ILabyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        dessin = new Dessin(labyrinthe, this);
        Group root = new Group();
        this.scene = new Scene(root);
        root.getChildren().add(dessin);
    }

    /**
     * Permet d'afficher les Sprites sur le dessin
     */
    @Override
    public void dessiner() {
        // recopie du fond (image); murs + salles
        dessin.dessinFond();
        dessin.dessinSalles(labyrinthe.getEtageCourant());

        for (ISprite s : this) {
            ISalle pos = s.getPosition();

            if (pos.getEtage().equals(labyrinthe.getEtageCourant())) {
                s.setCoordonnees(pos.getX() * UNITE, pos.getY() * UNITE);
                s.dessiner(dessin.getGraphicsContext2D());

            }

        }

    }

    /**
     *
     * @param sprite
     * @return
     */
    @Override
    public boolean add(ISprite sprite) {
        super.add(sprite);

        if (sprite instanceof EventHandler) {
            System.out.println(" registering keylistener ");

            this.scene.setOnKeyPressed((EventHandler) sprite);
        }
        return true;
    }
}
