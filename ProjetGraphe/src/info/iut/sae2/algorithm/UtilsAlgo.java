/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.Color;
import info.iut.sae2.graphs.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author vrioux
 */
public class UtilsAlgo {

    /**
     * method who give the color of the neighbor of the node
     *
     * @param g the graphe
     * @param node the node
     * @param nodeColors all the node and he's color associate
     * @return a mist of the used color
     */
    public HashSet<Integer> getNeighborColors(IGraph g, INode node,
            HashMap<INode, Integer> nodeColors) {
        HashSet<Integer> used = new HashSet<>();
        for (INode neighbor : g.getNeighbors(node)) {
            if (nodeColors.containsKey(neighbor)) {
                used.add(nodeColors.get(neighbor));
            }
        }
        return used;

    }

    /**
     * Method to sort all the nodes
     *
     * @param g the graph
     * @param nodes all the nodes
     */
    public static void sortNodes(IGraph g, ArrayList<INode> nodes) {
        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = 0; j < nodes.size() - i - 1; j++) {

                INode n1 = nodes.get(j);
                INode n2 = nodes.get(j + 1);

                int degreeN1 = g.getNeighbors(n1).size();
                int degreeN2 = g.getNeighbors(n2).size();

                if (degreeN1 < degreeN2) {
                    nodes.set(j, n2);
                    nodes.set(j + 1, n1);

                }
            }
        }
    }

    /**
     * sort with selection and not buble like methode sort node
     *
     * @param g the graph
     * @param nodes all the nodes
     */
    public static void sortSelection(IGraph g, ArrayList<INode> nodes) {

        for (int i = 0; i < nodes.size() - 1; i++) {
            int indexMax = i;

            for (int j = i + 1; j < nodes.size(); j++) {

                int degreeJ = g.getNeighbors(nodes.get(j)).size();
                int degreeMax = g.getNeighbors(nodes.get(indexMax)).size();

                if (degreeJ > degreeMax) {
                    indexMax = j;
                }
            }

            INode maxNode = nodes.get(indexMax);
            nodes.set(indexMax, nodes.get(i));
            nodes.set(i, maxNode);
        }
    }

    /**
     * give the smallest free color
     *
     * @param neighbors the neighbors
     * @param nodeColors all the node and he's color associate
     * @return the smallest free color
     */
    public int smallestFreeColor(ArrayList<INode> neighbors, HashMap<INode, Integer> nodeColors) {

        HashSet<Integer> used = new HashSet<>();
        for (INode neighbor : neighbors) {
            if (nodeColors.containsKey(neighbor)) {
                used.add(nodeColors.get(neighbor));
            }
        }
        int color = 0;
        while (used.contains(color)) {
            color++;
        }
        return color;
    }

    /**
     * find the most degree node
     *
     * @param g the graph
     * @param maxDeg the limit about the maxDegree wanted
     * @return INode of the degree if is find else null
     */
    public INode findNodeWithDegreeAtMost(IGraph g, int maxDeg) {
        for (INode node : g.getNodes()) {
            if (g.degree(node) <= maxDeg) {
                return node;
            }
        }
        return null;
    }

    /**
     * function to fusion
     *
     * @param g
     * @param w
     * @param u
     * @param v
     */
    public void mergeInto(IGraph g, INode firstNode, INode secondNode, INode thirdNode) {
        ArrayList<INode> wNeighbors = new ArrayList<>(g.getNeighbors(firstNode));

        for (INode neighbor : wNeighbors) {
            if (neighbor.equals(secondNode) || neighbor.equals(thirdNode)) {
                continue;
            }
            if (!g.existEdge(secondNode, neighbor, false)) {
                g.addEdge(secondNode, neighbor);
            }
        }

        removeNodeAndEdges(g, firstNode);
    }

    /**
     * With the help of the method in Graph can remove a node and a edge
     *
     * @param g the graph
     * @param n the node wanted (remove also the edge link to him)
     *
     */
    public void removeNodeAndEdges(IGraph g, INode n) {
        ArrayList<IEdge> toRemove = new ArrayList<>(g.getInOutEdges(n));
        for (IEdge e : toRemove) {
            g.delEdge(e);
        }
        g.delNode(n);
    }

    /**
     * method to give a uniq color for each node
     *
     * @param nodes list of nodes in graph
     * @param nodeColors each node is linked to a number who represent is color
     */
    public void naiveColor(ArrayList<INode> nodes,
            HashMap<INode, Integer> nodeColors) {
        int color = 0;
        for (INode node : nodes) {
            if (!nodeColors.containsKey(node)) {
                nodeColors.put(node, color++);
            }
        }
    }

    /**
     * copy the graph gived in parameters
     *
     * @param g the graph who need to be copied
     * @return the graph copied
     */
    public IGraph copyGraph(IGraph g) {
        IGraph copy = g.createGraph();

        HashMap<Integer, INode> idMap = new HashMap<>();
        for (INode n : g.getNodes()) {
            INode copied = copy.addNode(n);
            idMap.put(n.getId(), copied);
        }

        for (IEdge e : g.getEdges()) {
            INode src = idMap.get(e.source().getId());
            INode tgt = idMap.get(e.target().getId());
            if (src != null && tgt != null) {
                copy.addEdge(src, tgt);
            }
        }

        return copy;

    }

    /**
     * Add the color to the nodeColors function gived in parameters
     *
     * @param g the graph
     * @param nodeColors HashMap of the node and the color associate
     */
    public void applyColors(IGraph g, HashMap<INode, Integer> colors,
            HashMap<Integer, INode> originalById) {
        for (INode copyNode : colors.keySet()) {
            INode origNode = originalById.get(copyNode.getId());
            if (origNode != null) {
                g.setNodeColor(origNode, new Color(colors.get(copyNode)));
            }
        }
    }
    
    
    
    
    
    
    
    
    /*
    Note that all the sortFusion is given in the Cycle 5 of the First Semester i just adapt it to use ArrayList and 
    english variable because when i will need fast method sort to make a graph for the complexity part
    (name of the Cycle 5 :"Statuette"
    Look git : Git_prof)
    */
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Tri fusion, restreint sur un intervalle.
     *
     * @param tab         tableau à trier
     * @param start       indice du début de l'intervalle
     * @param end         indice de la fin de l'intervalle
     * @param degresCache le cache contenant le degré pré-calculé de chaque nœud
     * @return le nombre d'opérations durant ce tri
     */
    static long sortFusionPartial(ArrayList<INode> tab, int start, int end, HashMap<INode, Integer> degresCache) {
        long nbOperations = 1; // coût du test fin > debut
        if (end > start) {
            int middle = (start + end) / 2;
            nbOperations += sortFusionPartial(tab, start, middle, degresCache);
            nbOperations += sortFusionPartial(tab, middle + 1, end, degresCache);
            nbOperations += fusion(tab, start, middle, end, degresCache);
        }
        return nbOperations;
    }
   /**
     * sort fusion, not restraint on a interval.
     *
     * @param tab         tableau à trier
     * @param degresCache le cache contenant le degré pré-calculé de chaque nœud
     * @return le nombre d'opérations durant ce tri
     */
    static long sortFusionPartial(ArrayList<INode> tab, HashMap<INode, Integer> degresCache) {
        long nbOperations = 1; // coût du test fin > debut
        int start = 0;
        int end = tab.size();
        if (end > start) {
            int middle = (start + end) / 2;
            nbOperations += sortFusionPartial(tab, start, middle, degresCache);
            nbOperations += sortFusionPartial(tab, middle + 1, end, degresCache);
            nbOperations += fusion(tab, start, middle, end, degresCache);
        }
        return nbOperations;
    }

    /**
     * Action de fusion de deux intervalles triés.
     *
     * @param tab         tableau à trier
     * @param start       indice de début
     * @param middle      indice séparant les deux parties
     * @param end         indice de fin
     * @param degresCache le cache contenant le degré pré-calculé de chaque nœud
     * @return le nombre d'opérations durant ce tri
     */
    static long fusion(ArrayList<INode> tab, int start, int middle, int end, HashMap<INode, Integer> degresCache) {
        long nbOper = 0;

        
        ArrayList<INode> tmp = new ArrayList<>(tab);

        int posLeft = start;
        int posRight = middle + 1;
        int posTmp = start; 

       
        while (posLeft <= middle && posRight <= end) {
            nbOper += 3; 

            int degreGauche = degresCache.get(tab.get(posLeft));
            int degreDroite = degresCache.get(tab.get(posRight));

            
            if (degreGauche >= degreDroite) {
                tmp.set(posTmp, tab.get(posLeft));
                posLeft++;
                nbOper++; 
            } else {
                tmp.set(posTmp, tab.get(posRight));
                posRight++;
                nbOper++; 
            }
            posTmp++;
        }

  
        while (posLeft <= middle) {
            nbOper += 2; 
            tmp.set(posTmp, tab.get(posLeft));
            posLeft++;
            posTmp++;
        }

        
        while (posRight <= end) {
            nbOper += 2; 
            tmp.set(posTmp, tab.get(posRight));
            posRight++;
            posTmp++;
        }


        for (int i = start; i <= end; i++) {
            nbOper++;
            tab.set(i, tmp.get(i));
        }

        return nbOper;
    }

    /**
     * Tri fusion principal.
     *
     * @param tab tableau à trier
     * @param g   le graphe pour récupérer la taille des voisins
     * @return le nombre d'opérations durant ce tri
     */
    static long sortFusion(ArrayList<INode> tab, IGraph g) {
        if (tab == null || tab.size() <= 1) {
            return 0;
        }


        HashMap<INode, Integer> degresCache = new HashMap<>();
        for (INode n : tab) {
            degresCache.put(n, g.getNeighbors(n).size());
        }


        return sortFusionPartial(tab, 0, tab.size()-1, degresCache);
    }

}
