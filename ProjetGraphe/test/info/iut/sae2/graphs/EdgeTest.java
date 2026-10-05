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
public class EdgeTest {
    
    
    @Test 
    public void testEdgeConstructor(){
        
        assertTrue(Edge.nbEdge.equals(0));
        
        IEdge testAddEdge = new Edge();
        
        assertFalse(Edge.nbEdge.equals(0));
        assertTrue(Edge.nbEdge.equals(1));
        
        
        
        INode node1 = new Node();
        INode node2 = new Node();
        
        IEdge byNodeEdge = new Edge(node1, node2);
        
        assertFalse(Edge.nbEdge.equals(0));
        assertFalse(Edge.nbEdge.equals(1));
        assertTrue(Edge.nbEdge.equals(2));
        
        
        
        
    }
    
    
}
