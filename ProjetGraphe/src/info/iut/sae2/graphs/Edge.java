/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author vrioux
 */
public class Edge implements IEdge{
    
    private Size size;
    private Color color;
    
    private INode target;
    private INode source;
    
    private ArrayList<Coord> coords = new ArrayList<>();
    
    private Double width;
    
    public static Integer nbEdge = 0;
    private Integer id;
    
    
    //////////////////////////////////////////////////////////////
    /////////////////////////Constructor//////////////////////////

    public Edge() {
        this.size= new Size(1,1);
        this.color = new Color(0, 0, 0, 254);
        this.coords.add(new Coord(0,0));
        this.id=nbEdge;
        nbEdge++;
    }
    
    public Edge(Size s, Color cou, INode t, INode sou){
        this.color = cou;
        this.size = s;
        this.target = t;
        this.source = sou;
        this.id=nbEdge;
        nbEdge++;
    }
    
    
    
    public Edge(INode t, INode sou){
        this.color = new Color(0, 0, 0, 255);
        this.size = new Size(10, 10);
        this.target = t;
        this.source = sou;
        this.id = nbEdge;
        nbEdge++;
    }
    
        
    
    
    /////////////////////////Constructor//////////////////////////
    //////////////////////////////////////////////////////////////
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////
    //////////////////////////////Method//////////////////////////
    
    
    
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    @Override
    public INode target() {
        return this.target;
    }
    
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    @Override
    public INode source() {
        return this.source;
    }
    
    
    
    
    /**
     * getter of the color
     * @return color
     */
    @Override
    public Color getColor() {
        return this.color;
    }
    
    /**
     * setter of Color attribute
     * @param c 
     */
    @Override
    public void setColor(Color c) {
        this.color = c;
    }
    
    
    /**
     * methode to define the coords in this Edge
     * @param coos list of corrds wanted
     */
    @Override
    public void setCoords(ArrayList<Coord> coos) {
        this.coords = coos;
    }
    
    
    /**
     * method to get the coords
     * @return coords
     */
    @Override
    public ArrayList<Coord> getCoords() {
       return this.coords;
    }
    
    /**
     * setter of the width
     * @param w 
     */
    @Override
    public void setWidth(Double w) {
       this.width = w;
    }
    
    
    /**
     * getter of the width
     * @return get the witdth
     */
    @Override
    public Double getWidth() {
        return this.width;
    }
    
    /**
     * setter of the id 
     * @param id 
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    
    
    /**
     * getter of the id
     * @return the id of the edge
     */
    @Override
    public Integer getId() {
       return this.id;
    }
    /**
     * method equals
     * @param o object to comare of
     * @return boolean if is equal or not
     */
    @Override
    public boolean equals(Object o) {
        
        IEdge edge = (Edge) o;

        
        return edge.source().equals(this.source) && edge.target().equals(this.target);
    }
    /**
     * methode HashCode override to working equals override methods
     * @return hashcode by the target and the source
     */
    @Override
    public int hashCode() {
        
        return java.util.Objects.hash(target, source);
    }
    
    /////////////////////////Method//////////////////////////////
    //////////////////////////////////////////////////////////////
    
    
    
}
