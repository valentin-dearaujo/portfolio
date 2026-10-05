/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

import info.iut.sae2.properties.SizeProperty;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author vrioux
 */
public class Node implements INode {

    private Size size;
    private Color color;
    private Coord coord;
    private Integer id;
    
    public static Integer nbNode = 0;
    private HashSet<IEdge> myEdges = new HashSet<>();
    
    
    //////////////////////////////////////////////////////////////
    /////////////////////////Constructor//////////////////////////
    
    public Node() {
        this.size= new Size(1,1);
        this.color = new Color(255, 0, 0, 255);
        this.coord = new Coord(0, 0);
        this.id=nbNode;
        nbNode++;
    }
    public Node(Size s, Color cou, Coord coo){
        this.size = s;
        this.color = cou;
        this.coord = coo;
        this.id=nbNode;
        nbNode++;
    }
    
    
    /////////////////////////Constructor//////////////////////////
    //////////////////////////////////////////////////////////////
    
    //////////////////////////////////////////////////////////////
    //////////////////////////////Method//////////////////////////
    
    /**
     * setter of attribute Coord
     * @param coord 
     */
    @Override
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    
    /**
     * Getter of attribute Coord
     * @return 
     */
    @Override
    public Coord getCoord() {
        return this.coord;
    }
    
    /**
     * getter of attribute Color
     * @return 
     */
    @Override
    public Color getColor() {
        return this.color;
    }
    
    
    /**
     * setter of attribute Color
     * @param c 
     */
    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public void setSize(Size s) {
       this.size = s;
    }

    @Override
    public Size getSize() {
       return this.size;
    }

    @Override
    public HashSet<IEdge> getEdge() {
       return myEdges;
    }
    
    
    public Integer getId(){
        return this.id;
    }
    
    
    public void setId(Integer id){
        this.id = id;
    }
    
   

    @Override
    public boolean equals(Object o) {
        
        INode node = (Node) o;

        
        return this.id.equals(node.getId());
    }

    @Override
    public int hashCode() {
        
        return java.util.Objects.hash(this.id);
    }
    
    
    /////////////////////////Method//////////////////////////////
    //////////////////////////////////////////////////////////////
}
