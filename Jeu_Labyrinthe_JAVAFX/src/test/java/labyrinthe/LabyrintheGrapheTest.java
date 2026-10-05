/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import org.junit.Test;
import personnages.Heros;

import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author vdearaujo
 */
public class LabyrintheGrapheTest {

    @Test
    public void cheminTest() {

        LabyrintheGraphe laby = new LabyrintheGraphe();
        laby.clear();

        Etage e = new Etage(1);
        Salle entree = new Salle(0, 0, ESalle.ENTREE, e);
        Salle couloir = new Salle(1, 0, ESalle.NORMALE, e);
        Salle sortie = new Salle(2, 0, ESalle.SORTIE, e);

        laby.add(entree);
        laby.add(couloir);
        laby.add(sortie);

        laby.creerLabyrinthe();

        Collection<ISalle> cheminTrouve = laby.chemin(entree, sortie);

        assertNotNull(cheminTrouve);
        assertEquals(3, cheminTrouve.size());
        assertTrue(cheminTrouve.contains(entree));
        assertTrue(cheminTrouve.contains(sortie));
    }
}
