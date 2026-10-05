/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

import info.iut.sae2.algorithm.Algorithm;
import info.iut.sae2.algorithm.FiveColoration;
import info.iut.sae2.algorithm.SixColoration;
import info.iut.sae2.properties.ColorProperty;
import info.iut.sae2.properties.LayoutProperty;
import info.iut.sae2.properties.SizeProperty;

import info.iut.sae2.algorithm.WeshPowell;
import static info.iut.sae2.graphs.Node.nbNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author vrioux
 */
public class Graph implements IGraph {

    public HashMap<Integer, INode> nodelist = new HashMap<>();
    public HashMap<Integer, IEdge> edgelist = new HashMap<>();
    protected ColorProperty graphColor;
    protected LayoutProperty graphlayout;
    protected SizeProperty graphsize;

    public Graph() {
    }
public Graph(HashMap<Integer, INode> a, HashMap<Integer, IEdge> b, ColorProperty c, LayoutProperty d, SizeProperty e){
        this.nodelist = new HashMap<>(a);
        this.edgelist = new HashMap<>(b);
        this.graphColor = c;
        this.graphlayout = d;
        this.graphsize = e;
    }
    
    public Graph(Graph g){
        this(g.nodelist, g.edgelist, g.graphColor, g.graphlayout, g.graphsize);
    }

    @Override
    public void fiveColors() {
        Algorithm fiveColoration = new FiveColoration();
        fiveColoration.apply(this, nodelist);
    }

    @Override
    public void sixColors() {
        Algorithm sixColoration = new SixColoration();
        sixColoration.apply(this, nodelist);
    }

    @Override
    public IGraph createGraph() {
        return new Graph();
    }

    @Override
    public IGraph copy() {
       /* Graph res = new Graph();

        res.nodelist = new HashMap<>(this.nodelist);
        res.edgelist = new HashMap<>(this.edgelist);
        */
 return new Graph(this);
    }

    /**
     * Adds a new empty node to the graph and returns it.
     *
     * @return the added node
     */
    @Override
    public INode addNode() {
      INode res = new Node();
        this.nodelist.put(res.getId(), res);
        return res;
    }

    /**
     * Adds a given node to the graph and returns it.
     *
     * @param n the node to add in the graph
     * @return the added node
     */
    @Override
    public INode addNode(INode n) {

      this.nodelist.put(n.getId(), n);
        return n;
    }

    /**
     * Adds a given edge and returns an edge to the graph and returns it.
     *
     * @param e the edge to add
     * @return the added edge
     */
    @Override
    public IEdge addEdge(IEdge e) {
       this.edgelist.put(e.getId(), e);
        return e;
    }

    /**
     * Adds an edge between the two given nodes and returns it.
     *
     * @param src source of the edge to add
     * @param tgt target of the edge to add
     * @return the added edge
     */
    @Override
    public IEdge addEdge(INode src, INode tgt) {
       IEdge res = new Edge(tgt, src);
        this.edgelist.put(res.getId(), res);
        return res;
    }

    /**
     * Deletes a given node from the graph
     *
     * @param n the node to be deleted
     */
    @Override
    public void delNode(INode n) {
    this.nodelist.remove(n.getId());

    }

    /**
     * Deletes a given edge from the graph
     *
     * @param e the edge to be deleted
     */
    @Override
    public void delEdge(IEdge e) {
      this.edgelist.remove(e.getId());
    }

    /**
     * Returns the number of nodes in the graph
     *
     * @return the number of nodes
     */
    @Override
    public int numberOfNodes() {
        return nodelist.size();
    }

    /**
     * Returns the number of edges in the graph
     *
     * @return the number of edges
     */
    @Override
    public int numberOfEdges() {
        return edgelist.size();
    }

    /**
     * Returns the neighbors (successors and predecessors) of a given node in
     * the graph
     *
     * @param n the node whose neighborhood is queried
     * @return the neighbors of the node
     */
    @Override
    public ArrayList<INode> getNeighbors(INode n) {
        ArrayList<INode> res = getSuccesors(n);
        res.addAll(getPredecessors(n));
        return res;
    }

    /**
     * Returns the sucessors of a given node in the graph
     *
     * @param n the node whose successors are queried
     * @return the successors of the node
     */
    @Override
    public ArrayList<INode> getSuccesors(INode n) {
        ArrayList<INode> res = new ArrayList<>();
        ArrayList<IEdge> out = getOutEdges(n);

        for (IEdge i : out) {
            res.add(i.target());
        }

        return res;
    }

    /**
     * Returns the predecessors of a given node in the graph
     *
     * @param n the node whose predecessors are queried
     * @return the predecessors of the node
     */
    @Override
    public ArrayList<INode> getPredecessors(INode n) {
        ArrayList<INode> res = new ArrayList<>();
        ArrayList<IEdge> in = getInEdges(n);

        for (IEdge i : in) {
            res.add(i.source());
        }
        return res;
    }

    /**
     * Returns the indicent edges of a given node
     *
     * @param n the node whose incident edges are queried
     * @return the incident edges of the node
     */
    @Override
    public ArrayList<IEdge> getInOutEdges(INode n) {
        ArrayList<IEdge> res = getInEdges(n);
        res.addAll(getOutEdges(n));
        return res;
    }

    /**
     * Returns the in-going edges of a given node in the graph
     *
     * @param n the node whose in-going are queried
     * @return the in-going edges of the node
     */
    @Override
    public ArrayList<IEdge> getInEdges(INode n) {
        ArrayList<IEdge> res = new ArrayList<>();

        for (IEdge edge : edgelist.values()) {
            if (edge.target().equals(n)) {
                res.add(edge);
            }
        }
        return res;
    }

    /**
     * Returns the out-going edges of a given node in the graph
     *
     * @param n the node whose out-going are queried
     * @return the out-going edges of the node
     */
    @Override
    public ArrayList<IEdge> getOutEdges(INode n) {
        ArrayList<IEdge> res = new ArrayList<>();

        for (IEdge edge : edgelist.values()) {
            if (edge.source().equals(n)) {
                res.add(edge);
            }
        }
        return res;
    }

    /**
     * Returns the nodes of the graph
     *
     * @return the nodes of the graph
     */
    @Override
    public ArrayList<INode> getNodes() {

        ArrayList<INode> res = new ArrayList<>();

        for (INode i: nodelist.values()) {
            res.add(i);
        }

        return res;
    }

    /**
     * Returns the edges of the graph
     *
     * @return the edges of the graph
     */
    @Override
    public ArrayList<IEdge> getEdges() {
        ArrayList<IEdge> res = new ArrayList<>();

        for (IEdge i: edgelist.values()) {
            res.add(i);

        }

        return res;
    }

    /**
     * Returns the source node of a given edge in the graph
     *
     * @param e the edge whose source is queried
     * @return the source of the edge
     */
    @Override
    public INode source(IEdge e) {
        return e.source();
    }

    /**
     * Returns the target node of a given edge in the graph
     *
     * @param e the edge whose target is queried
     * @return the target of the edge
     */
    @Override
    public INode target(IEdge e) {
        return e.target();
    }

    /**
     * Returns the in-degree (number of predecessors) of a given node in the
     * graph
     *
     * @param n the node whose in-degree is queried
     * @return the in-degree of the node
     */
    @Override
    public int inDegree(INode n) {
        return getInEdges(n).size();
    }

    /**
     * Returns the out-degree (number of successors) of a given node in the
     * graph
     *
     * @param n the node whose out-degree is queried
     * @return the out-degree of the node
     */
    @Override
    public int outDegree(INode n) {
        return getOutEdges(n).size();
    }

    /**
     * Returns the degree (numbe of neighbors) of a given node in the graph
     *
     * @param n the node whose degree is queried
     * @return the degree of the node
     */
    @Override
    public int degree(INode n) {
        return getNeighbors(n).size();
    }

    /**
     * Returns true if there exists an edge between the two given nodes,
     * considering the orientation of the edges or not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function consider the edge as directed
     * (arc), and undirected otherwise
     * @return true if the edge exists, false otherwise
     */
    @Override
    public boolean existEdge(INode src, INode tgt, boolean oriented) {
        boolean res = false;

        if (oriented) {
            res = comparatorExistEdgeOriented(src, tgt);
        } else if (!oriented) {
            res = comparatorExistEdgeNotOriented(src, tgt);
        }

        return res;
    }

    /**
     * comparator for fonct existEdge
     *
     * @param src source INode
     * @param tgt target INode
     * @return the boolean if he exist
     */
    private boolean comparatorExistEdgeNotOriented(INode src, INode tgt) {
        boolean res = false;

        for (IEdge i : getEdges()) {
            if ((i.source() == src && i.target() == tgt) || (i.source() == tgt && i.target() == src)) {
                res = true;
                return res;
            }
        }
        return res;
    }

    /**
     * comparator for fonct existEdge
     *
     * @param src source INode
     * @param tgt target INode
     * @return the boolean if he exist
     */
    private boolean comparatorExistEdgeOriented(INode src, INode tgt) {
        boolean res = false;

        for (IEdge i : getEdges()) {
            if ((i.source() == src && i.target() == tgt) || (i.source() == tgt && i.target() == src)) {
                res = true;
                return res;
            }
        }
        return res;
    }

    /**
     * Returns an existing edge between the source and target nodes while
     * considering the orientation of not.
     *
     * @param src the source node of the putative edge
     * @param tgt the target node of the putative edge
     * @param oriented if true, the function consider the edge as directed
     * (arc), and undirected otherwise
     * @return the edge if the edge exists, null otherwise
     */
    @Override
    public IEdge getEdge(INode src, INode tgt, boolean oriented) {
        IEdge rep = null;
        ArrayList<IEdge> edges = getEdges();
        int i = 0;

        while (i < edges.size() && rep == null) {
            IEdge edge = edges.get(i);
            
            if (oriented) {
                if (edge.source().equals(src) && edge.target().equals(tgt)) {
                    rep = edge;
                }
            } else {
                if ((edge.source().equals(src) && edge.target().equals(tgt)) || 
                    (edge.source().equals(tgt) && edge.target().equals(src))) {
                    rep = edge;
                }
            }
            i++;
        }
        
        return rep;
    }

    /**
     * Returns the SizeProperty associated to the IGraph.
     *
     * @return The SizeProperty of the IGraph
     */
    @Override
    public SizeProperty getSizes() {
        return graphsize;
    }

    /**
     * Returns the size of a given node in the graph
     *
     * @param n the node of interest
     * @return the size of the node
     */
    @Override
    public Size getNodeSize(INode n) {
        return n.getSize();
    }

    /**
     * Returns the width of a given edge in the graph
     *
     * @param e the edge of interest
     * @return the width of the edge
     */
    @Override
    public Double getEdgeWidth(IEdge e) {
        return e.getWidth();
    }

    /**
     * Set the size of a given node in the graph
     *
     * @param n the node of interest
     * @param s new size of the node
     */
    @Override
    public void setNodeSize(INode n, Size s) {
        n.setSize(s);
    }

    /**
     * Set the width of a given edge in the graph
     *
     * @param e the edge of interest
     * @param width new width of the edge
     */
    @Override
    public void setEdgeWidth(IEdge e, Double width) {
        e.setWidth(width);
    }

    /**
     * Set the size of all nodes in the graph
     *
     * @param s new size of the nodes
     */
    @Override
    public void setAllNodesSizes(Size s) {
        for (INode i : getNodes()) {
            i.setSize(s);
        }
    }

    /**
     * Set the width of all edges in the graph
     *
     * @param width new width of the edges
     */
    @Override
    public void setAllEdgesWidths(Double width) {
        for (IEdge i : getEdges()) {
            i.setWidth(width);
        }
    }

    /**
     * Returns the LayoutProperty associated to the IGraph.
     *
     * @return The LayoutProperty of the IGraph
     */
    @Override
    public LayoutProperty getLayout() {
        return graphlayout;
    }

    /**
     * Returns the coordinates of a given node in the graph
     *
     * @param n the node of interest
     * @return the coordinates of the node
     */
    @Override
    public Coord getNodePosition(INode n) {
        return n.getCoord();
    }

    /**
     * Returns the coordinates of a given edge (ie the coordinates of its bends)
     * in the graph
     *
     * @param e the edge of interest
     * @return the coordinates of the bends of the edge
     */
    @Override
    public ArrayList<Coord> getEdgePosition(IEdge e) {
        return e.getCoords();
    }

    /**
     * set a Node Position given in the graph
     *
     * @param n
     * @param c
     */
    @Override
    public void setNodePosition(INode n, Coord c) {
        n.setCoord(c);
    }

    /**
     * Set the coordinates of a given edge (ie the coordinates of its bends) in
     * the graph
     *
     * @param e the edge of interest
     * @param bends the bends of the edge
     */
    @Override
    public void setEdgePosition(IEdge e, ArrayList<Coord> bends) {
        e.setCoords(bends);
    }

    /**
     * Set the coordinates of all nodes in the graph
     *
     * @param c new coordinates of the nodes
     */
    @Override
    public void setAllNodesPositions(Coord c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Set the coordinates of all edges (ie the coordinates of its bends) in the
     * graph
     *
     * @param bends the bends of the edges
     */
    @Override
    public void setAllEdgesPositions(ArrayList<Coord> bends) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Returns the coordinates of the bouding box of the graph drawing. The
     * bouding box of a drawing of a graph is the minimum rectangle such that
     * all nodes and edges of the graph are inside the rectangle. Please remind
     * that the y-axis is oriented toward the bottom.
     *
     * @return an ArrayList containing the coordinates of the top-left corner of
     * the bonding box (at the first index in the ArrayList) and the coordinates
     * of the bottom-right corner of the bounding box (second index)
     * 
     *
     */
    @Override
    public ArrayList<Coord> getBoundingBox()   {
        if (this.nodelist.isEmpty()) {
            return null; // else we have a error in the test case
        }

        INode firstNode = nodelist.values().iterator().next();
        Coord topL = new Coord(firstNode.getCoord());
        Coord botR = new Coord(firstNode.getCoord());

        for (INode i : getNodes()) {
            if (comparatorXSuperiority(i, botR)) {
                botR.x = i.getCoord().x;
            }
            if (comparatorYSuperiority(i, botR)) {
                botR.y = i.getCoord().y;
            }
            if (comparatorXInferiority(i, topL)) {
                topL.x = i.getCoord().x;
            }
            if (comparatorYInferiority(i, topL)) {
                topL.y = i.getCoord().y;
            }
        }

        ArrayList<Coord> res = new ArrayList<>();
        res.add(topL);
        res.add(botR);

        return res;
    }
    
    /**
     * comparator for the method getBoundingBox
     * @param node the node to compare it
     * @param toCompar coord with what we compare the coord of the node
     * @return return a boolean if the superiority is true 
     */
    private boolean comparatorXSuperiority(INode node, Coord toCompar) {
        return node.getCoord().x > toCompar.x;
    }
    
    /**
     * comparator for the method getBoundingBox
     * @param node the node to compare it
     * @param toCompar coord with what we compare the coord of the node
     * @return return a boolean if the superiority is true
     */
    private boolean comparatorYSuperiority(INode node, Coord toCompar) {
        return node.getCoord().y > toCompar.y;
    }
    /**
     * comparator for the method getBoundingBox
     * @param node the node to compare it
     * @param toCompar coord with what we compare the coord of the node
     * @return return a boolean if the superiority is true
     */
    private boolean comparatorXInferiority(INode node, Coord toCompar) {
        return node.getCoord().x < toCompar.x;
    }
    
    /**
     * comparator for the method getBoundingBox
     * @param node the node to compare it
     * @param toCompar coord with what we compare the coord of the node
     * @return return a boolean if the superiority is true
     */
    private boolean comparatorYInferiority(INode node, Coord toCompar) {
        return node.getCoord().y < toCompar.y;
    }

    /**
     * Returns the ColorProperty associated to the IGraph.
     *
     * @return The ColorProperty of the IGraph
     */
    @Override
    public ColorProperty getColor() {
        return this.graphColor;
    }

    /**
     * Returns the color of a given node in the graph.
     *
     * @param n the node of interest
     * @return the color of the node
     */
    @Override
    public Color getNodeColor(INode n) {
        return n.getColor();
    }

    /**
     * Returns the color of a given edge in the graph.
     *
     * @param e the edge of interest
     * @return the color of the edge
     */
    @Override
    public Color getEdgeColor(IEdge e) {
        return e.getColor();
    }

    /**
     * Set the color of a given node in the graph.
     *
     * @param n the node of interest
     * @param c new color of the node
     */
    @Override
    public void setNodeColor(INode n, Color c) {
        n.setColor(c);
    }

    /**
     * Set the color of a given edge in the graph.
     *
     * @param e the edge of interest
     * @param c the bends of the edge
     */
    @Override
    public void setEdgeColor(IEdge e, Color c) {
        e.setColor(c);
    }

    /**
     * Set the color of all nodes in the graph.
     *
     * @param c the new color of the nodes
     */
    @Override
    public void setAllNodesColors(Color c) {
        for (INode i : getNodes()) {
            i.setColor(c);
        }
    }

    /**
     * Set the color of all edges in the graph.
     *
     * @param c the new color of the edges
     */
    @Override
    public void setAllEdgesColors(Color c) {
        for (IEdge i : getEdges()) {
            i.setColor(c);
        }
    }

    @Override
    public void welshAndPowell() {
        Algorithm weshAndPowell = new WeshPowell();
        weshAndPowell.apply(this, nodelist);
    }
    
   

}
