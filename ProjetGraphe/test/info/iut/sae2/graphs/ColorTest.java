/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;


import org.junit.Before;


import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author vrioux
 */
public class ColorTest {
    
    @Test
    public void testColor(){
        Color color = new Color();
                
        
        
    }
    
    
    
    
    
    @Test
    public void testColorConstructor(){ // for the index notably
        Color color = new Color();
        Color colorRgbaRed = new Color(255,0,0,255);
        Color colorIndex = new Color(0); // 0 = red
        assertNotEquals(colorIndex, color);
        assertNotEquals(colorRgbaRed, color);
        assertEquals(colorRgbaRed, colorIndex);

    }
    
    @Test
    public void testColorConstructorIndex(){ // for the index notably
        
        Color colorIndex = new Color(0); // 0 = red
        
        assertEquals(colorIndex.r, 255);
        assertEquals(colorIndex.g, 0);
        assertEquals(colorIndex.b, 0);
        assertEquals(colorIndex.a, 255);
        
        colorIndex = new Color(1); // 1 = green
        
        assertEquals(colorIndex.r, 0);
        assertEquals(colorIndex.g, 255);
        assertEquals(colorIndex.b, 0);
        assertEquals(colorIndex.a, 255);
        
        

    }
    
    @Test
    public void testInterpolate(){
        Color start = new Color();
        Color end = new Color();
        int step = 2;
        int nbStep = 3;
        
        Color res = Color.interpolate(start, end, step, nbStep);
        
        
        
    }
    
}
