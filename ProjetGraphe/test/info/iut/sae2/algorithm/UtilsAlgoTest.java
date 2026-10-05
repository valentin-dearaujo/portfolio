package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert; // Import officiel de JUnit 4

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UtilsAlgoTest {

    private UtilsAlgo utilsAlgo;

    @Before
    public void setUp() {
        utilsAlgo = new UtilsAlgo();
        // Réinitialisation des compteurs statiques pour éviter les effets de bord
        Node.nbNode = 0;
        Edge.nbEdge = 0;
    }

    @Test
    public void testGetNeighborColors() {
        IGraph g = new Graph();
        INode mainNode = g.addNode(); 
        INode neighbor1 = g.addNode(); 
        INode neighbor2 = g.addNode(); 
        INode neighborWithoutColor = g.addNode(); 

        g.addEdge(mainNode, neighbor1);
        g.addEdge(mainNode, neighbor2);
        g.addEdge(mainNode, neighborWithoutColor);

        HashMap<INode, Integer> nodeColors = new HashMap<>();
        nodeColors.put(neighbor1, 10);
        nodeColors.put(neighbor2, 30);

        HashSet<Integer> result = utilsAlgo.getNeighborColors(g, mainNode, nodeColors);

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(10));
        Assert.assertTrue(result.contains(30));
        Assert.assertFalse(result.contains(20));
    }

    @Test
    public void testSortNodes() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);

        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(n1); 
        nodes.add(n3); 
        nodes.add(n2);

        UtilsAlgo.sortNodes(g, nodes);

        Assert.assertEquals(n2, nodes.get(0));
    }

    @Test
    public void testSortSelection() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n2, n3);

        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(n1); 
        nodes.add(n3); 
        nodes.add(n2);

        UtilsAlgo.sortSelection(g, nodes);

        Assert.assertEquals(n2, nodes.get(0));
    }

    @Test
    public void testSmallestFreeColor() {
        INode n1 = new Node();
        INode n2 = new Node();
        INode n3 = new Node();
        
        ArrayList<INode> neighbors = new ArrayList<>();
        neighbors.add(n1);
        neighbors.add(n2);
        neighbors.add(n3);

        HashMap<INode, Integer> nodeColors = new HashMap<>();
        nodeColors.put(n1, 0); 
        nodeColors.put(n2, 1); 
        nodeColors.put(n3, 3); 

        int freeColor = utilsAlgo.smallestFreeColor(neighbors, nodeColors);

        Assert.assertEquals(2, freeColor);
    }

    @Test
    public void testFindNodeWithDegreeAtMost() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();

        
        g.addEdge(n1, n2);

        
        INode result = utilsAlgo.findNodeWithDegreeAtMost(g, 1);
        
        
        Assert.assertNotNull(result);
        Assert.assertTrue(g.degree(result) <= 1);

        INode resultNull = utilsAlgo.findNodeWithDegreeAtMost(g, -1);
        Assert.assertNull(resultNull);
    }

    @Test
    public void testRemoveNodeAndEdges() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        
        g.addEdge(n1, n2);

        Assert.assertEquals(2, g.numberOfNodes());
        Assert.assertEquals(1, g.numberOfEdges());

        utilsAlgo.removeNodeAndEdges(g, n1);

        Assert.assertEquals(1, g.numberOfNodes());
        Assert.assertEquals(0, g.numberOfEdges());
        Assert.assertFalse(g.getNodes().contains(n1));
    }

    @Test
    public void testMergeInto() {
        IGraph g = new Graph();
        INode firstNode = g.addNode();
        INode secondNode = g.addNode();
        INode thirdNode = g.addNode();
        INode commonNeighbor = g.addNode();

        g.addEdge(firstNode, secondNode);
        g.addEdge(firstNode, thirdNode);
        g.addEdge(firstNode, commonNeighbor);

        Assert.assertFalse(g.existEdge(secondNode, commonNeighbor, false));

        utilsAlgo.mergeInto(g, firstNode, secondNode, thirdNode);

        Assert.assertTrue(g.existEdge(secondNode, commonNeighbor, false));
        Assert.assertFalse(g.getNodes().contains(firstNode));
    }

    @Test
    public void testNaiveColor() {
        INode n1 = new Node();
        INode n2 = new Node();
        INode n3 = new Node();

        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);

        HashMap<INode, Integer> nodeColors = new HashMap<>();
        nodeColors.put(n2, 88);

        utilsAlgo.naiveColor(nodes, nodeColors);

        // Sous JUnit 4, pour comparer des objets (Integer), on utilise explicitement assertEquals(Object, Object)
        Assert.assertEquals((Integer) 0, nodeColors.get(n1));
        Assert.assertEquals((Integer) 88, nodeColors.get(n2));
        Assert.assertEquals((Integer) 1, nodeColors.get(n3));
    }

@Test
    public void testCopyGraph() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        g.addEdge(n1, n2);

        
        IGraph copy = utilsAlgo.copyGraph(g);

        Assert.assertNotNull(copy);
        
        Assert.assertNotSame(g, copy); 
        
        
        Assert.assertEquals(g.numberOfNodes(), copy.numberOfNodes());
        Assert.assertEquals(g.numberOfEdges(), copy.numberOfEdges());
    }

    @Test
    public void testApplyColors() {
        IGraph g = new Graph();
        INode origNode = g.addNode(); 
        
        INode copyNode = new Node();
        copyNode.setId(origNode.getId()); 

        HashMap<INode, Integer> colors = new HashMap<>();
        colors.put(copyNode, 5); 

        HashMap<Integer, INode> originalById = new HashMap<>();
        originalById.put(origNode.getId(), origNode);

        utilsAlgo.applyColors(g, colors, originalById);

        Assert.assertNotNull(g.getNodeColor(origNode));
    }
    
    

    @Test
    public void testTriFusion_OrdreDecroissantDegre() {
        IGraph g = new Graph();
     
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();
        INode n4 = g.addNode();
        INode n5 = g.addNode();

        g.addEdge(n1, n2);
        g.addEdge(n1, n3);
        g.addEdge(n1, n5);

        g.addEdge(n3, n5);


        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(n4); 
        nodes.add(n2); 
        nodes.add(n1); 
        nodes.add(n3); 

     
        long ops = UtilsAlgo.sortFusion(nodes, g);

    
        Assert.assertTrue(ops > 0); 
        Assert.assertEquals(4, nodes.size());

        Assert.assertEquals(n3, nodes.get(1)); // Degré 2
        Assert.assertEquals(n2, nodes.get(2)); // Degré 1
        Assert.assertEquals(n4, nodes.get(3)); // Degré 0
    }

    @Test
    public void testTriFusion_StabiliteDegresEgaux() {
        IGraph g = new Graph();
        INode n1 = g.addNode();
        INode n2 = g.addNode();
        INode n3 = g.addNode();


        g.addEdge(n1, n3);
        g.addEdge(n2, n3);


        ArrayList<INode> nodes = new ArrayList<>();
        nodes.add(n1);
        nodes.add(n2);

        UtilsAlgo.sortFusion(nodes, g);

        // Le tri fusion étant stable (grâce au conditionnel '>='), l'ordre initial [n1, n2] doit rester inchangé        Assert.assertEquals(n1, nodes.get(0));
        Assert.assertEquals(n2, nodes.get(1));
    }
    
}