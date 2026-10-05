package info.iut.sae2.graphs;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Graph.
 */
public class GraphTest
{

    private Graph graph;
    private INode nodeA;
    private INode nodeB;
    private INode nodeC;

    @Before
    public void setUp() {
        graph = new Graph();
        nodeA = graph.addNode();
        nodeB = graph.addNode();
        nodeC = graph.addNode();
    }



    @Test
    public void testAddNodeEmpty() {
        Graph g = new Graph();
        assertEquals(0, g.numberOfNodes());
    }

    @Test
    public void testAddNodeIncrementsCount() {
        assertEquals(3, graph.numberOfNodes());
    }

    @Test
    public void testAddNodeReturnsNonNull() {
        INode n = graph.addNode();
        assertNotNull(n);
    }

    @Test
    public void testAddNodeWithExistingNode() {
        INode existing = new Node();
        int before = graph.numberOfNodes();
        graph.addNode(existing);
        assertEquals(before + 1, graph.numberOfNodes());
    }

    @Test
    public void testAddNodeWithExistingNodeReturnsSameNode() {
        INode existing = new Node();
        INode returned = graph.addNode(existing);
        assertEquals(existing, returned);
    }



    @Test
    public void testDelNodeDecrementsCount() {
        int before = graph.numberOfNodes();
        graph.delNode(nodeA);
        assertEquals(before - 1, graph.numberOfNodes());
    }

    @Test
    public void testDelNodeRemovesFromGetNodes() {
        graph.delNode(nodeA);
        assertFalse(graph.getNodes().contains(nodeA));
    }



    @Test
    public void testAddEdgeNoEdgesInitially() {
        Graph g = new Graph();
        g.addNode();
        g.addNode();
        assertEquals(0, g.numberOfEdges());
    }

    @Test
    public void testAddEdgeBetweenNodesIncrementsCount() {
        graph.addEdge(nodeA, nodeB);
        assertEquals(1, graph.numberOfEdges());
    }

    @Test
    public void testAddEdgeBetweenNodesReturnsNonNull() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertNotNull(e);
    }

    @Test
    public void testAddEdgeSetsCorrectTarget() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertEquals(nodeA, e.source());
        System.out.println(e.source());
    }

    @Test
    public void testAddEdgeSetsCorrectSource() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertEquals(nodeB, e.target());
    }

    @Test
    public void testAddEdgeObjectIncrementsCount() {
        IEdge e = new Edge(nodeA, nodeB);
        int before = graph.numberOfEdges();
        graph.addEdge(e);
        assertEquals(before + 1, graph.numberOfEdges());
    }


    @Test
    public void testDelEdgeDecrementsCount() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        int before = graph.numberOfEdges();
        graph.delEdge(e);
        assertEquals(before - 1, graph.numberOfEdges());
    }

    @Test
    public void testDelEdgeRemovesFromGetEdges() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        graph.delEdge(e);
        assertFalse(graph.getEdges().contains(e));
    }


    @Test
    public void testGetNodesReturnsAllNodes() {
        ArrayList<INode> nodes = graph.getNodes();
        assertTrue(nodes.contains(nodeA));
        assertTrue(nodes.contains(nodeB));
        assertTrue(nodes.contains(nodeC));
    }

    @Test
    public void testGetNodesSizeMatchesNumberOfNodes() {
        assertEquals(graph.numberOfNodes(), graph.getNodes().size());
    }

    @Test
    public void testGetEdgesReturnsAllEdges() {
        IEdge e1 = graph.addEdge(nodeA, nodeB);
        IEdge e2 = graph.addEdge(nodeB, nodeC);
        ArrayList<IEdge> edges = graph.getEdges();
        assertTrue(edges.contains(e1));
        assertTrue(edges.contains(e2));
    }

    @Test
    public void testGetEdgesSizeMatchesNumberOfEdges() {
        graph.addEdge(nodeA, nodeB);
        graph.addEdge(nodeB, nodeC);
        assertEquals(graph.numberOfEdges(), graph.getEdges().size());
    }



    @Test
    public void testSourceReturnsCorrectNode() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertEquals(nodeA, graph.source(e));
    }

    @Test
    public void testTargetReturnsCorrectNode() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertEquals(nodeB, graph.target(e));
    }



    @Test
    public void testGetOutEdgesReturnsEdgesFromNode() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertTrue(graph.getOutEdges(nodeA).contains(e));
    }

    @Test
    public void testGetOutEdgesDoesNotReturnIncomingEdge() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertFalse(graph.getOutEdges(nodeB).contains(e));
    }

    @Test
    public void testGetInEdgesReturnsEdgesToNode() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertTrue(graph.getInEdges(nodeB).contains(e));
    }

    @Test
    public void testGetInEdgesDoesNotReturnOutgoingEdge() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        assertFalse(graph.getInEdges(nodeA).contains(e));
    }

    @Test
    public void testGetInOutEdgesContainsBothDirections() {
        IEdge eAB = graph.addEdge(nodeA, nodeB);
        IEdge eCA = graph.addEdge(nodeC, nodeA);
        ArrayList<IEdge> inOut = graph.getInOutEdges(nodeA);
        assertTrue(inOut.contains(eAB));
        assertTrue(inOut.contains(eCA));
    }



    @Test
    public void testGetSuccessorsReturnCorrectNodes() {
        graph.addEdge(nodeA, nodeB);
        assertTrue(graph.getSuccesors(nodeA).contains(nodeB));
    }

    @Test
    public void testGetSuccessorsDoesNotReturnPredecessor() {
        graph.addEdge(nodeA, nodeB);
        assertFalse(graph.getSuccesors(nodeB).contains(nodeA));
    }

    @Test
    public void testGetPredecessorsReturnCorrectNodes() {
        graph.addEdge(nodeA, nodeB);
        assertTrue(graph.getPredecessors(nodeB).contains(nodeA));
    }

    @Test
    public void testGetPredecessorsDoesNotReturnSuccessor() {
        graph.addEdge(nodeA, nodeB);
        assertFalse(graph.getPredecessors(nodeA).contains(nodeB));
    }

    @Test
    public void testGetNeighborsContainsSuccessorsAndPredecessors() {
        graph.addEdge(nodeA, nodeB); 
        graph.addEdge(nodeC, nodeA); 
        ArrayList<INode> neighbors = graph.getNeighbors(nodeA);
        assertTrue(neighbors.contains(nodeB));
        assertTrue(neighbors.contains(nodeC));
    }



    @Test
    public void testInDegreeZeroWhenNoIncomingEdges() {
        assertEquals(0, graph.inDegree(nodeA));
    }

    @Test
    public void testInDegreeIncreasesWithIncomingEdge() {
        graph.addEdge(nodeB, nodeA);
        assertEquals(1, graph.inDegree(nodeA));
    }

    @Test
    public void testOutDegreeZeroWhenNoOutgoingEdges() {
        assertEquals(0, graph.outDegree(nodeA));
    }

    @Test
    public void testOutDegreeIncreasesWithOutgoingEdge() {
        graph.addEdge(nodeA, nodeB);
        assertEquals(1, graph.outDegree(nodeA));
    }

    @Test
    public void testInAndOutDegreeMultipleEdges() {
        graph.addEdge(nodeA, nodeB);
        graph.addEdge(nodeA, nodeC);
        graph.addEdge(nodeB, nodeA);
        assertEquals(1, graph.inDegree(nodeA));
        assertEquals(2, graph.outDegree(nodeA));
    }



    @Test
    public void testExistEdgeNotOrientedReturnsTrueForwardDirection() {
        graph.addEdge(nodeA, nodeB);
        assertTrue(graph.existEdge(nodeA, nodeB, false));
    }

    @Test
    public void testExistEdgeNotOrientedReturnsTrueReverseDirection() {
        graph.addEdge(nodeA, nodeB);
        assertTrue(graph.existEdge(nodeB, nodeA, false));
    }

    @Test
    public void testExistEdgeNotOrientedReturnsFalseWhenAbsent() {
        assertFalse(graph.existEdge(nodeA, nodeC, false));
    }



    @Test
    public void testExistEdgeOrientedReturnsTrueCorrectDirection() {
        graph.addEdge(nodeA, nodeB);
        assertTrue(graph.existEdge(nodeA, nodeB, true));
    }

    @Test
    public void testExistEdgeOrientedReturnsFalseWhenAbsent() {
        assertFalse(graph.existEdge(nodeA, nodeC, true));
    }




    @Test
    public void testCreateGraphReturnsNonNull() {
        assertNotNull(graph.createGraph());
    }

    @Test
    public void testCreateGraphReturnsEmptyGraph() {
        IGraph g = graph.createGraph();
        assertEquals(0, g.numberOfNodes());
        assertEquals(0, g.numberOfEdges());
    }

    @Test
    public void testCopyReturnsNonNull() {
        assertNotNull(graph.copy());
    }

    @Test
    public void testCopyHasSameNumberOfNodes() {
        IGraph copy = graph.copy();
        assertEquals(graph.numberOfNodes(), copy.numberOfNodes());
    }

    @Test
    public void testCopyHasSameNumberOfEdges() {
        graph.addEdge(nodeA, nodeB);
        IGraph copy = graph.copy();
        assertEquals(graph.numberOfEdges(), copy.numberOfEdges());
    }



    @Test
    public void testSetAndGetNodeColor() {
        Color c = new Color(255, 0, 0, 0);
        graph.setNodeColor(nodeA, c);
        assertEquals(c, graph.getNodeColor(nodeA));
    }

    @Test
    public void testSetAllNodesColors() {
        Color c = new Color(0, 255, 0, 0);
        graph.setAllNodesColors(c);
        for (INode n : graph.getNodes()) {
            assertEquals(c, graph.getNodeColor(n));
        }
    }

    @Test
    public void testSetAndGetNodeSize() {
        Size s = new Size(10.0, 10.0);
        graph.setNodeSize(nodeA, s);
        assertEquals(s, graph.getNodeSize(nodeA));
    }

    @Test
    public void testSetAllNodesSizes() {
        Size s = new Size(5.0, 5.0);
        graph.setAllNodesSizes(s);
        for (INode n : graph.getNodes()) {
            assertEquals(s, graph.getNodeSize(n));
        }
    }

    @Test
    public void testSetAndGetNodePosition() {
        Coord c = new Coord(3.0, 7.0);
        graph.setNodePosition(nodeA, c);
        assertEquals(c, graph.getNodePosition(nodeA));
    }



    @Test
    public void testSetAndGetEdgeColor() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        Color c = new Color(0, 0, 255, 0);
        graph.setEdgeColor(e, c);
        assertEquals(c, graph.getEdgeColor(e));
    }

    @Test
    public void testSetAllEdgesColors() {
        IEdge e1 = graph.addEdge(nodeA, nodeB);
        IEdge e2 = graph.addEdge(nodeB, nodeC);
        Color c = new Color(128, 128, 128, 128);
        graph.setAllEdgesColors(c);
        assertEquals(c, graph.getEdgeColor(e1));
        assertEquals(c, graph.getEdgeColor(e2));
    }

    @Test
    public void testSetAndGetEdgeWidth() {
        IEdge e = graph.addEdge(nodeA, nodeB);
        graph.setEdgeWidth(e, 3.0);
        assertEquals(3.0, graph.getEdgeWidth(e), 0.001);
    }

    @Test
    public void testSetAllEdgesWidths() {
        IEdge e1 = graph.addEdge(nodeA, nodeB);
        IEdge e2 = graph.addEdge(nodeB, nodeC);
        graph.setAllEdgesWidths(2.5);
        assertEquals(2.5, graph.getEdgeWidth(e1), 0.001);
        assertEquals(2.5, graph.getEdgeWidth(e2), 0.001);
    }



    @Test
    public void testGetBoundingBoxThrowsOnEmptyGraph()  {
        Graph g = new Graph();
        g.getBoundingBox();
    }

    @Test
    public void testGetBoundingBoxReturnsTwoCoords()  {
        graph.setNodePosition(nodeA, new Coord(0.0, 0.0));
        graph.setNodePosition(nodeB, new Coord(10.0, 5.0));
        graph.setNodePosition(nodeC, new Coord(5.0, 10.0));
        ArrayList<Coord> bb = graph.getBoundingBox();
        assertEquals(2, bb.size());
    }

    @Test
    public void testGetBoundingBoxTopLeftCorner()  {
        graph.setNodePosition(nodeA, new Coord(1.0, 2.0));
        graph.setNodePosition(nodeB, new Coord(10.0, 8.0));
        graph.setNodePosition(nodeC, new Coord(5.0, 5.0));
        Coord topLeft = graph.getBoundingBox().get(0);
        assertEquals(1.0, topLeft.x, 0.001);
        assertEquals(2.0, topLeft.y, 0.001);
    }

    @Test
    public void testGetBoundingBoxBottomRightCorner() {
        graph.setNodePosition(nodeA, new Coord(1.0, 2.0));
        graph.setNodePosition(nodeB, new Coord(10.0, 8.0));
        graph.setNodePosition(nodeC, new Coord(5.0, 5.0));
        Coord botRight = graph.getBoundingBox().get(1);
        assertEquals(10.0, botRight.x, 0.001);
        assertEquals(8.0, botRight.y, 0.001);
    }

    @Test
    public void testGetBoundingBoxSingleNode() {
        Graph g = new Graph();
        INode n = g.addNode();
        g.setNodePosition(n, new Coord(4.0, 6.0));
        ArrayList<Coord> bb = g.getBoundingBox();
        assertEquals(4.0, bb.get(0).x, 0.001);
        assertEquals(6.0, bb.get(0).y, 0.001);
        assertEquals(4.0, bb.get(1).x, 0.001);
        assertEquals(6.0, bb.get(1).y, 0.001);
    }
}