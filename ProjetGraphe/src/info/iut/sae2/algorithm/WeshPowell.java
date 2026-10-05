/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.IGraph;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import info.iut.sae2.graphs.Color;

/**
 *
 * @author vrioux
 */
public class WeshPowell extends UtilsAlgo implements Algorithm<Object> {

    
    
    
    @Override
    public Object apply(IGraph g, Map<String, Object> parameters) {

        ArrayList<INode> nodes = g.getNodes();

        
        HashMap<INode, Integer> nodeColors = new HashMap<>();
        //sortNodes(g, nodes); old version of the sort (bubble sort)
        sortFusion(nodes, g);

        for (INode node : nodes) {
            HashSet<Integer> neighborColorGived = new HashSet<>();
            
            for (INode neighbor : g.getNeighbors(node)) {
                if (nodeColors.containsKey(neighbor)) {
                    neighborColorGived.add(nodeColors.get(neighbor));
                }
            }

            int iColor = 0;
            while (neighborColorGived.contains(iColor)) {
                iColor++;
            }
            nodeColors.put(node, iColor);
            g.setNodeColor(node, new Color(iColor));
        }

        return nodeColors; // noted that apply could be void because we modify directly the graph (just the interface specify a return type)
    }
    
    
    
   

}
