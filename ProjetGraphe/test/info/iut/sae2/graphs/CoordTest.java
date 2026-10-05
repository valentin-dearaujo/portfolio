/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *
 * @author vrioux
 */
public class CoordTest {

    @Test
    public void testAdd() {

        Coord a = new Coord(2.0, 2.0);
        Coord b = new Coord(3.0, 1.0);
        Coord c = new Coord();
        Coord resultA = new Coord(5.0, 3.0);
        Coord resultB = new Coord(3.0, 1.0);
        assertTrue(resultA.x == Coord.add(a, b).x);
        assertTrue(resultA.y == Coord.add(a, b).y);
        assertTrue(resultB.x == Coord.add(b, c).x);
        assertTrue(resultB.y == Coord.add(b, c).y);


        Coord A = new Coord(2.0, 2.0);
        Coord B = new Coord(2.0, 2.0);
        Coord C = new Coord(2.0, 2.0);
        Coord moove1 = new Coord(5.0, 3.0);
        Coord moove2 = new Coord(3.0, 1.0);
        Coord moove3 = new Coord(0, 0);

        A.add(3, 1);
        B.add(1, -1);
        C.add(-2, -2);

        assertTrue(moove1.x == A.x);
        assertTrue(moove1.y == A.y);
        assertTrue(moove2.x == B.x);
        assertTrue(moove2.y == B.y);
        assertTrue(moove3.x == C.x);
        assertTrue(moove3.y == C.y);

    }

    @Test
    public void testMinus() {

        Coord a = new Coord(2.0, 2.0);
        Coord b = new Coord(3.0, 1.0);
        Coord c = new Coord();
        Coord resultA = new Coord(-1.0, 1.0);
        Coord resultB = b;
        assertTrue(resultA.x == Coord.minus(a, b).x);
        assertTrue(resultA.y == Coord.minus(a, b).y);
        assertTrue(resultB.x == Coord.minus(b, c).x);
        assertTrue(resultB.y == Coord.minus(b, c).y);
    }

    @Test
    public void testMult() {

        Coord a = new Coord(2.0, 2.0);
        
        Coord result1 = new Coord(4, 4);
        Coord result2 = new Coord(0, 0);
        Coord result3 = new Coord(-4, -4);
        double scalar1 = 2;
        double scalar2 = 0;
        double scalar3 = -2;
        
        assertTrue(result1.x == Coord.mult(a, scalar1).x);
        assertTrue(result1.y == Coord.mult(a, scalar1).y);
        
        assertTrue(result2.x == Coord.mult(a, scalar2).x);
        assertTrue(result2.y == Coord.mult(a, scalar2).y);
        
        assertTrue(result3.x == Coord.mult(a, scalar3).x);
        assertTrue(result3.y == Coord.mult(a, scalar3).y);


        Coord A = new Coord(3.0, -3.0);
        Coord B = new Coord(3.0, -3.0);
        Coord C = new Coord(3.0, -3.0);

        Coord mult1 = new Coord(6, -6);
        Coord mult2 = new Coord(0, 0);
        Coord mult3 = new Coord(-6, 6);
        double scal1 = 2;
        double scal2 = 0;
        double scal3 = -2;

        A.mult(scal1);
        B.mult(scal2);
        C.mult(scal3);

        assertTrue(mult1.x == A.x);
        assertTrue(mult1.y == A.y);

        assertTrue(mult2.x == B.x);
        assertTrue(mult2.y == B.y);

        assertTrue(mult3.x == C.x);
        assertTrue(mult3.y == C.y);

    }

}
