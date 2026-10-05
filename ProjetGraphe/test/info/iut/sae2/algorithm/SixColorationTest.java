/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.algorithm;


import info.iut.sae2.graphs.GraphLoader;


import static org.junit.Assert.*;

import info.iut.sae2.graphs.Graph;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author vrioux
 */
public class SixColorationTest {
    
    private SixColoration algo;
    private IGraph graph;
    private INode n1, n2, n3, n4, n5, n6, n7;
    
    
    @Before
    public void setUp() {
        algo = new SixColoration();
        graph = new Graph();
        

        n1 = graph.addNode(); 
        n2 = graph.addNode();
        n3 = graph.addNode();
        n4 = graph.addNode();
        n5 = graph.addNode();
        n6 = graph.addNode();
        n7 = graph.addNode();
    }
    
    @Test
    public void testSixColoration() { // test we check if each color is different compared to the neighbords
        IGraph graph = GraphLoader.loadFromFile("dataset/simple_nodes.csv", "dataset/simple_edges.csv");
        SixColoration sc = new SixColoration();
        sc.apply(graph, new HashMap<>());
        for (INode i : graph.getNodes()) {
            for(INode j : graph.getNeighbors(i)){
                assertNotEquals(i.getColor(), j.getColor());
            }
        }
    }
    
    
    /**
     * Test de la fonction initializeVirtualGraph
     * Objectif : Vérifier que les degrés de départ sont bien calculés et que 
     * seuls les nœuds de degré <= 5 intègrent la liste de traitement.
     */
    @Test
    public void testInitializeVirtualGraph() {

        graph.addEdge(n1, n2);
        graph.addEdge(n1, n3);
        graph.addEdge(n1, n4);
        graph.addEdge(n1, n5);
        graph.addEdge(n1, n6);
        graph.addEdge(n1, n7);

        HashMap<INode, Integer> currentDegrees = new HashMap<>();
        HashMap<INode, Boolean> isRemoved = new HashMap<>();
        ArrayList<INode> list = new ArrayList<>();

       
        algo.initializeVirtualGraph(graph, currentDegrees, isRemoved, list);

        Assert.assertEquals(Integer.valueOf(6), currentDegrees.get(n1));
        Assert.assertEquals(Integer.valueOf(1), currentDegrees.get(n2));
        Assert.assertFalse(isRemoved.get(n1));

        Assert.assertFalse(list.contains(n1));
  
        Assert.assertTrue(list.contains(n2));
    }

    /**
     * Test de la fonction updateNeighborDegrees
     * Objectif : S'assurer que lorsqu'un nœud est "supprimé", le degré de ses voisins
     * non-supprimés diminue bien de 1, et qu'ils basculent dans la liste si nécessaire.
     */
    @Test
    public void testUpdateNeighborDegrees() {
        graph.addEdge(n1, n2); 

        HashMap<INode, Integer> currentDegrees = new HashMap<>();
        currentDegrees.put(n1, 6);
        currentDegrees.put(n2, 1);

        HashMap<INode, Boolean> isRemoved = new HashMap<>();
        isRemoved.put(n1, false);
        isRemoved.put(n2, false);

        ArrayList<INode> list = new ArrayList<>();


        isRemoved.put(n2, true); 
        algo.updateNeighborDegrees(graph, n2, currentDegrees, isRemoved, list);


        Assert.assertEquals(Integer.valueOf(5), currentDegrees.get(n1));
      
        Assert.assertTrue(list.contains(n1));
    }

    /**
     * Test de la fonction getNextNodeToRemove et rescueGetNextNodeToRemove
     * Objectif : Vérifier qu'on récupère bien un nœud valide à supprimer de la liste,
     * ou qu'on utilise le plan B (rescue) si la liste principale est vide.
     */
    @Test
    public void testGetNextNodeToRemoveAndRescue() {
        ArrayList<INode> allNodes = graph.getNodes();
        ArrayList<INode> list = new ArrayList<>();
        HashMap<INode, Boolean> isRemoved = new HashMap<>();

        for (INode n : allNodes) {
            isRemoved.put(n, false);
        }


        list.add(n1);
        INode removed = algo.getNextNodeToRemove(list, isRemoved, allNodes);
        Assert.assertEquals(n1, removed);


        list.clear();
        INode rescueRemoved = algo.getNextNodeToRemove(list, isRemoved, allNodes);
        
        Assert.assertNotNull(rescueRemoved);
        Assert.assertFalse(isRemoved.get(rescueRemoved));
    }

    /**
     * Test de la fonction processNodeRemovals
     * Objectif : Valider que la méthode crée une pile complète contenant TOUS
     * les nœuds du graphe dans un ordre d'élimination valide.
     */
    @Test
    public void testProcessNodeRemovals() {
        HashMap<INode, Integer> currentDegrees = new HashMap<>();
        HashMap<INode, Boolean> isRemoved = new HashMap<>();
        ArrayList<INode> list = new ArrayList<>();

        algo.initializeVirtualGraph(graph, currentDegrees, isRemoved, list);
        ArrayList<INode> stack = algo.processNodeRemovals(graph, currentDegrees, isRemoved, list);

      
        Assert.assertEquals(graph.getNodes().size(), stack.size());

        for (INode n : graph.getNodes()) {
            Assert.assertTrue(isRemoved.get(n));
        }
    }

    /**
     * Test de la fonction assignColors
     * Objectif : Vérifier que l'attribution finale des couleurs se fait 
     * sans conflit de voisinage en dépilant les éléments.
     */
    @Test
    public void testAssignColors() {

        graph.addEdge(n1, n2);
        graph.addEdge(n2, n3);

       
        ArrayList<INode> removedNodesStack = new ArrayList<>();
        removedNodesStack.add(n3);
        removedNodesStack.add(n2);
        removedNodesStack.add(n1);

        HashMap<Integer, INode> originalById = new HashMap<>();
        for (INode n : graph.getNodes()) {
            originalById.put(n.getId(), n);
        }

        
        algo.assignColors(graph, graph, removedNodesStack, originalById);

        
        Color c1 = graph.getNodeColor(n1);
        Color c2 = graph.getNodeColor(n2);
        Color c3 = graph.getNodeColor(n3);

        assertNotNull(c1);
       assertNotNull(c2);
       assertNotNull(c3);

        
        assertNotEquals(c1.getR(), c2.getR());
        
       assertNotEquals(c2.getR(), c3.getR());
    }
}

