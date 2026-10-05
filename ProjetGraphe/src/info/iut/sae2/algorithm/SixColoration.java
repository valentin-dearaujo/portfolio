/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.algorithm;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.IGraph;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import info.iut.sae2.graphs.Color;


/**
 *
 * @author vrioux
 */
public class SixColoration extends UtilsAlgo implements Algorithm<IGraph> {

    @Override
    public IGraph apply(IGraph g, Map<String, Object> parameters) {
        IGraph copy = copyGraph(g);

        HashMap<Integer, INode> originalById = new HashMap<>();
        for (INode n : g.getNodes()) {
            originalById.put(n.getId(), n);
        }

        HashMap<INode, Integer> currentDegrees = new HashMap<>();
        HashMap<INode, Boolean> isRemoved = new HashMap<>();
        ArrayList<INode> list = new ArrayList<>();

        initializeVirtualGraph(copy, currentDegrees, isRemoved, list);
        ArrayList<INode> removedNodesStack = processNodeRemovals(copy, currentDegrees, isRemoved, list);
        assignColors(g, copy, removedNodesStack, originalById);

        return g;
    }
    
    /**
     * method to initialise the arrays needed for the 6 coloration
     * @param g the graph
     * @param currentDegrees a map of nodes and is degree
     * @param isRemoved a map who can we do looking if a node is removed from the algo 
     * @param list list of all the less 5 degree (to delelte in 6 coloration algo)
     */
    void initializeVirtualGraph(IGraph g, HashMap<INode, Integer> currentDegrees,
            HashMap<INode, Boolean> isRemoved, ArrayList<INode> list) {
        for (INode node : g.getNodes()) {
            int degree = g.getNeighbors(node).size();
            currentDegrees.put(node, degree);
            isRemoved.put(node, false);
            if (degree <= 5) {
                list.add(node);
            }
        }
    }
    
    
    /**
     * method to execute the remove of the less  5 degree node and update ambiant degree
     * @param g the graph
     * @param currentDegrees our map of node and degree associated
     * @param isRemoved our map of node and boolean associated to say if a node was removed
     * @param list list of less 5 degree node
     * @return all the removed nodes
     */
     ArrayList<INode> processNodeRemovals(IGraph g, HashMap<INode, Integer> currentDegrees,
            HashMap<INode, Boolean> isRemoved, ArrayList<INode> list) {
        ArrayList<INode> removedNodesStack = new ArrayList<>();
        ArrayList<INode> allNodes = g.getNodes();
        int totalNodes = allNodes.size();

        while (removedNodesStack.size() < totalNodes) {
            INode nodeToRemove = getNextNodeToRemove(list, isRemoved, allNodes);
            removedNodesStack.add(nodeToRemove);
            isRemoved.put(nodeToRemove, true);
            updateNeighborDegrees(g, nodeToRemove, currentDegrees, isRemoved, list);
        }

        return removedNodesStack;
    }
    
    
    /**
     *  get the next node at to be removed
     * @param list list of less 5 degree node
     * @param isRemoved isRemoved our map of node and boolean associated to say if a node was removed
     * @param allNodes as is named can let think is it all the nodes
     * @return INode who is the next to be removed
     */
     INode getNextNodeToRemove(ArrayList<INode> list,
            HashMap<INode, Boolean> isRemoved, ArrayList<INode> allNodes) {
        INode resultNode = null;
        int c = 0;
        while (c < list.size() && resultNode == null) {
            INode candidate = list.get(c);
            if (!isRemoved.get(candidate)) {
                resultNode = candidate;
                list.remove(c);
            } else {
                c++;
            }
        }
        
        resultNode = rescueGetNextNodeToRemove(resultNode, allNodes, isRemoved);

        

        return resultNode;
    }
    /**
     *  get the next node at to be removed if getNextNodeToRemove failed and shearch just a node not removed
     * @param list list of less 5 degree node
     * @param isRemoved isRemoved our map of node and boolean associated to say if a node was removed
     * @param allNodes as is named can let think is it all the nodes
     * @return INode who is the next to be removed
     */
     INode rescueGetNextNodeToRemove(INode resultNode, ArrayList<INode> allNodes, HashMap<INode, Boolean> isRemoved){
        int index = 0;
        INode res = resultNode;
        while (res == null && index < allNodes.size()) {
            INode candidate = allNodes.get(index);
            if (!isRemoved.get(candidate)) {
                res = candidate;
            }
            index++;
        }
        return res;
    }
    
    
    /**
     * update the degree of the graph now some Node been removed
     * @param g the graph
     * @param removedNode the node who is removed
     * @param currentDegrees the degree of each node 
     * @param isRemoved isRemoved our map of node and boolean associated to say if a node was removed
     * @param list list of less 5 degree node
     */
     void updateNeighborDegrees(IGraph g, INode removedNode,  HashMap<INode, Integer> currentDegrees, HashMap<INode, Boolean> isRemoved,  ArrayList<INode> list) { 
        for (INode neighbor : g.getNeighbors(removedNode)) {
            if (!isRemoved.get(neighbor)) {
                int newDegree = currentDegrees.get(neighbor)-1;
                currentDegrees.put(neighbor, newDegree);
                if (newDegree == 5) {
                    list.add(neighbor);
                }
            }
        }
    }
    
    
    /**
     * choose and assign a color to a Node
     * @param g the graph
     * @param copy copy of the graph g
     * @param removedNodesStack list of the node removed
     * @param originalById node associated by is id 
     */
     void assignColors(IGraph g, IGraph copy, ArrayList<INode> removedNodesStack,
            HashMap<Integer, INode> originalById) {
        HashMap<INode, Integer> nodeColors = new HashMap<>();

        for (int i = removedNodesStack.size()-1; i >= 0; i--) {
            INode copyNode = removedNodesStack.get(i);
            int color = smallestFreeColor(copy.getNeighbors(copyNode), nodeColors);
            nodeColors.put(copyNode, color);

            INode origNode = originalById.get(copyNode.getId());
            if (origNode != null) {
                g.setNodeColor(origNode, new Color(color));
            }
        }
    }
}