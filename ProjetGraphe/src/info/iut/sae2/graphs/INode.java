/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package info.iut.sae2.graphs;

import java.util.HashSet;

/**
 *
 * @author rbourqui
 */
public interface INode {

    /**
     * setter of coord attribute in INode
     * @param coord 
     */
    public void setCoord(Coord coord);
    
    /**
     * getter of coord attribute in INode
     * @return coord
     */
    public Coord getCoord();
    
    public Color getColor();
    
    public void setColor(Color c);
    
    public void setSize(Size s);
    public Size getSize();
    public HashSet<IEdge> getEdge();
    public void setId(Integer id);
    public Integer getId();
    
    

}
