/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author vrioux
 */
public class NodeTest {
    @Test
    public void testNodeConstructor(){
        
    }
    @Test
    public void testNodeConstructorSinceParameter(){
        INode nodeSince = new Node();
        
        assertEquals(nodeSince.getSize(), new Size(1,1));
        assertEquals(nodeSince.getCoord(), new Coord(0, 0));
        assertEquals(nodeSince.getColor(), new Color(255, 0, 0, 255));
        assertEquals(nodeSince.getId(), this);   
    }
    @Test
    public void testNodeConstructorWithParameter(){
        INode nodeParam = new Node(new Size(), new Color(), new Coord());
        
        assertEquals(nodeParam.getSize(), new Size(1,1));
        assertEquals(nodeParam.getCoord(), new Coord(0, 0));
        assertEquals(nodeParam.getColor(), new Color(255, 0, 0, 255));
        assertEquals(nodeParam.getId(), this);
        
    }
    
    
    
    
    @Test
    public void testNodeGetEdge(){
        
    }
    
    
}
