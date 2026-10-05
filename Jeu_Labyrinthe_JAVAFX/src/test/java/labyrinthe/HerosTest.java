/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import personnages.Heros;

/**
 *
 * @author vdearaujo
 */
public class HerosTest {

    @Test
    public void TestFaitSonChoix() {
        Etage e = new Etage(1);
        Salle posInitiale = new Salle(0, 0, ESalle.ENTREE, e);
        Salle accessible = new Salle(1, 0, ESalle.NORMALE, e);
        Salle inaccessible = new Salle(10, 10, ESalle.NORMALE, e);

        Heros h = new Heros(posInitiale);

        Collection<ISalle> salles = new ArrayList<>();
        salles.add(posInitiale);
        salles.add(accessible);

        h.salleChoisie = accessible;
        assertEquals(accessible, h.faitSonChoix(salles));

        h.salleChoisie = inaccessible;
        assertEquals(posInitiale, h.faitSonChoix(salles));
  
    }
}
