    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package labyrinthe;

import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import personnages.Heros;

/**
 *
 * @author gothmog
 */
public class LabyrintheTest {
    
    public LabyrintheTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   @Test
    public void TestSallesAccessibles() {
        Labyrinthe laby = new Labyrinthe();
        laby.clear();

        Etage e = new Etage(1);
        Salle centre = new Salle(2, 2, ESalle.NORMALE, e);
        Salle haut = new Salle(2, 1, ESalle.NORMALE, e);
        Salle bas = new Salle(2, 3, ESalle.NORMALE, e);
        Salle loin = new Salle(10, 10, ESalle.NORMALE, e);

        laby.add(centre);
        laby.add(haut);
        laby.add(bas);
        laby.add(loin);

        Heros herosTest = new Heros(centre);

        Collection<ISalle> accessibles = laby.sallesAccessibles(herosTest);

        assertEquals(2, accessibles.size());
        assertTrue(accessibles.contains(haut));
        assertTrue(accessibles.contains(bas));
        assertFalse(accessibles.contains(loin));
        assertFalse(accessibles.contains(centre));
    }

    @Test
    public void TestGetEntree() {
        Labyrinthe laby = new Labyrinthe();
        laby.clear();

        Etage e = new Etage(1);
        Salle s1 = new Salle(0, 0, ESalle.NORMALE, e);
        Salle s2 = new Salle(1, 1, ESalle.ENTREE, e);
        Salle s3 = new Salle(2, 2, ESalle.NORMALE, e);

        assertNull(laby.getEntree());

        laby.add(s1);
        laby.add(s2);
        laby.add(s3);

        assertEquals(s2, laby.getEntree());
    }

    @Test
    public void TestGetSortie() {
        Labyrinthe laby = new Labyrinthe();
        laby.clear();

        Etage e = new Etage(1);
        Salle s1 = new Salle(0, 0, ESalle.NORMALE, e);
        Salle s2 = new Salle(1, 1, ESalle.SORTIE, e);

        assertNull(laby.getSortie());

        laby.add(s1);
        laby.add(s2);

        assertEquals(s2, laby.getSortie());
    }

    @Test
    public void TestGetEtageCourant() {
        Labyrinthe laby = new Labyrinthe();
        assertNotNull(laby.getEtageCourant());
    }

    @Test
    public void TestSetEtageCourant() {
        Labyrinthe laby = new Labyrinthe();
        Etage nouvelEtage = new Etage(5);

        laby.setEtageCourant(nouvelEtage);

        assertEquals(nouvelEtage, laby.getEtageCourant());
        assertEquals(5, laby.getEtageCourant().getNum());
    }

    @Test
    public void TestChemin() {
        Labyrinthe laby = new Labyrinthe();
        Etage e = new Etage(1);
        Salle s1 = new Salle(0, 0, ESalle.NORMALE, e);
        Salle s2 = new Salle(1, 1, ESalle.NORMALE, e);

        assertNull(laby.chemin(s1, s2));
    }
    
}
