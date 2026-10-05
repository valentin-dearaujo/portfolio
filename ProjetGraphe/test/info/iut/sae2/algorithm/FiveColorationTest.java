package info.iut.sae2.algorithm;


import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.GraphLoader;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author vrioux
 */
public class FiveColorationTest {
    @Test
    public void testFiveColoration() { // test we check if each color is different compared to the neighbords
       
        IGraph graph = GraphLoader.loadFromFile("dataset/planar_grid_20x20_nodes.csv", "dataset/planar_grid_20x20_edges.csv");
        FiveColoration fc = new FiveColoration();
        fc.apply(graph, new HashMap<>());
        for (INode i : graph.getNodes()) {
            for(INode j : graph.getNeighbors(i)){
                assertNotEquals(i.getColor(), j.getColor());
            }
        }
    }
    




    private FiveColoration algo;
    private IGraph graph;

    @Before
    public void setUp() {
        algo = new FiveColoration();
        graph = new Graph();
    }

    /**
     * Test indirect de naiveColor (g.getNodes().size() <= 5)
     */
    @Test
    public void testColorationPetitsGraphes() {
        INode n1 = graph.addNode();
        INode n2 = graph.addNode();
        INode n3 = graph.addNode();
        graph.addEdge(n1, n2);
        graph.addEdge(n2, n3);

        algo.apply(graph, new HashMap<>());

        ValidColorationTest(graph);
    }

    /**
     * Test indirect de la méthode degree4
     */
    @Test
    public void testCasDegre4() {
        INode n1 = graph.addNode(); 
        INode n2 = graph.addNode();
        INode n3 = graph.addNode();
        INode n4 = graph.addNode();
        INode n5 = graph.addNode();
        INode n6 = graph.addNode(); 

        graph.addEdge(n1, n2);
        graph.addEdge(n1, n3);
        graph.addEdge(n1, n4);
        graph.addEdge(n1, n5);
        graph.addEdge(n2, n6);

        algo.apply(graph, new HashMap<>());

        ValidColorationTest(graph);
    }

    /**
     * Test indirect de la méthode degree5 et de findPair
     */
    @Test
    public void testCasDegre5EtFindPair() {
        INode centre = graph.addNode(); 
        INode v1 = graph.addNode();
        INode v2 = graph.addNode();
        INode v3 = graph.addNode();
        INode v4 = graph.addNode();
        INode v5 = graph.addNode();
        INode extra = graph.addNode(); 

        graph.addEdge(centre, v1);
        graph.addEdge(centre, v2);
        graph.addEdge(centre, v3);
        graph.addEdge(centre, v4);
        graph.addEdge(centre, v5);
        graph.addEdge(v1, extra);
        graph.addEdge(v2, extra);

        algo.apply(graph, new HashMap<>());

        ValidColorationTest(graph);
    }

    
    /**
     * Méthode utilitaire d'assertion partagée révisée
     */
    private void ValidColorationTest(IGraph g) {
        HashSet<Color> uniqueColors = new HashSet<>();

        for (INode node : g.getNodes()) {
            Color colorNode = g.getNodeColor(node);

            assertNotNull(colorNode);
            uniqueColors.add(colorNode);


            for (INode neighbor : g.getNeighbors(node)) {
                Color colorNeighbor = g.getNodeColor(neighbor);
                assertNotNull(colorNeighbor);
                boolean sameColor = (colorNode.getR() == colorNeighbor.getR()) && (colorNode.getG() == colorNeighbor.getG()) && (colorNode.getB() == colorNeighbor.getB());

                if (sameColor) {
                    fail();
                }
            }
        }

      
        assertTrue(uniqueColors.size() <= 5);
    }
}

