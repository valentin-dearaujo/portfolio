package vue2D.javafx;

import java.util.Collection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;

import vue2D.AVue;
import vue2D.sprites.ISprite;
import labyrinthe.IEtage;

/**
 *
 * @author INFO Professors team
 */
public class Dessin extends Canvas {

    private Collection<ISprite> sprites;
    private int unite;

    // donnees labyrinthe labyrinthe
    private ILabyrinthe labyrinthe;
    private ISalle entree;
    private ISalle sortie;
    private int largeur;
    private int hauteur;

    private GraphicsContext tampon;
    private Image murImage;
    private Image solImage;
    private Image escalierM;
    private Image escalierD;
    private Image sortieS;
    private int tailleLinkH = 6;
    private int tailleLinkL = 2;

    private static final double PORTEE_LUMIERE = 8.0;

    public Dessin(ILabyrinthe labyrinthe, Collection<ISprite> sprites) {
        this.labyrinthe = labyrinthe;
        this.sprites = sprites;
        this.unite = AVue.UNITE;
        entree = labyrinthe.getEntree();
        sortie = labyrinthe.getSortie();
        largeur = labyrinthe.getEtageCourant().getLargeur();
        hauteur = labyrinthe.getEtageCourant().getHauteur();
        setWidth(largeur * unite);
        setHeight(hauteur * unite);
        tampon = this.getGraphicsContext2D();
        chargementImages();

        dessinFond();

        IEtage rep = this.labyrinthe.getEtageCourant();
        dessinSalles(rep);
    }

    /**
     * Permet de charger les images dans les attributs
     */
    public void chargementImages() {
        murImage = new Image("file:icons/mur0.gif");
        solImage = new Image("file:icons/pyramide.png");
        escalierM = new Image("file:icons/up.gif");
        escalierD = new Image("file:icons/down.gif");
        sortieS = new Image("file:icons/sortie.gif");
    }

    /**
     * Met l'image au fond du dessin
     */
    public void dessinFond() {
        tampon.drawImage(solImage, 0, 0, unite * largeur,
                unite * hauteur);
    }

    /**
     * Dessine toutes les salles de l'étage
     *
     * @param etage l'étage a dessiner
     */
    public void dessinSalles(IEtage etage) {
        for (ISalle s : etage) {
            Color c = Color.rgb(200, 200, 200);
            dessinSalle(s, c, getPositionHeros());
        }
    }

    /**
     * Renvoie la position du heros, Comme Core.initSprites ajoute toujours le
     * heros en premier (avant les monstres), c'est le premier sprite de la
     * collection. Renvoie null si aucun sprite n'a encore ete ajoute
     */
    private ISalle getPositionHeros() {
        if (sprites.isEmpty()) {
            return null;
        }
        ISprite premier = sprites.iterator().next();
        return premier.getPosition();
    }

    /**
     * Methode qui permet de dessiner l"étage sur le dessin
     *
     * @param s la salle a dessiner
     * @param c une couleur pour les salles
     * @param positionHeros la pos du heros sur les cases
     */
    public void dessinSalle(ISalle s, Color c, ISalle positionHeros) {

        int posX = s.getX() * unite;
        int posY = s.getY() * unite;

        switch (s.getType()) {
            case ESCALIER_DESCENDANT:
                tampon.drawImage(escalierD, posX, posY, unite, unite);
                break;
            case ESCALIER_MONTANT:
                tampon.drawImage(escalierM, posX, posY, unite, unite);
                break;
            case ENTREE:
                tampon.setFill(Color.GREEN);
                tampon.fillRect(posX, posY, unite, unite);
                break;
            case SORTIE:
                tampon.drawImage(sortieS, posX, posY, unite, unite);
                break;
            case NORMALE:
                tampon.drawImage(murImage, posX, posY, unite, unite);
                break;
            default:
                tampon.fillRect(posX, posY, unite, unite);
                break;
        }

        if (positionHeros != null) {
            int distance = distanceManhattan(s, positionHeros);
            double opacite = distance / PORTEE_LUMIERE;
            if (opacite > 1.0) {
                opacite = 1.0;
            }
            tampon.setFill(Color.rgb(0, 0, 0, opacite));
            tampon.fillRect(posX, posY, unite, unite);
        }
    }

    /**
     * Calcule la distance de Manhattan entre deux salles
     *
     * @param a La première salle
     * @param b La deuxième salle
     * @return La distance totale calculée entre les deux salles
     */
    private int distanceManhattan(ISalle a, ISalle b) {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        if (dx < 0) {
            dx = -dx;
        }
        if (dy < 0) {
            dy = -dy;
        }
        return dx + dy;
    }

    public void dessinPlusCourtChemin(ISprite p) {
        // ...
    }

}
