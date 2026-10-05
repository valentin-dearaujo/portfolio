package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class FiveColoration extends UtilsAlgo implements Algorithm<IGraph> {

    @Override
    public IGraph apply(IGraph g, Map<String, Object> parameters) {
        IGraph copy = g.copy();
        HashMap<Integer, INode> originalId = buildId(g);
        HashMap<INode, Integer> colors = new HashMap<>();
        
        colorGraph(copy, colors);
        applyColors(g, colors, originalId);
        
        return g;
    }
    
    /**
     * method to associated all node by him id in a HashMap
     * @param g the graph
     * @return all node by him id in a HashMap
     */
    private HashMap<Integer, INode> buildId(IGraph g) {
        HashMap<Integer, INode> originalById = new HashMap<>();
        for (INode n : g.getNodes()) {
            originalById.put(n.getId(), n);
        }
        return originalById;
    }

    
    /**
     * method to color the the graph
     * @param g the graph
     * @param colors  all the node associated to his color (construtor color can build a color by a int)
     */
    private void colorGraph(IGraph g, HashMap<INode, Integer> colors) {
        if (g.getNodes().size() <= 5) {
            naiveColor(g.getNodes(), colors);
        } else {
            INode node4 = findNodeWithDegreeAtMost(g, 4);
            if (node4 != null) {
                degree4(g, node4, colors);
            } else {
                degree5(g, colors);
            }
        }
    }
    
    
    /**
     * In the case of a degree4 node 
     * @param g the graph
     * @param node4 4 degree node 
     * @param colors all the colors associated 
     */
    private void degree4(IGraph g, INode node4, HashMap<INode, Integer> colors) {
        ArrayList<INode> neighbors = g.getNeighbors(node4);
        removeNodeAndEdges(g, node4);
        
        colorGraph(g, colors);
        
        colors.put(node4, smallestFreeColor(neighbors, colors));
    }
    
    /**
     * In the case of a degree4 node 
     * @param g the graph
     * @param node5 5 degree node 
     * @param colors all the colors associated 
     */
    private void degree5(IGraph g, HashMap<INode, Integer> colors) {
        INode node5 = findNodeWithDegreeAtMost(g, 5);
        ArrayList<INode> neighbors5 = new ArrayList<>(g.getNeighbors(node5));
        
        INode[] pair = findPair(g, neighbors5);
        INode u = pair[0];
        INode v = pair[1];
        
        mergeInto(g, v, u, node5);
        removeNodeAndEdges(g, node5);
        
        colorGraph(g, colors);
        
        colors.put(v, colors.get(u));
        colors.put(node5, smallestFreeColor(neighbors5, colors));
    }
    
    
    
    
    /**
     * shearch in a array of node the first pair of non-adjacent nodes
     * @param g the graph
     * @param neighbors list node where search a non-adjacent nodes pair 
     * @return a pair of node not adjacent
     */
    private INode[] findPair(IGraph g, ArrayList<INode> neighbors) {
        INode[] pair = new INode[2];
        boolean found = false;
        int i = 0;
        
        while (i < neighbors.size() && !found) {
            int j = i + 1;
            while (j < neighbors.size() && !found) {
                INode a = neighbors.get(i);
                INode b = neighbors.get(j);
                if (!g.existEdge(a, b, false)) {
                    pair[0] = a;
                    pair[1] = b;
                    found = true;
                }
                j++;
            }
            i++;
        }
        
        return pair; 
    }
    
    
}