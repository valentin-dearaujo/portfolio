/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package info.iut.sae2.graphs;

import java.util.ArrayList;

/**
 *
 * @author rbourqui
 */
public interface IEdge {
    /**
     * Returns the source of the edge
     * 
     * @return the source of the edge
     */
    public INode source();
    
    /**
     * Returns the target of the edge
     * 
     * @return the target of the edge
     */
    public INode target();
    
    /**
     * setter of coord attribute in IEdge
     * @param coord 
     */
    public void setCoords(ArrayList<Coord> coords);
    
    /**
     * getter of coord attribute in IEdge
     * @return coord
     */
    public ArrayList<Coord> getCoords();
    
    
    public Color getColor();
    
    public void setColor(Color c);
    
    public void setWidth(Double width);
    
    public Double getWidth();
    
    public void setId(Integer id);
    public Integer getId();
}
