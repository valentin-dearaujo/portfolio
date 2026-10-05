/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author vdearaujo
 */
public class EtageTest {
    @Test
    public void TestGetLargeur() {
        Etage e1 = new Etage();
        assertEquals(40, e1.getLargeur());
    }

    @Test
    public void TestGetHauteur() {
        Etage e1 = new Etage();
        assertEquals(40, e1.getHauteur());
    }

    @Test
    public void TestGetNum() {
        Etage e1 = new Etage();
        assertEquals(1, e1.getNum());

        Etage e2 = new Etage(5);
        assertEquals(5, e2.getNum());
    }
}
