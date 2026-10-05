package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import labyrinthe.ESalle;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import personnages.Heros;
import personnages.Monstre;
import vue2D.IVue;
import vue2D.sprites.HerosSprite;
import vue2D.sprites.ISprite;
import vue2D.sprites.MonstreSprite;

/**
 *
 * @author arpecher
 */
public class Core {

    ISprite heros;
    ILabyrinthe labyrinthe;

    protected void initLabyrinthe() {
        // creation du labyrinthe
        labyrinthe = new labyrinthe.LabyrintheGraphe();
    }

    protected void initSprites(IVue vue) {
        // creation du heros 
        Heros h = new personnages.Heros(labyrinthe.getEntree());
        this.heros = new HerosSprite(h, labyrinthe);
        vue.add(this.heros);

        
        //creation des monstres 
        int nombreMonstres = 10;
        Random random = new Random();
        List<ISalle> posDejaChoisi = new ArrayList<>();
        List<ISalle> sallesPossibles = new ArrayList<>();
        for (ISalle s : labyrinthe) {
            if (s.getType() == ESalle.NORMALE && s.getEtage() == labyrinthe.getEtageCourant()) {
                sallesPossibles.add(s);
            }
        }

       while (posDejaChoisi.size() < nombreMonstres) {
            ISalle position = sallesPossibles.get(random.nextInt(sallesPossibles.size()));

            if (!posDejaChoisi.contains(position)) {
                Monstre m = new Monstre(position);
                MonstreSprite ms = new MonstreSprite(m);
                vue.add(ms);
                posDejaChoisi.add(position);
            }
        }
    }

    protected void jeu(IVue vue) {
        // boucle principale
        ISalle destination = null;
        while (!labyrinthe.getSortie().equals(heros.getPosition())) {
            // ajustement etage courant: celui du héros
            labyrinthe.setEtageCourant(heros.getPosition().getEtage());
            // choix et deplacements de chaque sprite
            for (ISprite s : vue) {
                Collection<ISalle> sallesAccessibles = labyrinthe.sallesAccessibles(s);
                destination = s.faitSonChoix(sallesAccessibles); // on demande au personnage de faire son choix de salle
                s.setPosition(destination); // deplacement

            }
            // detection des collisions
            boolean collision = false;
            ISprite monstre = null;
            for (ISprite s : vue) {
                if (s != heros) {
                    if (s.getPosition() == heros.getPosition()) {
                        System.out.println("Collision !!");
                        collision = true;
                        monstre = s;
                    }
                }
            }
            if (collision) {
                vue.remove(monstre);
                vue.remove(heros);
                System.out.println("Perdu !");
                System.out.println("Plus que " + vue.size() + " personnages ...");
            }

            temporisation(50);
        }
        System.out.println("Gagné!");
    }

    protected void temporisation(int nb) {
        try {
            Thread.sleep(nb); // pause de nb millisecondes
        } catch (InterruptedException ie) {
        }
    }
}
