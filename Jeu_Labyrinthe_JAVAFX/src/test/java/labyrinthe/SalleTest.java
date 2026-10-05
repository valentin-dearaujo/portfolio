/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package labyrinthe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gothmog
 */
public class SalleTest {

    public SalleTest() {
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
    public void TestGetX() {
        Etage e = new Etage(1);
        Salle s1 = new Salle(5, 10, ESalle.NORMALE, e);
        assertEquals(5, s1.getX());

        Salle s2 = new Salle(-1, 0, ESalle.NORMALE, e);
        assertEquals(-1, s2.getX());
    }

    @Test
    public void TestGetY() {
        Etage e = new Etage(1);
        Salle s1 = new Salle(5, 10, ESalle.NORMALE, e);
        assertEquals(10, s1.getY());

        Salle s2 = new Salle(0, -5, ESalle.NORMALE, e);
        assertEquals(-5, s2.getY());
    }

    @Test
    public void TestGetType() {
        Etage e = new Etage(1);
        Salle s1 = new Salle(0, 0, ESalle.NORMALE, e);
        assertEquals(ESalle.NORMALE, s1.getType());

        Salle s2 = new Salle(0, 0, ESalle.ENTREE, e);
        assertEquals(ESalle.ENTREE, s2.getType());
    }

    @Test
    public void TestGetEtage() {
        Etage e = new Etage(1);
        Salle s = new Salle(0, 0, ESalle.NORMALE, e);
        assertEquals(e, s.getEtage());
        assertEquals(1, s.getEtage().getNum());
    }

    @Test
    public void TestEstAdjacente() {
        Etage e1 = new Etage(1);
        Etage e2 = new Etage(2);

        Salle centre = new Salle(5, 5, ESalle.NORMALE, e1);
        Salle haut = new Salle(5, 4, ESalle.NORMALE, e1);
        Salle bas = new Salle(5, 6, ESalle.NORMALE, e1);
        Salle gauche = new Salle(4, 5, ESalle.NORMALE, e1);
        Salle droite = new Salle(6, 5, ESalle.NORMALE, e1);
        Salle loin = new Salle(10, 10, ESalle.NORMALE, e1);

        assertTrue(centre.estAdjacente(haut));
        assertTrue(centre.estAdjacente(bas));
        assertTrue(centre.estAdjacente(gauche));
        assertTrue(centre.estAdjacente(droite));
        assertFalse(centre.estAdjacente(loin));
        assertFalse(centre.estAdjacente(centre));

        Salle escalierMontantE1 = new Salle(3, 3, ESalle.ESCALIER_MONTANT, e1);
        Salle escalierDescendantE2 = new Salle(3, 3, ESalle.ESCALIER_DESCENDANT, e2);
        Salle escalierDescendantMauvaisesCoordonnees = new Salle(4, 4, ESalle.ESCALIER_DESCENDANT, e2);

        assertTrue(escalierMontantE1.estAdjacente(escalierDescendantE2));
        assertTrue(escalierDescendantE2.estAdjacente(escalierMontantE1));
        assertFalse(escalierMontantE1.estAdjacente(escalierDescendantMauvaisesCoordonnees));
    }

    @Test
    public void TestEquals() {
        Etage e1 = new Etage(1);
        Etage e2 = new Etage(2);

        Salle s1 = new Salle(5, 5, ESalle.NORMALE, e1);
        Salle s2 = new Salle(5, 5, ESalle.ENTREE, e1);
        Salle s3 = new Salle(6, 5, ESalle.NORMALE, e1);
        Salle s4 = new Salle(5, 6, ESalle.NORMALE, e1);
        Salle s5 = new Salle(5, 5, ESalle.NORMALE, e2);

        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(null));
        assertFalse(s1.equals("Test"));
        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
        assertFalse(s1.equals(s5));
    }

    @Test
    public void TestHashCode() {
        Etage e1 = new Etage(1);
        Salle s1 = new Salle(5, 5, ESalle.NORMALE, e1);
        Salle s2 = new Salle(5, 5, ESalle.ENTREE, e1);
        Salle s3 = new Salle(6, 5, ESalle.NORMALE, e1);

        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }
}
